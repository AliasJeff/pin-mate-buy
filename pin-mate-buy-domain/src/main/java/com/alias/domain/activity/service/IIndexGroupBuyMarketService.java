package com.alias.domain.activity.service;

import com.alias.domain.activity.model.entity.MarketProductEntity;
import com.alias.domain.activity.model.entity.TrialBalanceEntity;
import com.alias.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import com.alias.domain.activity.model.valobj.TeamStatisticVO;

import java.util.List;

public interface IIndexGroupBuyMarketService {

    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProduct) throws Exception;

    /**
     * 查询进行中的拼单订单
     *
     * @param activityId  活动ID
     * @param userId      用户ID
     * @param ownerCount  个人数量
     * @param randomCount 随机数量
     * @return 用户拼单明细数据
     */
    List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailList(Long activityId, String userId, Integer ownerCount, Integer randomCount);

    /**
     * 活动拼单队伍总结
     *
     * @param activityId 活动ID
     * @return 队伍统计
     */
    TeamStatisticVO queryTeamStatisticByActivityId(Long activityId);


}
