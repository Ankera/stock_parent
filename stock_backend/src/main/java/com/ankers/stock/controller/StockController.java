package com.ankers.stock.controller;

import com.ankers.stock.pojo.domain.InnerMarketDomain;
import com.ankers.stock.pojo.domain.StockBlockDomain;
import com.ankers.stock.pojo.domain.StockUpDownDomain;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(value = "/api/quot", tags = {"定义股票相关控制器"})
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
    @GetMapping("/sector/all")
    public R<List<PageResult<StockUpDownDomain>>> getSectorInfoByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
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
}
