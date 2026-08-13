# Gerenciador de Eventos API

API REST para gerenciamento de administradores e eventos, desenvolvida em Java 17 com Spring Boot 4.0.7.

Visitantes podem consultar eventos publicamente. Administradores autenticados gerenciam eventos e outros administradores.

## Stack

- Java 17 | Spring Boot 4.0.7 | Maven
- Spring Data JPA | PostgreSQL
- Spring Security | JWT (jjwt 0.12.6) | BCrypt
- SpringDoc OpenAPI (Swagger) | Lombok | Bean Validation

## Como Rodar

**Prerequisitos:** Java 17+, PostgreSQL 12+

```sql
CREATE DATABASE gerenciador_eventos;
```

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

**Swagger:** `http://localhost:8080/swagger-ui/index.html`

## Credenciais Padrao

| Campo | Valor |
|-------|-------|
| Email | admin@gerenciadoreventos.com |
| Senha | Admin@123456 |

## Endpoints

### Autenticacao

| Metodo | Endpoint | Acesso |
|--------|----------|--------|
| `POST` | `/login` | Publico |

### Administradores

| Metodo | Endpoint | Acesso |
|--------|----------|--------|
| `POST` | `/administradores` | Publico |
| `GET` | `/administradores` | Auth |
| `GET` | `/administradores/pagina` | Auth |
| `GET` | `/administradores/{id}` | Auth |
| `PUT` | `/administradores/{id}` | Auth |
| `PATCH` | `/administradores/senha` | Auth |
| `DELETE` | `/administradores/{id}` | Auth |

### Eventos

| Metodo | Endpoint | Acesso |
|--------|----------|--------|
| `GET` | `/eventos` | Publico |
| `GET` | `/eventos/pagina` | Publico |
| `GET` | `/eventos/{id}` | Publico |
| `GET` | `/eventos/administrador/{id}` | Auth |
| `GET` | `/eventos/meus/count` | Auth |
| `POST` | `/eventos` | Auth |
| `PUT` | `/eventos/{id}` | Auth |
| `DELETE` | `/eventos/{id}` | Auth |

### Solicitacao de Redefinicao de Senha

| Metodo | Endpoint | Acesso |
|--------|----------|--------|
| `POST` | `/solicitacoes-senha` | Publico |
| `GET` | `/solicitacoes-senha` | Auth |
| `PATCH` | `/solicitacoes-senha/{id}` | Auth |

## Autenticacao

Envie o header em requisicoes protegidas:

```
Authorization: Bearer SEU_TOKEN
```



## Estrutura

```
src/main/java/com/leonam/gerenciador_eventos/
├── config/          # CORS, Security, Swagger, seed admin
├── controller/      # Endpoints REST
├── dto/             # Request e Response DTOs
├── entity/          # Entidades JPA
├── enums/           # StatusSolicitacaoSenha
├── exception/       # Excecoes e tratamento global
├── repository/      # Spring Data JPA
├── security/        # JWT (filtro, token, handlers)
└── service/         # Regras de negocio
```

## Variaveis de Ambiente

| Variavel | Descricao | Padrao |
|----------|-----------|--------|
| `DB_USERNAME` | Usuario PostgreSQL | `*****` |
| `DB_PASSWORD` | Senha PostgreSQL | `*****` |
| `JWT_SECRET` | Chave JWT (min 32 bytes) | `chave-jwt-desenvolvimento-32-bytes-minimo-123456` |

## Build

```bash
mvn clean package
java -jar target/gerenciador-eventos-0.0.1-SNAPSHOT.jar
```

## Frontend

[Repositorio Frontend](https://github.com/LeonamNgr/gerenciador_eventos_FrontEnd.git)
