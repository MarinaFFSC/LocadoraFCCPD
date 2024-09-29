Locadora Online 

Este projeto implementa um sistema de envio e recebimento de mensagens para uma locadora online, utilizando **RabbitMQ** como intermediador. A aplicação é dividida em três componentes principais: **Produtor**, **Consumidor** e **Backend de Auditoria**, que trocam informações sobre o aluguel de filmes, atualização de catálogo e notificações aos clientes.

## Estrutura do Projeto

- **Produtor (Administração da Locadora / Sistema de Pagamentos)**: Envia mensagens sobre operações de aluguel, atualizações de catálogo e pagamentos.
- **Consumidor (Aplicação Cliente / Serviço de Notificação)**: Recebe as mensagens e processa as operações ou envia notificações.
- **Backend de Auditoria**: Armazena logs das mensagens trocadas para garantir integridade, rastreabilidade e auditoria das operações realizadas.

### Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.3**
- **RabbitMQ**
- **Maven**

## Configuração do Ambiente
### 1. Pré-requisitos
- **Java 17+**
- **Maven 3.8+**
- **RabbitMQ instalado** (ou uma instância remota configurada)
- **IDE**: Eclipse ou IntelliJ IDEA

### 2. Importar o projeto na IDE
#### Eclipse:
1. Abra o **Eclipse** e selecione um workspace.
2. No menu superior, vá em **File** > **Import...** > **Existing Maven Projects**.
3. Selecione o diretório raiz do projeto onde estão os arquivos `pom.xml` e clique em **Finish**.
4. Aguarde o Maven baixar as dependências.

### 3. Configuração do RabbitMQ

No arquivo `application.properties`, adicione as credenciais e a URL de conexão do RabbitMQ, além das configurações das exchanges e filas, se necessário:

```properties
spring.rabbitmq.host=<HOST>
spring.rabbitmq.username=<USERNAME>
spring.rabbitmq.password=<PASSWORD>

message.topicExchangeName=locadora.topic.exchange
```

## Execução do Projeto

### 1. Executar o Produtor

#### Eclipse:
1. Clique com o botão direito sobre o arquivo `ProdApplication.java` dentro do pacote **com.example.message**.
2. Selecione **Run As** > **Java Application**.

O **Produtor** enviará mensagens sobre operações de aluguel, atualizações de catálogo e pagamentos para o RabbitMQ.

### 2. Executar o Consumidor

1. Clique com o botão direito sobre o arquivo `ConsApplication.java` dentro do pacote **com.example.message**.
2. Selecione **Run As** > **Java Application**.

O **Consumidor** receberá as mensagens enviadas pelo Produtor, processando as operações ou enviando notificações aos clientes.

## Cenários de Teste

- **Um Produtor e Múltiplos Consumidores**: Testar como as mensagens são distribuídas entre os consumidores e como o Backend de Auditoria registra as transações.
- **Múltiplos Produtores e Consumidores**: Verificar a comunicação em um ambiente com várias instâncias de produtores e consumidores, e como as mensagens são auditadas em tempo real.
- **Auditoria Completa**: Certificar-se de que todas as mensagens enviadas e consumidas são corretamente registradas pelo Backend de Auditoria.
