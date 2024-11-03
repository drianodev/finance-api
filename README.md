# Finance API

Este é um projeto de API financeira desenvolvido com Spring Boot. O objetivo do projeto é fornecer uma interface RESTful para gerenciar informações financeiras.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para construção de aplicações Java baseadas em Spring.
- **Spring Data JPA**: Para integração com bancos de dados usando JPA.
- **PostgreSQL**: Banco de dados relacional utilizado como armazenamento.
- **Lombok**: Biblioteca para reduzir a verbosidade do código Java.

## Instalação

Para instalar as dependências do projeto, siga os passos abaixo:

1. Clone o repositório:

    ```bash
    git clone git@github.com:drianodev/finance-api.git
    cd finance-api
    ```

2. Compile o projeto com Maven:

    ```bash
    ./mvnw clean install
    ```

## Executando o Projeto

Para executar o projeto, você pode usar o comando abaixo:

```bash
./mvnw spring-boot:run
```

O aplicativo estará disponível em [http://localhost:8080](http://localhost:8080) por padrão.

## Construindo uma Imagem Docker

Para construir uma imagem Docker para a aplicação, execute:

```bash
docker build -t finance-api .
```

E para executar a aplicação com Docker:

```bash
docker-compose up
```

## Configuração do Banco de Dados

Este projeto utiliza o PostgreSQL. Você deve configurar as credenciais do banco de dados no arquivo `application.yml`.
