package com.alias.types.design.framework.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * 异步资源加载策略
 */
public abstract class AbstractMultiThreadStrategyRouter<T, D, R> implements StrategyMapper<T, D, R>, StrategyHandler<T, D, R> {

    @Getter
    @Setter
    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEFAULT;

    public R router(T requestParameters, D dynamicContext) throws Exception {
        StrategyHandler<T, D, R> strategyHandler = get(requestParameters, dynamicContext);
        if (strategyHandler != null) {
            return strategyHandler.apply(requestParameters, dynamicContext);
        }
        return defaultStrategyHandler.apply(requestParameters, dynamicContext);
    }

    @Override
    public R apply(T requestParameters, D dynamicContext) throws Exception {
        multiThread(requestParameters, dynamicContext);
        return doApply(requestParameters, dynamicContext);
    }

    /**
     * 异步加载数据
     */
    protected abstract void multiThread(T requestParameters, D dynamicContext) throws ExecutionException, InterruptedException, TimeoutException;

    /**
     * 业务流程受理
     */
    protected abstract R doApply(T requestParameters, D dynamicContext) throws Exception;
}
