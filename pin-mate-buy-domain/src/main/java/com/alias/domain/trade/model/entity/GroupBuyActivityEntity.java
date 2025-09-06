package com.alias.domain.trade.model.entity;

import com.alias.types.enums.ActivityStatusEnumVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description 拼单活动实体对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyActivityEntity {

    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 折扣ID
     */
    private String discountId;
    /**
     * 拼单方式（0自动成团、1达成目标拼单）
     */
    private Integer groupType;
    /**
     * 拼单次数限制
     */
    private Integer takeLimitCount;
    /**
     * 拼单目标
     */
    private Integer target;
    /**
     * 拼单时长（分钟）
     */
    private Integer validTime;
    /**
     * 活动状态（0创建、1生效、2过期、3废弃）
     */
    private ActivityStatusEnumVO status;
    /**
     * 活动开始时间
     */
    private Date startTime;
    /**
     * 活动结束时间
     */
    private Date endTime;
    /**
     * 人群标签规则标识
     */
    private String tagId;
    /**
     * 人群标签规则范围
     */
    private String tagScope;

}

