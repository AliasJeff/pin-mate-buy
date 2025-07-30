package com.alias.domain.activity.service.discount;

import com.alias.domain.activity.model.enums.DiscountTypeEnum;
import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

public abstract class AbstractDiscountCalculateService implements IDiscountCalculateService {

    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {

        // 人群标签过滤
        if (DiscountTypeEnum.TAG.equals(groupBuyDiscount.getDiscountType())) {
            if (!isInCrowdRange(userId, groupBuyDiscount)) {
                return originalPrice;
            }
        }

        // 折扣优惠计算
        return doCalculate(originalPrice, groupBuyDiscount);
    }

    private boolean isInCrowdRange(String userId, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        // TODO implement isInCrowdRange / isInCrowdTags
        return true;
    }

    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);
}
