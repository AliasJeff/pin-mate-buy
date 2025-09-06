package com.alias.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description 拼单订单枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum GroupBuyOrderEnumVO {

    PROGRESS(0, "拼单中"),
    COMPLETE(1, "完成"),
    FAIL(2, "失败"),
    COMPLETE_FAIL(3, "完成-含退单"),
    ;

    private Integer code;
    private String info;

    public static GroupBuyOrderEnumVO valueOf(Integer code) {
        switch (code) {
            case 0:
                return PROGRESS;
            case 1:
                return COMPLETE;
            case 2:
                return FAIL;
            case 3:
                return COMPLETE_FAIL;
        }
        throw new RuntimeException("Error: code not exist!");
    }

}

