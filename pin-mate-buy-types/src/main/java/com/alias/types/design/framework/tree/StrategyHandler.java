package com.alias.types.design.framework.tree;

/**
 * 策略处理器
 *
 * @param <T> 入参类型
 * @param <D> 动态上下文类型
 * @param <R> 返回类型
 */
public interface StrategyHandler<T, D, R> {

    StrategyHandler DEFAULT = (T, D) -> null;

    R apply(T requestParameters, D dynamicContext) throws Exception;
}
