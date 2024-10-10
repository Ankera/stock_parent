package com.ankers.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.ankers.stock.mapper.StockMarketIndexInfoMapper;
import com.ankers.stock.mapper.StockRtInfoMapper;
import com.ankers.stock.pojo.domain.InnerMarketDomain;
import com.ankers.stock.pojo.domain.Stock4MinuteDomain;
import com.ankers.stock.pojo.domain.StockBlockDomain;
import com.ankers.stock.pojo.domain.StockUpDownDomain;
import com.ankers.stock.pojo.vo.StockInfoConfig;
import com.ankers.stock.service.StockService;
import com.ankers.stock.utils.DateTimeUtil;
import com.ankers.stock.vo.resp.PageResult;
import com.ankers.stock.vo.resp.R;
import com.ankers.stock.vo.resp.ResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 股票服务实现
 */
@ApiModel(description = "股票服务实现")
@Service("stockService")
@Slf4j
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
    public R<PageResult<StockUpDownDomain>> getSectorInfoByPage(Integer page, Integer pageSize) {
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();

        curDate = DateTime.parse( "2021-12-30 09:42:00", DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:Ss")).toDate();

        PageHelper.startPage(page, pageSize);

        List<StockUpDownDomain> pageData = stockRtInfoMapper.getStockInfoByTime(curDate);

        PageInfo<StockUpDownDomain> pageInfo = new PageInfo<>(pageData);

        PageResult<StockUpDownDomain> pageResult = new PageResult<>(pageInfo);

        return R.ok(pageResult);
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

    /**
     * 导出指定页码最新股票信息
     * @param page
     * @param pageSize
     * @param response
     */
    @Override
    public void exportStockUpDownInfo(Integer page, Integer pageSize, HttpServletResponse response) {
        R<PageResult<StockUpDownDomain>> r = this.getSectorInfoByPage(page, pageSize);
        List<StockUpDownDomain> rows = r.getData().getRows();

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        String filename = null;
        try {
            filename = URLEncoder.encode("latest_stock_info", "utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xlsx");
            EasyExcel.write(response.getOutputStream(), StockUpDownDomain.class).sheet("模板").doWrite(rows);
        } catch (IOException e) {
            log.error("当前页面:{} | 每页大小:{} | 当前时间: {} | 异常信息: {}", page, pageSize, e.getMessage(), DateTime.now().toString("yyy-MM-dd HH:mm"));
//            throw new RuntimeException(e);
            // 通知前端异常，稍后重试
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            R<Object> error = R.error(ResponseCode.ERROR);
            try {
                response.getWriter().write(error.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 统计T日和T-1日每分钟交易量
     * @return
     */
    @Override
    public R<Map<String, List>> getCompareStockTradeAmt() {
        DateTime tEndDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        tEndDateTime = DateTime.parse( "2022-01-03 14:40:00", DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:Ss"));
        Date tEndDate = tEndDateTime.toDate();

        // 开盘时间
        Date tStartDate = DateTimeUtil.getOpenDate(tEndDateTime).toDate();

        // 获取 T-1
        DateTime preTEndDateTime = DateTimeUtil.getPreviousTradingDay(tEndDateTime);
        preTEndDateTime = DateTime.parse( "2022-01-02 14:40:00", DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:Ss"));
        Date preTEndDate = preTEndDateTime.toDate();

        Date preTStartDate = DateTimeUtil.getOpenDate(preTEndDateTime).toDate();

        List<Map> tData = stockMarketIndexInfoMapper.getSumAmtInfo(tStartDate, tEndDate, stockInfoConfig.getInner());

        List<Map> preData = stockMarketIndexInfoMapper.getSumAmtInfo(preTStartDate, preTEndDate, stockInfoConfig.getInner());

        Map<String, List> map = new HashMap<>();
        map.put("amtList", tData);
        map.put("yesAmtList", preData);
        return R.ok(map);
    }

    /**
     *
     * @return
     */
    @Override
    public R<Map> getIncreaseRange() {

        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        curDateTime = DateTime.parse( "2022-01-06 09:55:00", DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:Ss"));
        Date endDate = curDateTime.toDate();

        List<Map> infos = stockRtInfoMapper.getIncreaseRangeByDate(endDate);
        List<String> upDownRange = stockInfoConfig.getUpDownRange();

        List<Map> allInfos = new ArrayList<>();
        for (String title: upDownRange) {
            Map temp = null;
            for (Map info : infos) {
                if (info.containsValue(title)){
                    temp = info;
                    break;
                }
            }
            if (temp == null) {
                temp = new HashMap<>();
                temp.put("title", title);
                temp.put("count", 0);
            }

            allInfos.add(temp);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("time", curDateTime.toString("yyyy-MM-dd HH:mm:ss"));
        map.put("infos", allInfos);
        return R.ok(map);
    }

    @Override
    public R<List<Stock4MinuteDomain>> getStockScreenTimeSharing(String stockCode) {
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        endDateTime = DateTime.parse( "2021-12-30 14:30:00", DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:Ss"));
        Date endDate = endDateTime.toDate();
        Date openDate = DateTimeUtil.getOpenDate(endDateTime).toDate();

        List<Stock4MinuteDomain> data = stockRtInfoMapper.getStock4MinuteInfo(openDate, endDate, stockCode);
        return R.ok(data);
    }
}
