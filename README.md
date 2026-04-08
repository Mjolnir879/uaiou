# UAiOU Backend

Backend da plataforma UAiOU, com foco em cadastro de usuários, autenticação JWT e operações básicas de usuário.

## Contexto acadêmico

Este repositório faz parte de um projeto acadêmico desenvolvido para participação na **45ª Feira Tecnológica do Inatel**.

## Visão geral

O projeto utiliza arquitetura em camadas:

- `core`: entidades de domínio, exceções, casos de uso e contratos (gateways)
- `infrastructure`: persistência, segurança, controladores web e configurações Spring
- `resources`: configurações da aplicação, chaves JWT e migrações Flyway

## Stack tecnológica

- Java 21
- Spring Boot 4.0.2
- Spring Web MVC
- Spring Data JPA
- Spring Security + OAuth2 Resource Server (JWT)
- Flyway
- PostgreSQL
- MapStruct
- Lombok

## Autenticação

A autenticação é baseada em JWT assinado com chave RSA.

- Endpoints públicos:
  - `POST /api/v1/auth/register`
  - `POST /api/v1/auth/login`
- Todos os demais endpoints exigem token Bearer
- Senhas são armazenadas com hash BCrypt

Configurações no `application.yml`:

- `jwt.public.key`
- `jwt.private.key`
- `token.access.duration`

Arquivos esperados em `src/main/resources`:

- `app.pub`
- `app.key`

## Estrutura do projeto

- `src/main/java/com/uaiou/core`
  - `domain`
  - `usecase`
- `src/main/java/com/uaiou/infrastructure`
  - `config`
  - `security`
  - `persistence`
  - `web`
- `src/main/resources`
  - `application.yml`
  - `db/migration`
  - `app.pub`
  - `app.key`

## Requisitos para rodar localmente

- JDK 21
- Maven Wrapper (já incluído)
- PostgreSQL ativo

Configuração padrão atual (`application.yml`):

- Host: `localhost`
- Porta: `32768`
- Database: `uaiou`
- Usuário: `postgres`
- Senha: `root`

OBS: alterar valores para o ambiente rodado localmente.

## Executando a aplicação

Windows (PowerShell):

```bash
mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Porta padrão da API:

- `8080`

## Build

Windows:

```bash
mvnw.cmd clean compile
```

Linux/macOS:

```bash
./mvnw clean compile
```

## Endpoints principais

### 1) Registro de usuário

`POST /api/v1/auth/register`

Campos esperados:

- `username`
- `email`
- `password`
- `phoneNumber` (opcional)
- `type`: `DELIVERY_PERSON` ou `ESTABLISHMENT`

Quando `type = DELIVERY_PERSON`:

- `fiscalCode` (obrigatório)
- `cnhCode` (obrigatório)
- `cnhDocument` (opcional)

Quando `type = ESTABLISHMENT`:

- `establishmentName` (obrigatório)
- `establishmentFiscalCode` (obrigatório)

Sucesso:

- `201 Created`

### 2) Login

`POST /api/v1/auth/login`

Payload:

- `email`
- `password`

Sucesso:

- `200 OK`
- Retorna `accessToken` e `expiresIn`

### 3) Criar usuário (autenticado)

`POST /api/v1/users`

Header obrigatório:

`Authorization: Bearer <token>`

### 4) Buscar usuário por ID (autenticado)

`GET /api/v1/users/{id}`

Header obrigatório:

`Authorization: Bearer <token>`

## Padrão de erros

O tratamento global retorna payload padronizado com:

- `timestamp`
- `status`
- `error`
- `message`
- `reason`
- `errors` (somente em validação)

Status comuns:

- `400 Bad Request`
- `401 Unauthorized`
- `403 Forbidden`
- `409 Conflict`
- `422 Unprocessable Entity`
- `500 Internal Server Error`

## Migrações de banco

As migrações ficam em:

- `src/main/resources/db/migration`

Migrações relevantes:

- `V1__create_initial_schema.sql`
- `V2__add_users_password_hash.sql`

## Observações

- O registro é transacional para evitar persistência parcial em caso de falha.
- A segurança é stateless (sem sessão HTTP).
- A pasta `exemplo` foi usada como referência de implementação para autenticação e segurança.
