package com.ankers.stock.pojo.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "stock")
//@Component
public class StockInfoConfig {

    /**
     * 封装国内A股大盘的集合
     */
    private List<String> inner;

    /**
     * 外盘的编码集合
     */
    private List<String> outer;
}
