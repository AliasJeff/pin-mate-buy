package com.alias.domain.activity.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DiscountTypeEnum {

    BASE(0, "基础优惠"),
    TAG(1, "人群标签优惠");

    private Integer code;
    private String info;

    public static DiscountTypeEnum get(Integer code) {
        switch (code) {
            case 0:
                return BASE;
            case 1:
                return TAG;
            default:
                throw new RuntimeException("Invalid discount code: " + code);
        }
    }
}
