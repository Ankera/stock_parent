package com.ankers.stock.service.impl;

import com.ankers.stock.mapper.StockMarketIndexInfoMapper;
import com.ankers.stock.mapper.StockRtInfoMapper;
import com.ankers.stock.pojo.domain.InnerMarketDomain;
import com.ankers.stock.pojo.domain.StockBlockDomain;
import com.ankers.stock.pojo.domain.StockUpDownDomain;
import com.ankers.stock.pojo.vo.StockInfoConfig;
import com.ankers.stock.service.StockService;
import com.ankers.stock.utils.DateTimeUtil;
import com.ankers.stock.vo.resp.PageResult;
import com.ankers.stock.vo.resp.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

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
     */
    @Override
    public R<List<StockBlockDomain>> getSectorIndustry() {
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();

        curDate = DateTime.parse( "2021-12-21 14:30:00", DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:Ss")).toDate();

        List<StockBlockDomain> data = stockMarketIndexInfoMapper.getSectorIndustry(curDate);
        return R.ok(data);
    }

    @Override
    public R<List<PageResult<StockUpDownDomain>>> getSectorInfoByPage(Integer page, Integer pageSize) {
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();

        curDate = DateTime.parse( "2021-12-30 09:42:00", DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:Ss")).toDate();

        PageHelper.startPage(page, pageSize);

        List<StockUpDownDomain> pageData = stockRtInfoMapper.getStockInfoByTime(curDate);

        PageInfo<StockUpDownDomain> pageInfo = new PageInfo<>(pageData);

        PageResult<StockUpDownDomain> pageResult = new PageResult<>(pageInfo);

        return R.ok(Collections.singletonList(pageResult));
    }

    /**
     * 统计股票最新交易日内每分钟涨跌停的股票数量
     */
    @Override
    public R<Map<String, List>> getStockUpDownCount() {
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        curDateTime = DateTime.parse( "2022-01-06 14:25:00", DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:Ss"));
        Date endDate = curDateTime.toDate();

        Date startDate = DateTimeUtil.getOpenDate(curDateTime).toDate();

        // 涨停
        List<Map> upList = stockRtInfoMapper.getStockUpDownCount(startDate, endDate, 0);

        // 跌停
        List<Map> downList = stockRtInfoMapper.getStockUpDownCount(startDate, endDate, 1);

        Map<String, List> map = new HashMap<>();

        map.put("upList", upList);
        map.put("downList", downList);
        return R.ok(map);
    }
}
