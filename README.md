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
