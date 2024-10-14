package com.ankers.stock.service;

/**
 * 定义股票采集数据服务接口
 */
public interface StockTimerTaskService {

    /**
     * 定义国内大盘的实时数据信息
     */
    void getInnerMarketInfo();

    void getStockRtIndex();
}
