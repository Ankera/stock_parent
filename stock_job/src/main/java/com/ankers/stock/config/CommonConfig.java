package com.ankers.stock.config;

import com.ankers.stock.pojo.vo.StockInfoConfig;
import com.ankers.stock.utils.IdWorker;
import com.ankers.stock.utils.ParserStockInfoUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StockInfoConfig.class) // 开启配置，随用谁配置
public class CommonConfig {
    /**
     * 配置id生成器
     */
    @Bean
    public IdWorker idWorker(){
        //基于2号机房的1号机器生成的id
        return new IdWorker(1L, 2L);
    }

    @Bean
    public ParserStockInfoUtil parserStockInfoUtil(){
        return new ParserStockInfoUtil(idWorker());
    }
}
