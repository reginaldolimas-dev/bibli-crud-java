# Biblioteca (bibli-crud-java)

Este repositório contém uma API backend em Java (Spring Boot) para gerenciamento de uma pequena biblioteca (books, users, loans, portfolio). Este README descreve como configurar e executar o projeto localmente, incluindo Docker e Docker Compose para subir a aplicação completa com banco de dados PostgreSQL.

---

## Sumário

- Visão geral
- Pré-requisitos
- Como executar com Docker Compose
- Endpoints disponíveis
- Troubleshooting
- Recursos adicionais

---

## Visão geral

Projeto Spring Boot com JPA, Flyway e PostgreSQL. A arquitetura segue camadas: controller, service, repository, entity e DTOs.

---

## Pré-requisitos

- **Docker Desktop** (Windows/Mac) ou Docker Engine (Linux) — Docker deve estar em execução
- **Docker Compose** (geralmente incluído no Docker Desktop)

Verifique se o Docker está instalado:
```bash
docker --version
docker compose --version
```

---

## Como executar com Docker Compose

Esta é a forma mais simples de executar a aplicação completa, incluindo banco de dados e aplicação em containers.

### Arquitetura do Docker Compose

O arquivo `docker-compose.yml` define dois serviços:

**Serviço `db` (PostgreSQL)**
- Imagem: `postgres:13.10-alpine`
- Banco de dados: `library`
- Usuário: `postgres`
- Senha: `q1w2e3r4`
- Porta: `5432`
- Healthcheck: verifica se PostgreSQL está pronto antes de iniciar a aplicação

**Serviço `app` (Aplicação Spring Boot)**
- Constrói a imagem a partir do `Dockerfile`
- Porta: `9090`
- Depende do serviço `db` (aguarda o healthcheck)
- Variáveis de ambiente configuradas para conectar ao banco via hostname `db`
- Reinicia automaticamente em caso de falha

### Como executar

1. **Subir todos os serviços** (na raiz do projeto):

```bash
docker compose up
```

Ou em background:
```bash
docker compose up -d
```

2. **Verificar status**:

```bash
docker compose ps
```

3. **Ver logs da aplicação**:

```bash
docker compose logs -f app
```

4. **Ver logs do banco**:

```bash
docker compose logs -f db
```

5. **Acessar a aplicação**:
- API: http://localhost:9090
- Swagger UI: http://localhost:9090/swagger-ui/index.html

6. **Parar os serviços**:

```bash
docker compose down
```

7. **Limpar tudo incluindo volumes** (remove dados do banco):

```bash
docker compose down -v
```

---

## Entendendo o Dockerfile

O `Dockerfile` utiliza **multi-stage build** para otimizar o tamanho da imagem:

**Stage 1: Build**
```dockerfile
FROM maven:3.9.8-eclipse-temurin-21 AS build
```
- Usa imagem com Maven + JDK 21
- Copia `pom.xml` e código fonte
- Executa `mvn package` para gerar o JAR (pula testes com `-DskipTests`)

**Stage 2: Runtime**
```dockerfile
FROM eclipse-temurin:21-jre
```
- Usa imagem JRE (apenas runtime, sem Maven)
- Copia o JAR da stage anterior
- Expõe porta `9090`
- Executa a aplicação com `java -jar`

**Benefício**: A imagem final contém apenas o necessário para rodar a aplicação, reduzindo significativamente o tamanho.

---

## Configuração da aplicação (`application.properties`)

O arquivo está localizado em `src/main/resources/application.properties`.

Ao executar com Docker Compose, a aplicação se conecta ao banco de dados usando o hostname `db` (resolvido automaticamente pela rede Docker):

```properties
spring.datasource.url=jdbc:postgresql://db:5432/library
spring.datasource.username=postgres
spring.datasource.password=q1w2e3r4
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.flyway.enabled=true
server.port=9090
```

### Opções úteis para `ddl-auto`:

- `validate` — apenas valida o schema (recomendado em produção)
- `update` — atualiza o schema automaticamente
- `create` — cria do zero (destrói dados existentes)
- `create-drop` — cria na inicialização e destrói ao desligar
- `none` — desabilita DDL automático

---

## Endpoints disponíveis

A aplicação expõe endpoints RESTful para gerenciar:

### Books (Livros)
- `GET /books` — Listar livros com filtros e paginação
- `POST /books` — Criar novo livro
- `PUT /books/{isbn}` — Atualizar livro
- `DELETE /books/{isbn}` — Deletar livro

### Users (Usuários)
- `GET /users` — Listar usuários
- `POST /users` — Criar novo usuário
- `PUT /users/{id}` — Atualizar usuário
- `DELETE /users/{id}` — Deletar usuário

### Loans (Empréstimos)
- `GET /loans` — Listar empréstimos
- `POST /loans` — Criar empréstimo
- `PUT /loans/{id}` — Atualizar empréstimo
- `DELETE /loans/{id}` — Deletar empréstimo

### Portfolio (Acervo)
- `GET /portfolio` — Listar portfolio
- `POST /portfolio` — Adicionar ao portfolio
- `PUT /portfolio/{id}` — Atualizar portfolio
- `DELETE /portfolio/{id}` — Remover do portfolio

**Documentação interativa**: Acesse http://localhost:9090/swagger-ui/index.html para ver e testar todos os endpoints.

---

## Troubleshooting

### Container do banco não inicia

```bash
# Verificar logs
docker compose logs db

# Reiniciar o serviço
docker compose restart db
```

### Aplicação não consegue conectar ao banco

- Verifique se o healthcheck do `db` passou: `docker compose ps`
- Confirme que a URL JDBC está correta no `application.properties`
- Se executando localmente: `jdbc:postgresql://localhost:5432/library`
- Se via Docker Compose: `jdbc:postgresql://db:5432/library`

### Porta 9090 já em uso

Altere a porta no `application.properties`:

```properties
server.port=8080
```

Ou no `docker-compose.yml`:

```yaml
ports:
  - "8080:9090"
```

---

## Recursos adicionais

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Flyway Documentation](https://flywaydb.org/)
- [Docker Documentation](https://docs.docker.com/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
