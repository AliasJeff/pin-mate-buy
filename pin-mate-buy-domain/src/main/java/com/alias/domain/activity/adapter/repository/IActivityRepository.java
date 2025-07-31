package com.alias.domain.activity.adapter.repository;

import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.alias.domain.activity.model.valobj.SCSkuActivityVO;
import com.alias.domain.activity.model.valobj.SkuVO;

public interface IActivityRepository {

    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId);

    SkuVO querySkuByGoodsId(String goodsId);

    SCSkuActivityVO querySCSkuActivityBySCGoodsId(String source, String channel, String goodsId);

    boolean isInCrowdTags(String tagId, String userId);

    boolean downgradeSwitch();

    boolean cutRange(String userId);

}
