# Biblioteca (bibli-crud-java)

Este repositório contém uma API backend em Java (Spring Boot) para gerenciamento de uma pequena biblioteca (books, users, loans, portfolio). Este README descreve como configurar e executar o projeto localmente, incluindo um docker-compose para subir o banco de dados PostgreSQL.

---

## Sumário

- Visão geral
- Pré-requisitos
- Subir o banco com Docker Compose
- Configuração da aplicação (application.properties)
- Rodando a aplicação
- Rodando os testes
- Migrações (Flyway)
- Observações sobre Windows / WSL
- Contato / próximos passos

---

## Visão geral

Projeto Spring Boot com JPA, Flyway e PostgreSQL. A arquitetura segue camadas: controller, service, repository, entity e DTOs.

---

## Pré-requisitos

- Java (JDK) instalado (versão compatível conforme projeto; o pom indica Java 21). Verifique com:

```bash
java -version
```

- Maven (ou use o wrapper incluído `./mvnw` / `mvnw.cmd`).
- Docker Desktop (para rodar o PostgreSQL via Docker Compose) — Docker deve estar ativo.

---

## Subir o banco com Docker Compose

Um arquivo `docker-compose.yml` foi adicionado na raiz do projeto para subir um container PostgreSQL:

- Serviço: `db` (imagem: `postgres:13.10-alpine`)
- Banco: `sapmetabase`
- Usuário: `postgres`
- Senha: `q1w2e3r4`
- Porta exposta: `5432`

Comandos para subir o banco (na raiz do projeto):

```bash
# sobe o Postgres em background
docker compose up -d

# checa status dos serviços
docker compose ps

# acompanhar logs do serviço db
docker compose logs -f db
```

Comandos úteis para inspecionar o container:

```bash
# abrir shell no container
docker exec -it library sh
# dentro do container usar psql
psql -U postgres -d sapmetabase -c '\l'
```

Caso prefira não deixar credenciais no `docker-compose.yml`, use um arquivo `.env` e referencie variáveis no compose (ex.: `${POSTGRES_PASSWORD}`).

---

## Configuração da aplicação (`application.properties`)

No `src/main/resources/application.properties` configure a datasource para apontar ao container:

- Se você rodar a aplicação na sua máquina (host) e o Postgres no Docker Desktop no mesmo host:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sapmetabase
spring.datasource.username=postgres
spring.datasource.password=q1w2e3r4
```

- Se você rodar a aplicação também via Docker (no mesmo `docker-compose`), altere a URL para usar o nome do serviço `db`:

```properties
spring.datasource.url=jdbc:postgresql://db:5432/library
```

Outras propriedades úteis (exemplo):

```properties
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.flyway.enabled=true
```

Observação: ajuste `ddl-auto` conforme sua necessidade (`none`, `validate`, `update`, `create`, `create-drop`).

---

## Rodando a aplicação

Build e execução local com o Maven Wrapper:

```bash
# compila o projeto (pula testes para rapidez)
./mvnw package -DskipTests

# roda a aplicação
./mvnw spring-boot:run
```

Ou execute o JAR gerado:

```bash
java -jar target/biblioteca-0.0.1-SNAPSHOT.jar
```

Se o banco estiver em um container Docker, primeiro espere o healthcheck do container ficar OK (`docker compose ps` ou `docker compose logs -f db`) antes de iniciar a aplicação.

## Acessos

Após subir a aplicação localmente (porta padrão 9090 neste projeto), os endpoints podem ser acessados em:

- API: http://localhost:9090
- Swagger UI: http://localhost:9090/swagger-ui/index.html

(Se você mudou a porta no `application.properties`, ajuste os links acima de acordo.)

---

## Migrações (Flyway)

O projeto inclui Flyway. As migrations SQL estão em `src/main/resources/db/migration`. Flyway será executado automaticamente na inicialização se estiver habilitado via `spring.flyway.enabled=true`.

---

