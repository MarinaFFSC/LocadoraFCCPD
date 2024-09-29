import pika
import time

# Configurações do RabbitMQ
RABBITMQ_HOST = 'localhost'
EXCHANGE_NAME = 'topic-exchange'
ROUTING_KEY = 'locadora.filmes.#'

# Função que envia mensagens automaticamente
def enviar_mensagens_automaticas():
    # Conecta ao RabbitMQ
    connection = pika.BlockingConnection(pika.ConnectionParameters(host=RABBITMQ_HOST))
    channel = connection.channel()

    # Declara a exchange
    channel.exchange_declare(exchange=EXCHANGE_NAME, exchange_type='topic')

    # Envia 5 mensagens automaticamente
    for i in range(1, 6):
        message = f"Mensagem de teste {i}"
        channel.basic_publish(exchange=EXCHANGE_NAME, routing_key=ROUTING_KEY, body=message)
        print(f"Enviando: {message}")
        time.sleep(1)  # Pausa de 1 segundo entre as mensagens

    # Fecha a conexão
    connection.close()
    print("Todas as mensagens enviadas com sucesso!")

if __name__ == "__main__":
    enviar_mensagens_automaticas()
