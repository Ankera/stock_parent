package com.ankers.stock;

import com.ankers.stock.service.impl.StockTimerTaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class TestRestTemplate {

    @Autowired
    private StockTimerTaskServiceImpl stockTimerTaskService;

    @Test
    public void test01() {
        stockTimerTaskService.getInnerMarketInfo();
    }

    @Test
    public void test02() {
        stockTimerTaskService.getStockRtIndex();
    }
}
