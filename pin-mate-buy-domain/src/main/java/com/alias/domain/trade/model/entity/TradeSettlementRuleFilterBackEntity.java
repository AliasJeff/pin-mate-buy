package com.alias.domain.trade.model.entity;

import com.alias.domain.trade.model.valobj.NotifyConfigVO;
import com.alias.types.enums.GroupBuyOrderEnumVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description 拼单交易结算规则反馈
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeSettlementRuleFilterBackEntity {

    /**
     * 拼单组队ID
     */
    private String teamId;
    /**
     * 活动ID
     */
    private Long activityId;
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
    /**
     * 状态（0-拼单中、1-完成、2-失败）
     */
    private GroupBuyOrderEnumVO status;
    /**
     * 拼单开始时间 - 参与拼单时间
     */
    private Date validStartTime;
    /**
     * 拼单结束时间 - 拼单有效时长
     */
    private Date validEndTime;
    /**
     * 回调配置
     */
    private NotifyConfigVO notifyConfigVO;

}

