package com.ankers.stock.mapper;

import com.ankers.stock.pojo.entity.StockOuterMarketIndexInfo;

/**
* @author yuyayong
* @description 针对表【stock_outer_market_index_info(外盘详情信息表)】的数据库操作Mapper
* @createDate 2024-09-10 22:24:16
* @Entity com.ankers.stock.pojo.entity.StockOuterMarketIndexInfo
*/
public interface StockOuterMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockOuterMarketIndexInfo record);

    int insertSelective(StockOuterMarketIndexInfo record);

    StockOuterMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockOuterMarketIndexInfo record);

    int updateByPrimaryKey(StockOuterMarketIndexInfo record);

}
