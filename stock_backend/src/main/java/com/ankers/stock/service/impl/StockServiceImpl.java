package com.ankers.stock.service.impl;

import com.ankers.stock.mapper.StockMarketIndexInfoMapper;
import com.ankers.stock.pojo.domain.InnerMarketDomain;
import com.ankers.stock.pojo.domain.StockBlockDomain;
import com.ankers.stock.pojo.vo.StockInfoConfig;
import com.ankers.stock.service.StockService;
import com.ankers.stock.utils.DateTimeUtil;
import com.ankers.stock.vo.resp.R;
import io.swagger.annotations.ApiModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 股票服务实现
 */
@ApiModel(description = "股票服务实现")
@Service("stockService")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    /**
     * 获取最新股票数据
     * @return
     */
    @Override
    public R<List<InnerMarketDomain>> getInnerMarketInfo() {
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();

//        模拟数据
        curDate = DateTime.parse( "2022-05-13 15:00:00", DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:Ss")).toDate();

        List<String> mCodes = stockInfoConfig.getInner();

        List<InnerMarketDomain> data = stockMarketIndexInfoMapper.getMarketInfo(curDate, mCodes);

        return R.ok(data);
    }

    /**
     * 获取行业股票最新数据
     * @return
     */
    @Override
    public R<List<StockBlockDomain>> getSectorAll() {
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();

        curDate = DateTime.parse( "2021-12-21 14:30:00", DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:Ss")).toDate();

        List<StockBlockDomain> data = stockMarketIndexInfoMapper.getSectorAll(curDate);
        return R.ok(data);
    }
}
