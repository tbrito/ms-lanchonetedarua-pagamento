# Microserviço de Pagamentos para Lanchonete da Rua

Este é um microserviço de pagamentos para a Lanchonete da Rua, construído com Micronaut, Gradle e Groovy.

## Construção e Execução

### Requisitos

- Docker

### Passos

1. **Construir a Aplicação:**

   Execute o seguinte comando para construir a aplicação e criar a imagem Docker:

   ```bash
   make build-docker

2. **Iniciar a Aplicação:**

    Após construir a imagem, inicie o contêiner com o seguinte comando:

    ```bash
   docker run \
      --name lanchonete-da-rua-pagamento \
      -p 8080:8080 \
      lanchonete-da-rua-pagamento

3. **Acessar a Aplicação:**

    Acesse a documentação da API no Swagger em http://localhost:8080


4. **Banco de Dados**

   A aplicação por padrão utiliza o Banco de Dados em memória **H2**. Para usar outros BDs, configure as variáveis de ambientes definidas em `application.yml`. Ex:

   ```bash
   export JDBC_URL=jdbc:postgresql://localhost:5432/lanchonetedaruapagamento
   export JDBC_USER=lanchonetedaruapagamento
   export JDBC_PASSWORD=lanchonetedaruapagamento
   export JDBC_DRIVER=org.postgresql.Driver


5. **SQS**
   
   Para conectar com SQS é necessário ter as seguintes variáveis de ambiente declaradas `AWS_ACCESS_KEY_ID` e `AWS_SECRET_KEY`
