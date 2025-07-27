package com.alias.domain.activity.service.trial.node;

import com.alias.domain.activity.model.entity.MarketProductEntity;
import com.alias.domain.activity.model.entity.TrialBalanceEntity;
import com.alias.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.alias.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.alias.types.design.framework.tree.StrategyHandler;
import com.alias.types.enums.ResponseCode;
import com.alias.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 根节点
 */
@Slf4j
@Service
public class RootNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private SwitchNode switchNode;

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParameters, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-RootNode userId:{} requestParameter:{}", requestParameters.getUserId(), JSON.toJSONString(requestParameters));
        // 参数判断
        if (StringUtils.isBlank(requestParameters.getUserId()) || StringUtils.isBlank(requestParameters.getGoodsId()) ||
                StringUtils.isBlank(requestParameters.getSource()) || StringUtils.isBlank(requestParameters.getChannel())) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }
        return router(requestParameters, dynamicContext);

    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameters, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return switchNode;
    }
}
