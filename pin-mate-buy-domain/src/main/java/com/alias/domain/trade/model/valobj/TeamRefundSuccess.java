package com.alias.domain.trade.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 拼团退单消息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamRefundSuccess {

    /**
     * 退单类型
     */
    private String type;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 拼单组队ID
     */
    private String teamId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 预购订单ID
     */
    private String orderId;

}

