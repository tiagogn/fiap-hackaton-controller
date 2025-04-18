# Projeto Hackaton - SOAT8 - Grupo 18 Pós-Tech - FIAP

![Kotlin](https://img.shields.io/badge/Kotlin-1.5.31-blue)
![SpringBoot](https://img.shields.io/badge/SpringBoot-3.4-green)
![Docker](https://img.shields.io/badge/Docker-20.10-blue)
![Postgres](https://img.shields.io/badge/Postgres-16-green)
![Gradle](https://img.shields.io/badge/Gradle-8-green)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=tiagogn_fiap-hackaton-controller&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=tiagogn_fiap-hackaton-controller)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=tiagogn_fiap-hackaton-controller&metric=coverage)](https://sonarcloud.io/summary/new_code?id=tiagogn_fiap-hackaton-controller)

O objetivo do projeto é disponibilizar APIs processamento e download de videos:

- upload de videos
- listar videos
- download de videos

O propjeto foi desenvolvido utilizando as seguintes tecnologias:

- kotlin 1.5.31+
- springboot 3.4+
- docker
- banco de dados postgres 16

## Executando o projeto localmente

Baixe o projeto no seguinte endereço:

- https://github.com/tiagogn/fiap-hackaton-controller
- https://github.com/tiagogn/fiap-hackaton-processor

em seguinte, dentro da pasta do projeto execute o comando

```shell
idea .
```

com isso, o projeto será aberto dentro da IDE **Intellij**

Para rodar o projeto através do Docker Compose, utilize o seguinte comando:

```shell
docker-compose -f docker-compose.yaml up -d
```

ou execute o seguinte comando na pasta do projeto:

```shell
./gradlew bootRun
```

## Swagger

* API (http://localhost:8080/controller/swagger-ui/index.html)

## Postman

Dentro do projeto, existe uma collection do Postman com os endpoints para teste.

* Collection (hackaton/controller/Tech
  Challenge.postman_collection.json) [Download](Fiap%20-%20Lanchonete%20-%20Produto.postman_collection.json)
* Environment (hackaton/controller/Tech
  Challenge.postman_environment.json) [Download](Tech%20Challenge.postman_environment.json)

Em cada endpoint, existe um exemplo de requisição para teste.

Ordem de execução dos endpoints:

1. POST Upload do Video
2. GET Listar Videos
3. GET Download do Video

## Kubernetes

A arquitetura local proposta em Kubernetes visa atender aos requisitos de escalabilidade, permitindo o aumento e
diminuição de Pods de acordo com a demanda. Para isso, implementamos dois Deployments: um para a aplicação e outro para
o banco de dados.

A arquitetura local proposta em Kubernetes visa atender aos requisitos de escalabilidade, permitindo o aumento e
diminuição de Pods de acordo com a demanda. Para isso, implementamos dois Deployments: um para a aplicação e outro para
o banco de dados.

#### Deployments:

Deployment da Aplicação: Inicializa com 3 Pods.

Projetado para escalar automaticamente conforme a carga.

Deployment do Banco de Dados: Inicia com 1 Pod. Gerencia a persistência e integridade dos dados.

Configuração do Horizontal Pod Autoscaler (HPA) Para otimizar a escalabilidade da aplicação, configuramos o Horizontal
Pod Autoscaler (HPA) com as seguintes especificações:

#### Métricas de Escalonamento:

Mínimo de Pods: 2 Pods devem estar sempre disponíveis para garantir a continuidade do serviço. Máximo de Pods: O limite
é de 10 Pods para evitar sobrecarga e garantir uso eficiente dos recursos.

#### Critério de Escalonamento:

Quando um Pod atingir 80% de consumo da CPU, o HPA acionará a criação de um novo Pod. Isso assegura que a aplicação
mantenha um desempenho ideal mesmo em picos de demanda.

Fluxo de Trabalho Monitoramento: O HPA monitora continuamente o uso de CPU dos Pods da aplicação.

Ajuste Dinâmico: À medida que a demanda aumenta e um ou mais Pods atingem o limite de 80% de uso da CPU, novos Pods são
criados automaticamente até o limite máximo de 10.

Escalonamento para Baixa Demanda: Quando a demanda diminui, o HPA pode reduzir o número de Pods, mantendo sempre pelo
menos 2 Pods em operação

Arquitetura To Be Kubernetes na CLOUD

[IMAGEM AQUI]

### Para rodar o projeto no Kubernetes, utilize os seguintes comandos, dentro da pasta raíz do projeto:
[ATUALIZAR AQUI]
```shell
kubectl create namespace lanchonete

kubectl apply -R -f manifests/

kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

kubectl get pods -n lanchonete //lista os pods

kubectl top pods -n lanchonete  //exibe métrica de consumo dos pods
 
```

## SonarQube

## Link do Miro

## Link Video Fase 5

