package com.ankers.stock.service;

import com.ankers.stock.pojo.domain.*;
import com.ankers.stock.vo.resp.PageResult;
import com.ankers.stock.vo.resp.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface StockService {

    /**
     * 获取国内大盘最新数据
     * @return
     */
    R<List<InnerMarketDomain>> getInnerMarketInfo();

    R<List<StockBlockDomain>> getSectorIndustry();

    R<PageResult<StockUpDownDomain>> getSectorInfoByPage(Integer page, Integer pageSize);

    R<Map<String, List>> getStockUpDownCount();

    /**
     * 导出指定页码最新股票信息
     * @param page
     * @param pageSize
     * @param response
     */
    void exportStockUpDownInfo(Integer page, Integer pageSize, HttpServletResponse response);

    /**
     * 统计T日和T-1日每分钟交易量
     * @return
     */
    R<Map<String, List>> getCompareStockTradeAmt();

    R<Map> getIncreaseRange();

    R<List<Stock4MinuteDomain>> getStockScreenTimeSharing(String code);

    R<List<Stock4EvrDayDomain>> getStockScreenDKline(String code);
}
