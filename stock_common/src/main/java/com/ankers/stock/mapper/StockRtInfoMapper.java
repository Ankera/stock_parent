package com.ankers.stock.mapper;

import com.ankers.stock.pojo.domain.Stock4MinuteDomain;
import com.ankers.stock.pojo.domain.StockUpDownDomain;
import com.ankers.stock.pojo.entity.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
* @author yuyayong
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2024-09-10 22:24:16
* @Entity com.ankers.stock.pojo.entity.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    List<StockUpDownDomain> getStockInfoByTime(@Param("curDate") Date curDate);

    /**
     * 统计指定日期范围内股票涨停或者跌停的数量流水
     * @param startDate 开盘时间
     * @param endDate 停盘时间
     * @param flag 1 涨停
     *             0 跌停
     * @return
     */
    List<Map> getStockUpDownCount(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("flag") int flag);

    List<Map> getIncreaseRangeByDate(@Param("curDate") Date curDate);

    List<Stock4MinuteDomain> getStock4MinuteInfo(@Param("openDate") Date openDate, @Param("endDate") Date endDate, @Param("stockCode") String stockCode);
}
