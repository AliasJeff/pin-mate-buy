package com.alias.api;

import com.alias.api.dto.LockMarketPayOrderRequestDTO;
import com.alias.api.dto.LockMarketPayOrderResponseDTO;
import com.alias.api.response.Response;

/**
 * @description 营销交易服务接口
 */
public interface IMarketTradeService {

    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);

}

