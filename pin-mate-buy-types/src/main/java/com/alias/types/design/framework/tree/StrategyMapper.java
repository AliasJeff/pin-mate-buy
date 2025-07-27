package com.alias.types.design.framework.tree;

/**
 * 策略映射器
 *
 * @param <T> 入参类型
 * @param <D> 动态上下文
 * @param <R> 返回类型
 */
public interface StrategyMapper<T, D, R> {

    /**
     * 获取待执行策略
     *
     * @param requestParameters 入参
     * @param dynamicContext    动态上下文
     * @return 返回参数
     * @throws Exception 异常
     */
    StrategyHandler<T, D, R> get(T requestParameters, D dynamicContext) throws Exception;

}
