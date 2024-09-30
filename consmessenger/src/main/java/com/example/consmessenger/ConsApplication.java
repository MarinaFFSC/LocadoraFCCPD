package com.example.consmessenger;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsApplication {

    static final String topicExchangeName = "topic-exchange";
    static final String queueName = "fila-primeira";
    
    // Aqui estamos configurando para receber mensagens com padrões de tópicos
    static final String routingKeyPattern = "aluguel.#";  // Consumidor receberá todas as mensagens relacionadas a aluguel

    @Bean
    Queue queue() {
        return new Queue(queueName, false, false, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName, false, true);
    }

    @Bean
    Binding binding() {
        // Consumidor vai receber todas as mensagens cujo tópico começa com "aluguel"
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKeyPattern);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsApplication.class, args);
    }
}

