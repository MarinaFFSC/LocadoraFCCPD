Locadora Online 

Este projeto implementa um sistema de envio de mensagens de uma locadora online para os clientes, utilizando **RabbitMQ** como intermediador.

O Cliente (consumidor) escolhe quais catálogos de filmes ele deseja ter acesso, a partir da escolha de categorias de filmes, e então a Locadora ( Produtor) envia  o seu catálogo de filmes de cada categoria para os consumidores que escolheram determinada categoria.

 A aplicação é dividida em três componentes principais: Produtor, Consumidor e Backend de Auditoria, o qual salva todas as mensagens enviadas pelo Produtor independente da classe de filmes.


## Estrutura do Projeto

- **Produtor (Administração da Locadora)**:  Envia a Lista de Filmes Disponíveis na Locadora para o Consumidor que escolheu determinada classe (categoria) de filme.
- **Consumidor (Cliente (s))**: Recebe as mensagens do produtor.
- **Backend de Auditoria**: Armazena todas as mensagens enviadas pelo produtor.

  
## Execução do Projeto

1.Importar projeto na IDE para Java;

2.Abra a IDE para Python (ex: VsCode) e copie nele a classe prodmessenger, agora adquirindo papel de uma pasta, com seus arquivos .py dentro dela;

3.Execute audit.backend.py - Na IDE com o arquivo Python;

4.Execute  consumer.py ( Pode executar outros consumidores) -  Na IDE com o arquivo Python;

5.Execute LocadoraOficialApplication - Na IDE em Java;

6.No consumer.py, escolha os canais de filmes desejados para recebimento do catálogo;

7.No Terminal da IDE do arquivo Java escolha a opção: 1. Produtor de mensagens, para enviar as listas para cada canal de filmes;

8.Escolha o canal de filme que enviará a lista para os consumidores digitando o número correspondente ao canal desejado;

9.Repita o passo 5 até não querer enviar mais mensagens para os produtores;

10.Para parar a execução digite 2 no Terminal da IDE em Java;


## Cenários de Teste
###  Como Produtor: 
- Escolha a opção 1 para enviar mensagens através do produtor, depois escolher o canal de filmes que será enviado para o consumidor.
  
  Checar se:
  
   -Ele confirma se está enviando a lista de filmes, bem como o status da conexão;
  
   -Se após ele enviar a mensagem ele volta para o Menu de opções: 1. Produtor de mensagens - Enviar Lista de Filmes, 2. Sair

- Verificar se está sendo possível enviar várias mensagens,uma após a outra. Sendo o fluxo como descrito acima, após ele escolher o canal a ser enviado (ex: 3. romance), ele mostre que a mensagem está sendo enviada, e depois volte para o Menu.
-  Verificar que o sistema encerra quando o 2. Sair. é apertado.


###  Como Consumidor: 
- Escolha apenas uma opção de canal de filme para conferir se chega só uma (ex: terror);

- Escolha mais de uma opção de canal de filme para conferir se chegará as duas (ex: comedia, terror);

- Execute dois consumidores diferentes ao mesmo tempo e confira se as mensagens continuam chegando corretamente em cada um deles, ou seja, se cada consumidor continua recebendo apenas os canais de filmes nas quais se cadastrou;

- Verifique se os consumidores estão recebendo apenas as mensagens dos canais escolhidos;

- Verifique se todas as mensagens enviadas pelo Produtor estão sendo armazenadas em Auditoria.


###Tecnologias Utilizadas
- **Java**
- **Spring Boot 3.3.3**
- **RabbitMQ**
- **Maven**
- **Python 3.x**
- **Pika (biblioteca para RabbitMQ em Python)**
