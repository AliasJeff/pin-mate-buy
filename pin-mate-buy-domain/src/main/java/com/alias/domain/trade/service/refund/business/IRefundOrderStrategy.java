package com.alias.domain.trade.service.refund.business;

import com.alias.domain.trade.model.entity.TradeRefundOrderEntity;

/**
 * 退单策略接口
 * 未支付，Unpaid
 * 未成团，UnformedTeam
 * 已成团，AlreadyFormedTeam
 */
public interface IRefundOrderStrategy {

    void refundOrder(TradeRefundOrderEntity tradeRefundOrderEntity) throws Exception;

}

