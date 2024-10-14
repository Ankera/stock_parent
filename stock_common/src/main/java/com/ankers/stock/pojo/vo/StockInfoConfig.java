package com.ankers.stock.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ApiModel
@Data
@ConfigurationProperties(prefix = "stock")
//@Component
public class StockInfoConfig {

    /**
     * 封装国内A股大盘的集合
     */
    @ApiModelProperty("封装国内A股大盘的集合")
    private List<String> inner;

    /**
     * 外盘的编码集合
     */
    @ApiModelProperty("外盘的编码集合")
    private List<String> outer;

    /**
     * 股票涨幅区间标题集合
     */
    @ApiModelProperty("股票涨幅区间标题集合")
    private List<String> upDownRange;

    /**
     * 大盘 外盘 个股的公共URL
     */
    @ApiModelProperty("大盘 外盘 个股的公共URL")
    private String marketUrl;

    /**
     * 板块采集URL
     */
    @ApiModelProperty("板块采集URL")
    private String blockUrl;
}
