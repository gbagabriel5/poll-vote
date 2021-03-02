# Poll Vote API
## Pré-requisitos
* Java (openjdk 15)
* Intellij Community / Eclipse
* SpringBoot
* Maven
* Docker

## Instalação
### Kafka e Banco de dados
Com o docker instalado, executar o comando abaixo pelo terminal, dentro da pasta do Docker no projeto.
````
docker-compose -f docker-compose.yml up -d
````

### Executando a API
Para realizar os testes da api é preciso ter o **Maven** e **Java 15** instalados.
````
mvn clean spring-boot:run
````
### Executando os testes da API
Para realizar os testes da api é preciso ter o **Maven** e **Java 15** instalados.
````
mvn clean test
````
## Kafka
O resultado da votação é enviado em forma de mensagem através do topico "result".

## API REST HEROKU CLOUD
Para ver a documentação e usar a api, acesse [POLLVOTE-API](https://agenda-vote.herokuapp.com/swagger-ui.html).

## Versionamento
A versão da aplicação usa o formato do [Semantic Version](https://semver.org/)

* `MAJOR` - quando há um grande mudança no projeto.
* `MINOR` - Novas funcionalidades, mudanças na API.
* `PATCH` - Correção de bugs ou melhorias nas funcionalidades já existentes, que não alteram a API.
* `PATCH` - Correção de bugs ou melhorias nas funcionalidades já existentes, que não alteram a API.
