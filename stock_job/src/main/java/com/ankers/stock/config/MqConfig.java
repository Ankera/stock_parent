package com.ankers.stock.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange stockTopicExchange() {
        return new TopicExchange("stockExchange", true, false);
    }

    @Bean
    public Queue innerMarketQueue() {
        return new Queue("innerMarketQueue", true);
    }

    @Bean
    public Binding bindingInnerMarketQueue() {
        return BindingBuilder.bind(innerMarketQueue()).to(stockTopicExchange()).with("inner.market");
    }
}
