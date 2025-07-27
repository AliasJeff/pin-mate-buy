package com.alias.domain.activity.service.trial.node;

import com.alias.domain.activity.model.entity.MarketProductEntity;
import com.alias.domain.activity.model.entity.TrialBalanceEntity;
import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.alias.domain.activity.model.valobj.SkuVO;
import com.alias.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.alias.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.alias.domain.activity.service.trial.thread.QueryGroupBuyActivityDiscountVOThreadTask;
import com.alias.domain.activity.service.trial.thread.QuerySkuVOFromDBThreadTask;
import com.alias.types.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * 营销优惠节点
 */
@Slf4j
@Service
public class MarketNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private EndNode endNode;

    @Override
    protected void multiThread(MarketProductEntity marketProductEntity, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        QueryGroupBuyActivityDiscountVOThreadTask queryGroupBuyActivityDiscountVOThreadTask = new QueryGroupBuyActivityDiscountVOThreadTask(marketProductEntity.getSource(), marketProductEntity.getChannel(), repository);
        FutureTask<GroupBuyActivityDiscountVO> groupBuyActivityDiscountVOFutureTask = new FutureTask<>(queryGroupBuyActivityDiscountVOThreadTask);
        threadPoolExecutor.execute(groupBuyActivityDiscountVOFutureTask);

        QuerySkuVOFromDBThreadTask querySkuVOFromDBThreadTask = new QuerySkuVOFromDBThreadTask(marketProductEntity.getGoodsId(), repository);
        FutureTask<SkuVO> querySkuFutureTask = new FutureTask<>(querySkuVOFromDBThreadTask);
        threadPoolExecutor.execute(querySkuFutureTask);

        dynamicContext.setGroupBuyActivityDiscountVO(groupBuyActivityDiscountVOFutureTask.get(timeout, TimeUnit.SECONDS));
        dynamicContext.setSkuVO(querySkuFutureTask.get(timeout, TimeUnit.SECONDS));

        log.info("拼团商品查询试算服务-MarketNode userId:{} 异步线程加载数据「GroupBuyActivityDiscountVO、SkuVO」完成", marketProductEntity.getUserId());
    }

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParameters, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-MarketNode userId:{} requestParameter:{}", requestParameters.getUserId(), JSON.toJSONString(requestParameters));

        // todo 拼团优惠试算
        return router(requestParameters, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameters, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }
}
