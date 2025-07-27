package com.alias.domain.activity.adapter.repository;

import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.alias.domain.activity.model.valobj.SkuVO;

public interface IActivityRepository {

    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String source, String channel);

    SkuVO querySkuByGoodsId(String goodsId);
    
}
