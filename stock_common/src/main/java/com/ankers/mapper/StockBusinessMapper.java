package com.ankers.mapper;

import com.ankers.pojo.entity.StockBusiness;

/**
* @author yuyayong
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2024-09-10 00:45:52
* @Entity com.ankers.pojo.entity.StockBusiness
*/
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

}
