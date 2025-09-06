package com.alias.domain.activity.service.trial;

import com.alias.domain.activity.adapter.repository.IActivityRepository;
import com.alias.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.alias.types.design.framework.tree.AbstractMultiThreadStrategyRouter;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * 抽象的拼单营销支撑类
 */
public abstract class AbstractGroupBuyMarketSupport<MarketProductEntity, DynamicParameters, TrialBalanceEntity> extends AbstractMultiThreadStrategyRouter<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    protected long timeout = 500;

    @Resource
    protected IActivityRepository repository;

    @Override
    protected void multiThread(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        // 缺省的方法
    }
}
