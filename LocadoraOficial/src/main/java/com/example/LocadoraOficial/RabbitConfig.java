package com.example.LocadoraOficial;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "movie_topic_exchange";

    @Bean
    public TopicExchange movieExchange() {
        return new TopicExchange(EXCHANGE_NAME, false, false);
    }
}
