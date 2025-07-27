package com.alias.infrastructure.dao;

import com.alias.infrastructure.dao.po.Sku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISkuDao {

    Sku querySkuByGoodsId(String goodsId);
}
