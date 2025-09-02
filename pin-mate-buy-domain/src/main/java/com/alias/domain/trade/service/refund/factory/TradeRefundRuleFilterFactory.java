package com.alias.domain.trade.service.refund.factory;

import com.alias.domain.trade.model.entity.GroupBuyTeamEntity;
import com.alias.domain.trade.model.entity.MarketPayOrderEntity;
import com.alias.domain.trade.model.entity.TradeRefundBehaviorEntity;
import com.alias.domain.trade.model.entity.TradeRefundCommandEntity;
import com.alias.domain.trade.service.refund.filter.DataNodeFilter;
import com.alias.domain.trade.service.refund.filter.RefundOrderNodeFilter;
import com.alias.domain.trade.service.refund.filter.UniqueRefundNodeFilter;
import com.alias.types.design.framework.link.model2.LinkArmory;
import com.alias.types.design.framework.link.model2.chain.BusinessLinkedList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * 交易退单工程
 */
@Slf4j
@Service
public class TradeRefundRuleFilterFactory {

    @Bean("tradeRefundRuleFilter")
    public BusinessLinkedList<TradeRefundCommandEntity, DynamicContext, TradeRefundBehaviorEntity> tradeRefundRuleFilter(
            DataNodeFilter dataNodeFilter,
            UniqueRefundNodeFilter uniqueRefundNodeFilter,
            RefundOrderNodeFilter refundOrderNodeFilter) {

        // 组装链
        LinkArmory<TradeRefundCommandEntity, DynamicContext, TradeRefundBehaviorEntity> linkArmory =
                new LinkArmory<>("退单规则过滤链",
                        dataNodeFilter,
                        uniqueRefundNodeFilter,
                        refundOrderNodeFilter);

        // 链对象
        return linkArmory.getLogicLink();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        private MarketPayOrderEntity marketPayOrderEntity;

        private GroupBuyTeamEntity groupBuyTeamEntity;

    }

}

