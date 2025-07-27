package com.alias.domain.activity.service.discount.impl;

import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.alias.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 折扣优惠计算
 */
@Slf4j
@Service("ZK")
public class ZKCalculateService extends AbstractDiscountCalculateService {
    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        BigDecimal discount = new BigDecimal(groupBuyDiscount.getMarketExpr());

        BigDecimal price = originalPrice.multiply(discount);

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }

        return price;
    }
}
