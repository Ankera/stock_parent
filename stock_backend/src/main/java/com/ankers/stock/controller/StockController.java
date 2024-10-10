package com.ankers.stock.controller;

import com.ankers.stock.pojo.domain.*;
import com.ankers.stock.service.StockService;
import com.ankers.stock.vo.resp.PageResult;
import io.swagger.annotations.Api;
import com.ankers.stock.vo.resp.R;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(tags = "股票接口服务")
@RestController
@RequestMapping("/api/quot")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * 获取国内大盘最新的数据
     */
    @ApiOperation(value = "获取国内大盘最新的数据", notes = "获取国内大盘最新的数据", httpMethod = "GET")
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> getInnerMarketInfo() {
        return stockService.getInnerMarketInfo();
    }

    /**
     * 国内板块指数业务分析
     */
    @ApiOperation(value = "国内板块指数业务分析", notes = "国内板块指数业务分析", httpMethod = "GET")
    @GetMapping("/sector/industry")
    public R<List<StockBlockDomain>> getSectorIndustry() {
        return stockService.getSectorIndustry();
    }

    /**
     * 根据分页查询最新股票交易数据
     * @param page
     * @param pageSize
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "")
    })
    @ApiOperation(value = "根据分页查询最新股票交易数据", notes = "根据分页查询最新股票交易数据", httpMethod = "GET")
    @GetMapping("/stock/all")
    public R<PageResult<StockUpDownDomain>> getSectorInfoByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return stockService.getSectorInfoByPage(page, pageSize);
    }

    /**
     * 统计股票最新交易日内每分钟涨跌停
     * 涨跌停的股票数量
     * @return
     */
    @ApiOperation(value = "统计股票最新交易日内每分钟涨跌停", notes = "统计股票最新交易日内每分钟涨跌停", httpMethod = "GET")
    @GetMapping("/stock/updown/count")
    public R<Map<String, List>> getStockUpDownCount() {
        return stockService.getStockUpDownCount();
    }

    /**
     * 导出指定页码最新股票信息
     * @param page
     * @param pageSize
     * @param response
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "")
    })
    @ApiOperation(value = "导出指定页码最新股票信息", notes = "导出指定页码最新股票信息", httpMethod = "GET")
    @GetMapping("/stock/export")
    public void exportStockUpDownInfo(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                      HttpServletResponse response) {
        stockService.exportStockUpDownInfo(page, pageSize, response);
    }

    /**
     * 统计T日和T-1日每分钟交易量
     * @return
     */
    @ApiOperation(value = "统计T日和T-1日每分钟交易量", notes = "统计T日和T-1日每分钟交易量", httpMethod = "GET")
    @GetMapping("/stock/tradeAmt")
    public R<Map<String, List>> getCompareStockTradeAmt() {
        return stockService.getCompareStockTradeAmt();
    }

    /**
     * 统计最新交易时间点下股票(A股)在各个涨幅区间的数量
     * @return
     */
    @ApiOperation(value = "统计最新交易时间点下股票(A股)在各个涨幅区间的数量", notes = "统计最新交易时间点下股票(A股)在各个涨幅区间的数量", httpMethod = "GET")
    @GetMapping("/stock/upDown")
    public R<Map> getIncreaseRange() {
        return stockService.getIncreaseRange();
    }

    /**
     * 获取指定股票T分时数据
     * @param code
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "code", value = "", required = true)
    })
    @ApiOperation(value = "获取指定股票T分时数据", notes = "获取指定股票T分时数据", httpMethod = "GET")
    @GetMapping("/stock/screen/time-sharing")
    public R<List<Stock4MinuteDomain>> getStockScreenTimeSharing(@RequestParam(value = "code", required = true) String code) {
        return stockService.getStockScreenTimeSharing(code);
    }

    /**
     * 获取指定股票T天数据
     * @param code
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "code", value = "", required = true)
    })
    @ApiOperation(value = "获取指定股票T天数据", notes = "获取指定股票T天数据", httpMethod = "GET")
    @GetMapping("/stock/screen/dkline")
    public R<List<Stock4EvrDayDomain>> getStockScreenDKline(@RequestParam(value = "code", required = true) String code) {
        return stockService.getStockScreenDKline(code);
    }
}

