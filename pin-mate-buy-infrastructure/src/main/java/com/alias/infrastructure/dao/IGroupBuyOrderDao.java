package com.alias.infrastructure.dao;

import com.alias.infrastructure.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 用户拼单
 */
@Mapper
public interface IGroupBuyOrderDao {

    void insert(GroupBuyOrder groupBuyOrder);

    int updateAddLockCount(String teamId);

    int updateSubtractionLockCount(String teamId);

    GroupBuyOrder queryGroupBuyProgress(String teamId);

    GroupBuyOrder queryGroupBuyTeamByTeamId(String teamId);

    int updateAddCompleteCount(String teamId);

    int updateOrderStatus2COMPLETE(String teamId);


}

