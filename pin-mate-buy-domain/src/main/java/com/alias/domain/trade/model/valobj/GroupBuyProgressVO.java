package com.alias.domain.trade.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description 拼单进度值对象
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyProgressVO {

    /**
     * 目标数量
     */
    private Integer targetCount;
    /**
     * 完成数量
     */
    private Integer completeCount;
    /**
     * 锁单数量
     */
    private Integer lockCount;

}

