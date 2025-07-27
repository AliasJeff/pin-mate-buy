package com.alias.domain.activity.service.discount.impl;

import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.alias.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 直减优惠计算
 */
@Slf4j
@Service("ZJ")
public class ZJCalculateService extends AbstractDiscountCalculateService {
    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {

        log.info("ZJ直减优惠策略折扣计算:{}, {}", groupBuyDiscount.getDiscountType().getCode(), groupBuyDiscount.getDiscountType().getInfo());

        BigDecimal amount = new BigDecimal(groupBuyDiscount.getMarketExpr());

        // 直减为扣减金额
        BigDecimal price = originalPrice.subtract(amount);
        // 判断折扣后金额，最低支付1分钱
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }

        return price;
    }
}
