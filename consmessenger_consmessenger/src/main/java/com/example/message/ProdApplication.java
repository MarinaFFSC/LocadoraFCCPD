package com.example.message;

import java.util.Scanner;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProdApplication {

    static final String topicExchangeName = "topic-exchange";
    static final String routingKey = "locadora.filmes.#"; 

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProdApplication.class, args).getBean(ProdApplication.class).run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite as mensagens que deseja enviar. Digite 'sair' para encerrar.");
        
        while (true) {
            System.out.print("Mensagem: ");
            String message = scanner.nextLine();
            
            if (message.equalsIgnoreCase("sair")) {
                break;
            }

            rabbitTemplate.convertAndSend(topicExchangeName, routingKey, message);
            System.out.println("Mensagem enviada: " + message);
        }

        scanner.close();
        System.out.println("Envio de mensagens encerrado.");
    }
}
