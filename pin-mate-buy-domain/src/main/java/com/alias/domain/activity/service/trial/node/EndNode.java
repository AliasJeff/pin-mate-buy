package com.alias.domain.activity.service.trial.node;

import com.alias.domain.activity.model.entity.MarketProductEntity;
import com.alias.domain.activity.model.entity.TrialBalanceEntity;
import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.alias.domain.activity.model.valobj.SkuVO;
import com.alias.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.alias.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.alias.types.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 结束节点
 */
@Slf4j
@Service
public class EndNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParameters, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-EndNode userId:{} requestParameter:{}", requestParameters.getUserId(), JSON.toJSONString(requestParameters));

        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicContext.getGroupBuyActivityDiscountVO();
        SkuVO skuVO = dynamicContext.getSkuVO();

        // 返回空结果
        return TrialBalanceEntity.builder()
                .goodsId(skuVO.getGoodsId())
                .goodsName(skuVO.getGoodsName())
                .originalPrice(skuVO.getOriginalPrice())
                .deductionPrice(new BigDecimal("0.00"))
                .targetCount(groupBuyActivityDiscountVO.getTarget())
                .startTime(groupBuyActivityDiscountVO.getStartTime())
                .endTime(groupBuyActivityDiscountVO.getEndTime())
                .isVisible(false)
                .isEnable(false)
                .build();

    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameters, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }
}
