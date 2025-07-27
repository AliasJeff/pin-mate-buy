package com.alias.domain.activity.service.discount.impl;

import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.alias.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * N元购优惠计算
 */
@Slf4j
@Service("N")
public class NCalculateService extends AbstractDiscountCalculateService {
    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("N元购优惠策略折扣计算:{}, {}", groupBuyDiscount.getDiscountType().getCode(), groupBuyDiscount.getDiscountType().getInfo());
        return new BigDecimal(groupBuyDiscount.getMarketExpr());
    }
}
