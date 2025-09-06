package com.alias.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 营销拼单退单响应对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundMarketPayOrderResponseDTO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 组队ID
     */
    private String teamId;

    /**
     * 退单行为状态码
     */
    private String code;

    /**
     * 退单行为状态信息
     */
    private String info;

}

