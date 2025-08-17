package com.alias.domain.trade.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description 回调方式枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum NotifyTypeEnumVO {

    HTTP("HTTP", "HTTP 回调"),
    MQ("MQ", "MQ 消息通知"),
    ;

    private String code;
    private String info;

}

