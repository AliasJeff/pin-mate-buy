package com.alias.domain.trade.model.aggregate;

import com.alias.domain.trade.model.entity.TradeRefundOrderEntity;
import com.alias.domain.trade.model.valobj.GroupBuyProgressVO;
import com.alias.types.enums.GroupBuyOrderEnumVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 拼单退单聚合
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyRefundAggregate {

    /**
     * 交易退单
     */
    private TradeRefundOrderEntity tradeRefundOrderEntity;

    /**
     * 退单进度
     */
    private GroupBuyProgressVO groupBuyProgress;

    /**
     * 拼单枚举
     */
    private GroupBuyOrderEnumVO groupBuyOrderEnumVO;

    public static GroupBuyRefundAggregate buildUnpaid2RefundAggregate(TradeRefundOrderEntity tradeRefundOrderEntity, Integer lockCount) {
        GroupBuyRefundAggregate groupBuyRefundAggregate = new GroupBuyRefundAggregate();
        groupBuyRefundAggregate.setTradeRefundOrderEntity(tradeRefundOrderEntity);
        groupBuyRefundAggregate.setGroupBuyProgress(
                GroupBuyProgressVO.builder()
                        .lockCount(lockCount)
                        .build());
        return groupBuyRefundAggregate;
    }

    public static GroupBuyRefundAggregate buildPaid2RefundAggregate(TradeRefundOrderEntity tradeRefundOrderEntity,
                                                                    Integer lockCount,
                                                                    Integer completeCount) {
        GroupBuyRefundAggregate groupBuyRefundAggregate = new GroupBuyRefundAggregate();
        groupBuyRefundAggregate.setTradeRefundOrderEntity(tradeRefundOrderEntity);
        groupBuyRefundAggregate.setGroupBuyProgress(
                GroupBuyProgressVO.builder()
                        .lockCount(lockCount)
                        .completeCount(completeCount)
                        .build());

        return groupBuyRefundAggregate;
    }

    public static GroupBuyRefundAggregate buildPaidTeam2RefundAggregate(TradeRefundOrderEntity tradeRefundOrderEntity,
                                                                        Integer lockCount,
                                                                        Integer completeCount,
                                                                        GroupBuyOrderEnumVO groupBuyOrderEnumVO) {
        GroupBuyRefundAggregate groupBuyRefundAggregate = new GroupBuyRefundAggregate();
        groupBuyRefundAggregate.setTradeRefundOrderEntity(tradeRefundOrderEntity);
        groupBuyRefundAggregate.setGroupBuyProgress(
                GroupBuyProgressVO.builder()
                        .lockCount(lockCount)
                        .completeCount(completeCount)
                        .build());
        groupBuyRefundAggregate.setGroupBuyOrderEnumVO(groupBuyOrderEnumVO);

        return groupBuyRefundAggregate;
    }

}

