package com.ankers.stock.service;

import com.ankers.stock.pojo.domain.InnerMarketDomain;
import com.ankers.stock.vo.resp.R;

import java.util.List;

public interface StockService {

    /**
     * 获取国内大盘最新数据
     * @return
     */
    R<List<InnerMarketDomain>> getInnerMarketInfo();
}
