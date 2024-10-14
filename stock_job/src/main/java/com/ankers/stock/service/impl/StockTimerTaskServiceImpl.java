package com.ankers.stock.service.impl;

import com.ankers.stock.mapper.StockBusinessMapper;
import com.ankers.stock.mapper.StockMarketIndexInfoMapper;
import com.ankers.stock.mapper.StockRtInfoMapper;
import com.ankers.stock.pojo.entity.StockMarketIndexInfo;
import com.ankers.stock.pojo.entity.StockRtInfo;
import com.ankers.stock.pojo.vo.StockInfoConfig;
import com.ankers.stock.service.StockTimerTaskService;
import com.ankers.stock.utils.DateTimeUtil;
import com.ankers.stock.utils.IdWorker;
import com.ankers.stock.utils.ParseType;
import com.ankers.stock.utils.ParserStockInfoUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    StockInfoConfig stockInfoConfig;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;

    private HttpEntity<Object> entity;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    /**
     * bean 生命周期初始化之后
     */
    @PostConstruct
    public void initData() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");
        entity = new HttpEntity<>(headers);
    }

    @Override
    public void getInnerMarketInfo() {

        String url = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getInner());

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            log.error("当前时间点: {}, 采集数据失败，状态码: {}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), statusCode);
            // 推送钉钉

            return;
        }

        String jsData = responseEntity.getBody();
        log.info("当前时间点: {}, 采集的数据内容: {}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), jsData);


        // 解析数据
        String reg_str = "var hq_str_(.+)=\"(.+)\"";
        Pattern reg = Pattern.compile(reg_str);
        assert jsData != null;
        Matcher matcher = reg.matcher(jsData);

        List<StockMarketIndexInfo> entities = new ArrayList<>();
        while (matcher.find()) {
            String marketCode = matcher.group(1);

            String otherInfo = matcher.group(2);
            String[] splitArr = otherInfo.split(",");
            BigDecimal openPoint = new BigDecimal(splitArr[1]);
            BigDecimal preClosePoint = new BigDecimal(splitArr[2]);
            BigDecimal curPoint = new BigDecimal(splitArr[3]);
            BigDecimal maxPoint = new BigDecimal(splitArr[4]);
            BigDecimal minPoint = new BigDecimal(splitArr[5]);
            long tradeAmt = Long.parseLong(splitArr[8]);
            BigDecimal tradeVol = new BigDecimal(splitArr[9]);
            Date curTime = DateTimeUtil.getDateTimeWithoutSecond(splitArr[30] + " " + splitArr[31]).toDate();

            StockMarketIndexInfo entityItem = StockMarketIndexInfo.builder()
                    .id(idWorker.nextId())
                    .marketName(splitArr[0])
                    .openPoint(openPoint)
                    .preClosePoint(preClosePoint)
                    .curPoint(curPoint)
                    .maxPoint(maxPoint)
                    .minPoint(minPoint)
                    .tradeAmount(tradeAmt)
                    .tradeVolume(tradeVol)
                    .marketCode(marketCode)
                    .curTime(curTime)
                    .build();

            entities.add(entityItem);
        }

//        log.info("解析数据完毕: {}", entities);

        int count = stockMarketIndexInfoMapper.insertBatch(entities);
        if (count > 0) {
            log.info("当前时间:{}, 插入行数{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), count);
        } else {
            log.error("当前时间:{}, 插入行数{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), entities);
        }
    }

    @Override
    public void getStockRtIndex() {
        List<String> allCodes = stockBusinessMapper.getAllStockCodes();

        allCodes = allCodes.stream().map(code -> code.startsWith("6") ? "sh" + code : "sz" + code).collect(Collectors.toList());

        Lists.partition(allCodes, 15).forEach(codes -> {
            String url = stockInfoConfig.getMarketUrl() + String.join(",", codes);

//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Referer", "https://finance.sina.com.cn/stock/");
//            headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");
//            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode != HttpStatus.OK) {
                log.error("当前时间点: {}, 采集数据失败，状态码: {}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), statusCode);
                // 推送钉钉

                return;
            }

            String jsData = responseEntity.getBody();
            log.info("当前时间点: {}, 采集的数据内容: {}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), jsData);

            List<StockRtInfo> list = parserStockInfoUtil.parser4StockOrMarketInfo(jsData, ParseType.ASHARE);

            int count = stockRtInfoMapper.insertBatch(list);
            if (count > 0) {
                log.info("当前时间:{}, 插入行数{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), count);
            } else {
                log.error("当前时间:{}, 插入行数{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), list);
            }
        });
    }
}
