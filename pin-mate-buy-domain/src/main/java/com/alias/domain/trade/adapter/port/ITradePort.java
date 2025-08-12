package com.alias.domain.trade.adapter.port;

import com.alias.domain.trade.model.entity.NotifyTaskEntity;

/**
 * @description 交易接口服务接口
 */
public interface ITradePort {

    String groupBuyNotify(NotifyTaskEntity notifyTask) throws Exception;

}

