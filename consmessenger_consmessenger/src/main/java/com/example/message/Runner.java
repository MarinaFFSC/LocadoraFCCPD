package com.example.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    @Value("${message.topicExchangeName}")
    private String topicExchangeName;

    @Value("${message.routingKey}")
    private String routingKey;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            String message = "Mensagem de teste " + i;
            System.out.println("Enviando: " + message);
            rabbitTemplate.convertAndSend(topicExchangeName, routingKey, message);
            Thread.sleep(1000); // Pausa de 1 segundo entre envios
        }
        System.out.println("Todas as mensagens enviadas com sucesso!");
    }
}
