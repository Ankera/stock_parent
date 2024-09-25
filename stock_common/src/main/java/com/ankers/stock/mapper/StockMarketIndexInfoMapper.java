package com.ankers.stock.mapper;

import com.ankers.stock.pojo.domain.InnerMarketDomain;
import com.ankers.stock.pojo.domain.StockBlockDomain;
import com.ankers.stock.pojo.entity.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author yuyayong
* @description 针对表【stock_market_index_info(国内大盘数据详情表)】的数据库操作Mapper
* @createDate 2024-09-10 22:24:16
* @Entity com.ankers.stock.pojo.entity.StockMarketIndexInfo
*/
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    /**
     * 根据指定时间点查询大盘数据
     * @param curDate 指定时间点
     * @param marketCodes 编码
     * @return
     */
    List<InnerMarketDomain> getMarketInfo(@Param("curDate") Date curDate, @Param("marketCodes") List<String> marketCodes);

    List<StockBlockDomain> getSectorIndustry(@Param("curDate") Date curDate);

    /**
     * 统计指定日期范围内，指定大盘每分钟的成交量流水信息
     * @param openDate
     * @param endDate
     * @param marketCodes
     * @return List<Map>
     */
    List<Map> getSumAmtInfo(@Param("openDate") Date openDate, @Param("endDate") Date endDate, @Param("marketCodes") List<String> marketCodes);
}
