package com.alias.api;

import com.alias.api.dto.GoodsMarketRequestDTO;
import com.alias.api.dto.GoodsMarketResponseDTO;
import com.alias.api.response.Response;

/**
 * @description 营销首页服务接口
 */
public interface IMarketIndexService {

    /**
     * 查询拼单营销配置
     *
     * @param goodsMarketRequestDTO 营销商品信息
     * @return 营销配置信息
     */
    Response<GoodsMarketResponseDTO> queryGroupBuyMarketConfig(GoodsMarketRequestDTO goodsMarketRequestDTO);

}

