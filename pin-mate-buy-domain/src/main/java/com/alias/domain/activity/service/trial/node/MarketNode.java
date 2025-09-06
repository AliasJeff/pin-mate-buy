package com.alias.domain.activity.service.trial.node;

import com.alias.domain.activity.model.entity.MarketProductEntity;
import com.alias.domain.activity.model.entity.TrialBalanceEntity;
import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.alias.domain.activity.model.valobj.SkuVO;
import com.alias.domain.activity.service.discount.IDiscountCalculateService;
import com.alias.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.alias.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.alias.domain.activity.service.trial.thread.QueryGroupBuyActivityDiscountVOThreadTask;
import com.alias.domain.activity.service.trial.thread.QuerySkuVOFromDBThreadTask;
import com.alias.types.design.framework.tree.StrategyHandler;
import com.alias.types.enums.ResponseCode;
import com.alias.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 营销优惠节点
 */
@Slf4j
//@Service
public class MarketNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private TagNode tagNode;

    @Resource
    private ErrorNode errorNode;

    @Resource
    private Map<String, IDiscountCalculateService> discountCalculateServiceMap;


    /**
     * 在 MarketNode2CompletableFuture 继承的子类实现一个 CompletableFuture 多线程方式。
     * <p>
     * 1. CompletableFuture：适用于大多数现代 Java 应用，尤其在需要灵活任务编排时。
     * 2.  FutureTask：任务极度简单，适合简单场景。
     * <p>
     * | 对比维度    | FutureTask             | CompletableFuture                |
     * | :--------------- | :-------------------------- | :------------------------------------- |
     * | 任务编排能力 | 弱（需手动管理多个 Future） | 强（内置 `thenApply`、`allOf` 等方法） |
     * | 代码简洁性  | 冗余（显式调用 `get()`）    | 简洁（链式调用，逻辑内聚）             |
     * | 异常处理   | 繁琐（需捕获多个异常）      | 优雅（支持 `exceptionally` 统一处理）  |
     * | 线程阻塞     | 可能多次阻塞主线程          | 非阻塞或单次阻塞（如 `join()`）        |
     * | 适用场景     | 简单任务、低版本 Java 环境  | 复杂异步流程、Java 8+ 环境             |
     * <p>
     * 使用：MarketNode 的 @Service 注释掉，MarketNode2CompletableFuture 的 @Service 打开，就可以使用了。
     */
    @Override
    protected void multiThread(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        // 异步查询活动配置
        QueryGroupBuyActivityDiscountVOThreadTask queryGroupBuyActivityDiscountVOThreadTask = new QueryGroupBuyActivityDiscountVOThreadTask(marketProductEntity.getActivityId(), marketProductEntity.getSource(), marketProductEntity.getChannel(), marketProductEntity.getGoodsId(), repository);
        FutureTask<GroupBuyActivityDiscountVO> groupBuyActivityDiscountVOFutureTask = new FutureTask<>(queryGroupBuyActivityDiscountVOThreadTask);
        threadPoolExecutor.execute(groupBuyActivityDiscountVOFutureTask);

        QuerySkuVOFromDBThreadTask querySkuVOFromDBThreadTask = new QuerySkuVOFromDBThreadTask(marketProductEntity.getGoodsId(), repository);
        FutureTask<SkuVO> querySkuFutureTask = new FutureTask<>(querySkuVOFromDBThreadTask);
        threadPoolExecutor.execute(querySkuFutureTask);

        // 写入上下文 - 对于一些复杂场景，获取数据的操作，有时候会在下N个节点获取，这样前置查询数据，可以提高接口响应效率
        dynamicContext.setGroupBuyActivityDiscountVO(groupBuyActivityDiscountVOFutureTask.get(timeout, TimeUnit.MILLISECONDS));
        dynamicContext.setSkuVO(querySkuFutureTask.get(timeout, TimeUnit.MILLISECONDS));

        log.info("拼单商品查询试算服务-MarketNode userId:{} 异步线程加载数据「GroupBuyActivityDiscountVO、SkuVO」完成", marketProductEntity.getUserId());
    }

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParameters, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼单商品查询试算服务-MarketNode userId:{} requestParameter:{}", requestParameters.getUserId(), JSON.toJSONString(requestParameters));

        // 获取上下文数据
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicContext.getGroupBuyActivityDiscountVO();
        if (null == groupBuyActivityDiscountVO) {
            return router(requestParameters, dynamicContext);
        }

        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = groupBuyActivityDiscountVO.getGroupBuyDiscount();
        SkuVO skuVO = dynamicContext.getSkuVO();
        if (null == groupBuyDiscount || null == skuVO) {
            return router(requestParameters, dynamicContext);
        }

        // 优惠试算
        IDiscountCalculateService discountCalculateService = discountCalculateServiceMap.get(groupBuyActivityDiscountVO.getGroupBuyDiscount().getMarketPlan());
        if (null == discountCalculateService) {
            throw new AppException(ResponseCode.E0001.getCode(), ResponseCode.E0001.getInfo());
        }

        BigDecimal payPrice = discountCalculateService.calculate(requestParameters.getUserId(), skuVO.getOriginalPrice(), groupBuyActivityDiscountVO.getGroupBuyDiscount());
        BigDecimal deductionPrice = skuVO.getOriginalPrice().subtract(payPrice);
        dynamicContext.setDeductionPrice(deductionPrice);
        dynamicContext.setPayPrice(payPrice);

        return router(requestParameters, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameters, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        // 不存在配置的拼单活动，走异常节点
        if (null == dynamicContext.getGroupBuyActivityDiscountVO() || null == dynamicContext.getSkuVO() || null == dynamicContext.getDeductionPrice()) {
            return errorNode;
        }

        return tagNode;
    }
}
