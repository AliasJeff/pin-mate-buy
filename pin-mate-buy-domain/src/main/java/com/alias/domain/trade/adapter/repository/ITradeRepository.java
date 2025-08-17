package com.alias.domain.trade.adapter.repository;

import com.alias.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import com.alias.domain.trade.model.aggregate.GroupBuyTeamSettlementAggregate;
import com.alias.domain.trade.model.entity.GroupBuyActivityEntity;
import com.alias.domain.trade.model.entity.GroupBuyTeamEntity;
import com.alias.domain.trade.model.entity.MarketPayOrderEntity;
import com.alias.domain.trade.model.entity.NotifyTaskEntity;
import com.alias.domain.trade.model.valobj.GroupBuyProgressVO;

import java.util.List;

public interface ITradeRepository {

    MarketPayOrderEntity queryMarketPayOrderEntityByOutTradeNo(String userId, String outTradeNo);

    MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate) throws Exception;

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    GroupBuyActivityEntity queryGroupBuyActivityEntityByActivityId(Long activityId);

    Integer queryOrderCountByActivityId(Long activityId, String userId);

    GroupBuyTeamEntity queryGroupBuyTeamByTeamId(String teamId);

    NotifyTaskEntity settlementMarketPayOrder(GroupBuyTeamSettlementAggregate groupBuyTeamSettlementAggregate);

    boolean isSCBlackIntercept(String source, String channel);

    List<NotifyTaskEntity> queryUnExecutedNotifyTaskList();

    List<NotifyTaskEntity> queryUnExecutedNotifyTaskList(String teamId);

    int updateNotifyTaskStatusSuccess(String teamId);

    int updateNotifyTaskStatusError(String teamId);

    int updateNotifyTaskStatusRetry(String teamId);

}
