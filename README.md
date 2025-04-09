# Projeto Hackaton - SOAT8 - Grupo 18 Pós-Tech - FIAP

![Kotlin](https://img.shields.io/badge/Kotlin-1.5.31-blue)
![SpringBoot](https://img.shields.io/badge/SpringBoot-3.4-green)
![Docker](https://img.shields.io/badge/Docker-20.10-blue)
![Postgres](https://img.shields.io/badge/Postgres-16-blue)

## Problema
O Grupo 18 da Pós-Graduação em Arquitetura de Software da FIAP busca aprimorar seu projeto de processamento de imagens. Atualmente, o grupo conta com um protótipo simples capaz de processar um vídeo e gerar um arquivo .zip contendo as imagens extraídas. O objetivo agora é desenvolver uma versão mais robusta, permitindo o envio de vídeos e o download do arquivo .zip diretamente. Essa nova versão deverá incorporar conceitos como desenho de arquitetura, desenvolvimento de microsserviços, qualidade de software, mensageria e segurança.

## Escopo
O objetivo do projeto é disponibilizar APIs processamento e download de videos:

- upload de videos
- listar videos
- download de videos

## Requisitos Funcionais e Técnicos
Funcionalidades principais:
•	Processamento simultâneo de múltiplos vídeos.
•	Garantia de que nenhuma requisição seja perdida, mesmo em situações de pico.
•	Implementação de autenticação por usuário e senha para maior segurança.
•	Exibição de uma listagem com o status dos vídeos de cada usuário.
•	Notificação ao usuário em caso de erro (via e-mail ou outros meios de comunicação).
Requisitos técnicos:
•	Persistência dos dados.
•	Arquitetura escalável para suportar crescimento e maior demanda.
•	Versionamento do projeto no GitHub.
•	Garantia de qualidade por meio de testes bem estruturados.
•	Implementação de CI/CD (Integração Contínua e Entrega Contínua).

## Entregáveis
1.	Documentação: Descrição da arquitetura proposta e detalhes técnicos.
2.	Scripts: Arquivos para criação do banco de dados e de outros recursos utilizados.
3.	Repositório: Link do projeto versionado no GitHub.
4.	Apresentação: Vídeo de até 10 minutos apresentando a documentação, a arquitetura escolhida e o sistema em funcionamento.

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

![Diagrama Fase Hackaton.jpg](Diagrama%20Fase%20Hackaton.jpg)

## SonarQube

## Link do Miro https://miro.com/app/board/uXjVK5FMZfo=/?share_link_id=705083359492

## Link Video Fase 5

