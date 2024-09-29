import pika

# Configurações do RabbitMQ
RABBITMQ_HOST = 'localhost'  # Ou o IP/URL do servidor RabbitMQ
EXCHANGE_NAME = 'topic-exchange'
ROUTING_KEY = 'locadora.filmes.#'

# Conexão com o RabbitMQ
connection = pika.BlockingConnection(pika.ConnectionParameters(host=RABBITMQ_HOST))
channel = connection.channel()

# Declaração da exchange
channel.exchange_declare(exchange=EXCHANGE_NAME, exchange_type='topic')

# Função para enviar mensagens
def enviar_mensagem(mensagem):
    channel.basic_publish(exchange=EXCHANGE_NAME, routing_key=ROUTING_KEY, body=mensagem)
    print(f"Mensagem enviada: {mensagem}")

if __name__ == "__main__":
    print("Digite as mensagens que deseja enviar. Digite 'sair' para encerrar.")
    
    while True:
        mensagem = input("Mensagem: ")
        
        if mensagem.lower() == "sair":
            break
        
        enviar_mensagem(mensagem)
    
    connection.close()
    print("Envio de mensagens encerrado.")
