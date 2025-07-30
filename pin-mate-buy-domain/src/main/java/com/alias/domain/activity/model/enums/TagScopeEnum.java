package com.alias.domain.activity.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description 活动人群标签作用域范围枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TagScopeEnum {

    VISIBLE(true, false, "是否可看见拼团"),
    ENABLE(true, false, "是否可参与拼团"),
    ;

    private Boolean allow;
    private Boolean refuse;
    private String desc;

}

