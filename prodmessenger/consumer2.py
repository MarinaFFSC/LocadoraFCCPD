import pika


def callback(ch, method, properties, body):
    print(f"[{method.routing_key}] Recebido: {body.decode()}")

# Configurações de conexão usando CloudAMQP
url = 'amqps://ugybiebn:etTveVkK14vSlunhUI_jKR-8mz8cSFmp@jackal.rmq.cloudamqp.com/ugybiebn'
params = pika.URLParameters(url)
connection = pika.BlockingConnection(params)
channel = connection.channel()

channel.exchange_declare(exchange='movie_topic_exchange', exchange_type='topic')

# Consumidor escolhe os canais de filmes em uma linha, separados por vírgula
canais_escolhidos = input("Escolha o(s) canal(is) de filme (ex: comedia, terror, romance, acao, scifi): ")

# Divide a entrada em uma lista de categorias
categorias = [categoria.strip() for categoria in canais_escolhidos.split(",")]

# Criação da fila temporária e vínculo com as chaves de roteamento
result = channel.queue_declare(queue='', exclusive=True)
queue_name = result.method.queue

# Vincula a fila a cada chave de roteamento para as categorias escolhidas
for categoria in categorias:
    routing_key = f"filme.{categoria}"
    channel.queue_bind(exchange='movie_topic_exchange', queue=queue_name, routing_key=routing_key)

print(f"Aguardando mensagens nos canais {', '.join(categorias)}...")

channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)
channel.start_consuming()
