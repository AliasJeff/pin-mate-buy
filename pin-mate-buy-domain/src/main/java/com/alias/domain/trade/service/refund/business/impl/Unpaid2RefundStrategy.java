package com.alias.domain.trade.service.refund.business.impl;

import com.alias.domain.trade.adapter.repository.ITradeRepository;
import com.alias.domain.trade.model.aggregate.GroupBuyRefundAggregate;
import com.alias.domain.trade.model.entity.TradeRefundOrderEntity;
import com.alias.domain.trade.service.refund.business.IRefundOrderStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 未支付，未成团；发起退单（未支付），锁单量-1、组队订单状态更新
 */
@Slf4j
@Service("unpaid2RefundStrategy")
public class Unpaid2RefundStrategy implements IRefundOrderStrategy {

    @Resource
    private ITradeRepository repository;

    @Override
    public void refundOrder(TradeRefundOrderEntity tradeRefundOrderEntity) {
        log.info("退单；未支付，未成团 userId:{} teamId:{} orderId:{}", tradeRefundOrderEntity.getUserId(), tradeRefundOrderEntity.getTeamId(), tradeRefundOrderEntity.getOrderId());
        repository.unpaid2Refund(GroupBuyRefundAggregate.buildUnpaid2RefundAggregate(tradeRefundOrderEntity, -1));
    }

}

