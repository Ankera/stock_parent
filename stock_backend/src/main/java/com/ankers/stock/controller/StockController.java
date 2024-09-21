package com.ankers.stock.controller;

import com.ankers.stock.pojo.domain.InnerMarketDomain;
import com.ankers.stock.pojo.domain.StockBlockDomain;
import com.ankers.stock.service.StockService;
import io.swagger.annotations.Api;
import com.ankers.stock.vo.resp.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> getSectorAll() {
        return stockService.getSectorAll();
    }
}
