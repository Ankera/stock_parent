package com.ankers.stock.mq;

import com.ankers.stock.mapper.StockRtInfoMapper;
import com.ankers.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.benmanes.caffeine.cache.Cache;

import java.util.Date;

/**
 * 定义股票MQ消息监听
 */
@Component
@Slf4j
public class StockMQMsgListener {

    @Autowired
    private Cache<String, Object> caffeineCache;

    @Autowired
    private StockService stockService;

    @RabbitListener(queues = "innerMarketQueue")
    public void refreshInnerMarketInfo(Date startTime) {
        long diffTime = DateTime.now().getMillis() - new DateTime(startTime).getMillis();
        if (diffTime > 60000) {
            log.error("大盘发送的时间是:{}, 延迟时间: {}", new DateTime(startTime).toString("yyyy-MM-dd HH:mm:ss"), diffTime);
        }

        caffeineCache.invalidate("innerMarketKey");

        stockService.getInnerMarketInfo();
    }
}
