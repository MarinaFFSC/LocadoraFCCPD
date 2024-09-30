package com.example.LocadoraOficial;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
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
            System.out.println("1. Produtor de mensagens - Enviar Lista de Filmes");
            System.out.println("2. Sair");

            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1":
                    runProducer();
                    break;
                case "2":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private void runProducer() {
        
        Map<String, String[]> movieMap = new HashMap<>();
        movieMap.put("comedia", new String[]{"\nO Auto da Compadecida\n As Branquelas\n Gente Grande\n Todo mundo em pânico\n Se Beber, Não Case"});
        movieMap.put("terror", new String[]{"\nInvocação do Mal\n Halloween\n O exorcista\n Heredirátio\n Corra"});
        movieMap.put("romance", new String[]{"\n Diário de uma Paixão\n Titanic\n Orgulho e Preconceito\n La la land\n Como Eu Era Antes de Você"});
        movieMap.put("acao", new String[]{"\nVelozes e Furiosos\n Missão Impossível\n Mad Max: Estrada da Fúria\n John Wick\n Duro de Matar"});
        movieMap.put("scifi", new String[]{"\nInterestelar\n Matrix\n Star Wars\n Blade Runner 2049\n O Exterminador do Futuro"});

        
        String[] movieCategories = {"comedia", "terror", "romance", "acao", "scifi"};

        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o canal de filme:");
        for (int i = 0; i < movieCategories.length; i++) {
            System.out.println((i + 1) + " - " + movieCategories[i]);
        }

        int choice = scanner.nextInt();
        scanner.nextLine();  // Limpa  buffer de nova linha

        // Seleciona a categoria baseada na escolha do usuário
        String selectedCategory = movieCategories[choice - 1];
        String routingKey = "filme." + selectedCategory;

        // Obtém a lista de filmes correspondente à categoria
        String[] selectedMovies = movieMap.get(selectedCategory);
        String movieList = "Lista de filmes de " + selectedCategory + ": " + String.join(", ", selectedMovies);

     
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String timestamp = now.format(formatter);

        // Inclui a data e hora na mensagem
        String messageWithTimestamp = movieList + " | Enviado em: " + timestamp;

        
        System.out.println("Enviando lista de filmes de " + selectedCategory);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, messageWithTimestamp);
    }
}