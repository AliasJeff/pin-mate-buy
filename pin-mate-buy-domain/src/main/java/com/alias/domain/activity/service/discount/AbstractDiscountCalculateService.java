package com.alias.domain.activity.service.discount;

import com.alias.domain.activity.adapter.repository.IActivityRepository;
import com.alias.domain.activity.model.enums.DiscountTypeEnum;
import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 折扣计算服务抽象类
 */
@Slf4j
public abstract class AbstractDiscountCalculateService implements IDiscountCalculateService {

    @Resource
    private IActivityRepository repository;

    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {

        // 人群标签过滤
        if (DiscountTypeEnum.TAG.equals(groupBuyDiscount.getDiscountType())) {
            if (!isInCrowdRange(userId, groupBuyDiscount.getTagId())) {
                log.info("折扣优惠计算拦截，用户不再优惠人群标签范围内 userId:{}", userId);
                return originalPrice;
            }

        }

        // 折扣优惠计算
        return doCalculate(originalPrice, groupBuyDiscount);
    }

    private boolean isInCrowdRange(String userId, String tagId) {
        return repository.isInCrowdTags(tagId, userId);
    }

    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);
}
