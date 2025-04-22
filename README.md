# wsl_examples

## Spring Boot Kafka Microservice (wslkafka)

This is a simple Spring Boot microservice application that demonstrates how to produce and consume messages to/from Kafka. 
It uses Docker to run Kafka and connects to it through the application. We also collect the message we send from a 
Postgres database also located in a WSL Docker

In this exercise we see:

- Java
- Spring Boot
- Kafka
- Postgres
- Docker
- Windows Subsystem for Linux

### Features

- **Producer**: Sends messages to a Kafka topic (`demo-topic`) via a REST endpoint.
- **Consumer**: Listens to messages from Kafka and processes them.
- **MessageRepository**: Fetching data from Postgres

### Prerequisites

Before running this project, ensure you have the following installed:

- Java 11 or higher
- Maven
- Docker (for Kafka and Postgres)
- WSL (Windows Subsystem for Linux) if using Windows

### Kafka, Zookeeper and Postgres Setup with Docker

To run Kafka in Docker (via WSL or directly), you can use the following `docker-compose.yml` setup.

```yaml
version: '3.8'

services:
  zookeeper:
    image: bitnami/zookeeper:latest
    container_name: zookeeper
    ports:
      - "2181:2181"
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka:
    image: bitnami/kafka:3.5
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      - KAFKA_ENABLE_KRAFT=no
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://<YOU_WSL_IP>:9092
      - KAFKA_CFG_BROKER_ID=1
      - ALLOW_PLAINTEXT_LISTENER=yes
    depends_on:
      - zookeeper

  postgres:
    image: postgres:16
    container_name: postgres
    restart: always
    environment:
      POSTGRES_DB: mensajesdb
      POSTGRES_USER: <WSL_USER>
      POSTGRES_PASSWORD: <WSL_PASS>
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data
volumes:
  postgres-data:
```
### Important commands

#### 1. Access Kafka Container
To enter the Kafka container in WSL:

```bash
docker exec -it kafka bash
```
#### 2. List kafka topics
List kafka topic:

```bash
kafka-topics.sh --list --bootstrap-server localhost:9092
```

#### 3. Content topic
View content of a topic:

```bash
kafka-topics.sh --list --bootstrap-server localhost:9092
```
or in real time

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic demo-topic
```
#### 4. Access Postgres container

```bash
docker exec -it postgres bash
```

#### 5. Enter into database

```sql
psql -U useruser -d mensajesdb
```

#### 6. Start Kafka, Zookeeper and Postgres with Docker Compose

```bash
docker-compose up -d
```
