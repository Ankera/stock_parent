package com.ankers.stock.service;

import com.ankers.stock.pojo.domain.InnerMarketDomain;
import com.ankers.stock.pojo.domain.StockBlockDomain;
import com.ankers.stock.pojo.domain.StockUpDownDomain;
import com.ankers.stock.vo.resp.PageResult;
import com.ankers.stock.vo.resp.R;

import java.util.List;
import java.util.Map;

public interface StockService {

    /**
     * 获取国内大盘最新数据
     * @return
     */
    R<List<InnerMarketDomain>> getInnerMarketInfo();

    R<List<StockBlockDomain>> getSectorIndustry();

    R<List<PageResult<StockUpDownDomain>>> getSectorInfoByPage(Integer page, Integer pageSize);

    R<Map<String, List>> getStockUpDownCount();
}
