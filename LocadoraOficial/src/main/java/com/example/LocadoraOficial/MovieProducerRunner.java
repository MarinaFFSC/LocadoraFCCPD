package com.example.LocadoraOficial;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MovieProducerRunner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final static String EXCHANGE_NAME = "movie_topic_exchange";

    public MovieProducerRunner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Escolha uma opção:");
            System.out.println("1. Produtor de mensagens");
            System.out.println("2. Consumidor de mensagens");
            System.out.println("3. Auditoria");
            System.out.println("4. Sair");

            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1":
                    runProducer();
                    break;
                case "2":
                    System.out.println("Executar o consumidor de mensagens Python separadamente.");
                    break;
                case "3":
                    System.out.println("Executar o backend de auditoria Python separadamente.");
                    break;
                case "4":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
        
    }

    private void runProducer() {
        Scanner scanner = new Scanner(System.in);
        String[] movieCategories = {"comedia", "terror", "romance", "acao", "scifi"};
        String[][] movieLists = {
            {"A Ressaca", "As Branquelas", "Superbad: É Hoje", "Se Beber, Não Case", "Zumbilândia"},
            {"Invocação do Mal", "A Morte do Demônio", "O Exorcista", "Hereditário", "It: A Coisa"},
            {"Diário de uma Paixão", "A Culpa é das Estrelas", "Como Eu Era Antes de Você", "Orgulho e Preconceito", "Simplesmente Acontece"},
            {"Mad Max: Estrada da Fúria", "Velozes e Furiosos 7", "John Wick", "Gladiador", "Os Vingadores"},
            {"Interstellar", "Blade Runner 2049", "A Chegada", "Matrix", "Star Wars: O Império Contra-Ataca"}
        };

        System.out.println("Escolha o canal de filme:");
        for (int i = 0; i < movieCategories.length; i++) {
            System.out.println((i + 1) + " - " + movieCategories[i]);
        }

        int choice = scanner.nextInt();
        String selectedCategory = movieCategories[choice - 1];
        String routingKey = "filme." + selectedCategory;

        String[] selectedMovies = movieLists[choice - 1];

        System.out.println("Enviando lista de filmes de " + selectedCategory);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, "Lista de filmes de " + selectedCategory + ": " + String.join(", ", selectedMovies));

        System.out.println("Filme reservado e pronto para retirada.");
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, "Filme reservado e pronto para retirada.");
    }
}
