import pika


def audit_callback(ch, method, properties, body):
    print(f"[Auditoria] {body.decode()}")

# Configurações de conexão usando CloudAMQP
url = 'amqps://ugybiebn:etTveVkK14vSlunhUI_jKR-8mz8cSFmp@jackal.rmq.cloudamqp.com/ugybiebn'
params = pika.URLParameters(url)
connection = pika.BlockingConnection(params)
channel = connection.channel()

channel.exchange_declare(exchange='movie_topic_exchange', exchange_type='topic')

result = channel.queue_declare(queue='', exclusive=True)
queue_name = result.method.queue

# Captura todas as mensagens do tópico 'filme'
channel.queue_bind(exchange='movie_topic_exchange', queue=queue_name, routing_key="filme.#")

print("Backend de auditoria aguardando mensagens...")

channel.basic_consume(queue=queue_name, on_message_callback=audit_callback, auto_ack=True)
channel.start_consuming()