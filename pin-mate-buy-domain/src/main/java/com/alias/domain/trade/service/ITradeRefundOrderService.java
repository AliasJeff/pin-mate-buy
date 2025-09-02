package com.alias.domain.trade.service;

import com.alias.domain.trade.model.entity.TradeRefundBehaviorEntity;
import com.alias.domain.trade.model.entity.TradeRefundCommandEntity;
import com.alias.domain.trade.model.valobj.TeamRefundSuccess;

/**
 * 退单，逆向流程接口
 */
public interface ITradeRefundOrderService {

    TradeRefundBehaviorEntity refundOrder(TradeRefundCommandEntity tradeRefundCommandEntity) throws Exception;

    /**
     * 退单恢复锁单库存
     *
     * @param teamRefundSuccess 退单消息
     * @throws Exception 异常
     */
    void restoreTeamLockStock(TeamRefundSuccess teamRefundSuccess) throws Exception;

}

