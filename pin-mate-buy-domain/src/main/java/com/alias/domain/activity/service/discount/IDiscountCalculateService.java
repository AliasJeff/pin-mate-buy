package com.alias.domain.activity.service.discount;

import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

public interface IDiscountCalculateService {

    BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);

}
