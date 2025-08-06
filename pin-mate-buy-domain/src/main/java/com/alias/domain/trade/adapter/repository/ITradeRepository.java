package com.alias.domain.trade.adapter.repository;

import com.alias.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import com.alias.domain.trade.model.entity.GroupBuyActivityEntity;
import com.alias.domain.trade.model.entity.MarketPayOrderEntity;
import com.alias.domain.trade.model.valobj.GroupBuyProgressVO;

public interface ITradeRepository {

    MarketPayOrderEntity queryMarketPayOrderEntityByOutTradeNo(String userId, String outTradeNo);

    MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate) throws Exception;

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    GroupBuyActivityEntity queryGroupBuyActivityEntityByActivityId(Long activityId);

    Integer queryOrderCountByActivityId(Long activityId, String userId);

}
