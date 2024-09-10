package com.ankers.stock.mapper;

import com.ankers.stock.pojo.entity.StockBlockRtInfo;

/**
* @author yuyayong
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2024-09-10 22:24:16
* @Entity com.ankers.stock.pojo.entity.StockBlockRtInfo
*/
public interface StockBlockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

}
