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
- **Python 3.x**
- **Pika (biblioteca para RabbitMQ em Python)**

## Configuração do Ambiente
### 1. Pré-requisitos
- **Java 17+**
- **Maven 3.8+**
- **Python 3.x instalado**
- **IDE: Qualquer editor de texto ou IDE que suporte Python (como VSCode, PyCharm, etc.)**
- **RabbitMQ instalado** (ou uma instância remota configurada)
- **IDE**: Eclipse ou IntelliJ IDEA

### 2. Importar o projeto na IDE
#### Eclipse:
1. Abra o **Eclipse** e selecione um workspace.
2. No menu superior, vá em **File** > **Import...** > **Existing Maven Projects**.
3. Selecione o diretório raiz do projeto onde estão os arquivos `pom.xml` e clique em **Finish**.
4. Aguarde o Maven baixar as dependências.

### 2. Importar o projeto na IDE
1. Abra o **VSCode** e selecione a pasta do projeto.
2. Se você ainda não instalou a extensão Python, vá em **Extensões** (ícone do quadrado no menu à esquerda) e procure por **Python**. Instale a extensão oficial da Microsoft.
3. Certifique-se de que o **Python** esteja instalado no seu sistema. Caso não esteja, baixe e instale a versão mais recente de [python.org](https://www.python.org/downloads/).
4. No terminal do **VSCode**, execute o comando abaixo para garantir que a biblioteca `pika` (utilizada para integração com RabbitMQ) esteja instalada:

   ```
   pip install pika
   ```

### 3. Configuração do RabbitMQ

No arquivo `application.properties`, adicione as credenciais e a URL de conexão do RabbitMQ, além das configurações das exchanges e filas, se necessário:

```properties
spring.rabbitmq.host=<HOST>
spring.rabbitmq.username=<USERNAME>
spring.rabbitmq.password=<PASSWORD>

message.topicExchangeName=locadora.topic.exchange
```

## Execução do Projeto

### Passos para Executar o Produtor (Python) e o Consumidor (Java)

#### 1. Executar o **Produtor** em Python no VSCode

1. **Abra o VSCode** e selecione a pasta onde está o arquivo do **produtor.py**.
2. Certifique-se de que o ambiente Python esteja configurado corretamente.
3. No terminal integrado do **VSCode**, navegue até o diretório do seu código Python e execute o produtor com o seguinte comando:

   ```
   python produtor.py
   ```

4. O **Produtor** agora estará enviando mensagens para o RabbitMQ de acordo com a lógica implementada no código.

#### 2. Executar o **Consumidor** em Java no Eclipse

1. **Abra o Eclipse** e carregue o projeto que contém o **Consumidor** em Java.
2. No arquivo de configuração do **Consumidor**, certifique-se de que as credenciais e as configurações de conexão com o RabbitMQ estão corretas e compatíveis com o Produtor.
3. Clique com o botão direito no arquivo `ConsApplication.java` (ou o nome do seu arquivo principal do consumidor) e selecione **Run As** > **Java Application**.

4. O **Consumidor** estará ouvindo as mensagens enviadas pelo Produtor em Python, através do RabbitMQ.

### Interface de Comunicação

- **Produtor (Python)**: Envia as mensagens através do RabbitMQ usando a **Topic Exchange** configurada no código Python.
- **Consumidor (Java)**: Recebe as mensagens do RabbitMQ, conforme a lógica implementada no código Java.

Ambos se comunicam via **RabbitMQ**, e a interface de comunicação entre eles é o protocolo de mensagens fornecido pelo **RabbitMQ**. Você não precisa de uma interface gráfica específica para rodar o produtor e o consumidor; a troca de mensagens acontece através das filas e exchanges configuradas no RabbitMQ.

### Teste dos Componentes

1. **Envie uma mensagem** através do **Produtor (Python)** e verifique se o **Consumidor (Java)** consegue processá-la corretamente.
2. Você pode abrir um terminal no **RabbitMQ Management Interface** (se estiver habilitada) para visualizar as mensagens e as filas sendo processadas.

Se precisar de mais detalhes sobre como ajustar as configurações entre o Produtor e o Consumidor ou rodar ambos em paralelo, é só avisar!

## Cenários de Teste

- **Um Produtor e Múltiplos Consumidores**: Testar como as mensagens são distribuídas entre os consumidores e como o Backend de Auditoria registra as transações.
- **Múltiplos Produtores e Consumidores**: Verificar a comunicação em um ambiente com várias instâncias de produtores e consumidores, e como as mensagens são auditadas em tempo real.
- **Auditoria Completa**: Certificar-se de que todas as mensagens enviadas e consumidas são corretamente registradas pelo Backend de Auditoria.
