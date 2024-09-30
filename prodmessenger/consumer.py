import pika


def callback(ch, method, properties, body):
    print(f"[{method.routing_key}] Recebido: {body.decode()}")

# Configurações de conexão usando CloudAMQP
url = 'amqps://ugybiebn:etTveVkK14vSlunhUI_jKR-8mz8cSFmp@jackal.rmq.cloudamqp.com/ugybiebn'
params = pika.URLParameters(url)
connection = pika.BlockingConnection(params)
channel = connection.channel()

channel.exchange_declare(exchange='movie_topic_exchange', exchange_type='topic')

result = channel.queue_declare(queue='', exclusive=True)
queue_name = result.method.queue

canal_escolhido = input("Escolha o canal de filme (comedia, terror, romance, acao, scifi): ")
routing_key = f"filme.{canal_escolhido}"

channel.queue_bind(exchange='movie_topic_exchange', queue=queue_name, routing_key=routing_key)

print(f"Aguardando mensagens no canal '{canal_escolhido}'...")

channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)
channel.start_consuming()

