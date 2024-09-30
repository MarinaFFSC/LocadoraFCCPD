package com.example.message;

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
public class AuditApplication {

    static final String topicExchangeName = "topic-exchange";
    static final String auditQueueName = "audit-queue";
    
    // Este backend escuta todas as mensagens no exchange
    static final String auditRoutingKeyPattern = "#"; // Escuta todas as mensagens

    @Bean
    Queue auditQueue() {
        return new Queue(auditQueueName, false, false, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName, false, true);
    }

    @Bean
    Binding auditBinding() {
        // O backend de auditoria vai escutar todas as mensagens no sistema
        return BindingBuilder.bind(auditQueue()).to(exchange()).with(auditRoutingKeyPattern);
    }

    @Bean
    SimpleMessageListenerContainer auditContainer(ConnectionFactory connectionFactory,
                                                  MessageListenerAdapter auditListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(auditQueueName);
        container.setMessageListener(auditListenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter auditListenerAdapter(AuditReceiver auditReceiver) {
        return new MessageListenerAdapter(auditReceiver, "auditMessage");
    }

    public static void main(String[] args) {
        SpringApplication.run(AuditApplication.class, args);
    }
}

