Gerenciador de Eventos API

API REST desenvolvida em Java com Spring Boot para gerenciamento de administradores e eventos.

O sistema permite que usuários visitantes consultem eventos publicamente, enquanto administradores autenticados podem cadastrar, editar e excluir eventos e administrar outros administradores.

Tecnologias

Java 17

Spring Boot 4.0.7

Spring Web MVC

Spring Data JPA

Spring Security

JWT

BCrypt

PostgreSQL

Maven

Lombok

Bean Validation

SpringDoc OpenAPI / Swagger

Funcionalidades

Área pública

Usuários não autenticados podem:

Listar eventos

Consultar um evento por ID

Área administrativa

Administradores autenticados podem:

Fazer login

Cadastrar administradores

Listar administradores

Pesquisar administradores por nome

Editar administradores

Excluir administradores

Cadastrar eventos

Listar eventos

Consultar eventos por ID

Consultar eventos por administrador

Editar eventos

Excluir eventos

Autenticação

A autenticação é realizada através de JWT.

Fluxo:

Administrador
      ↓
POST /login
      ↓
E-mail + senha
      ↓
Validação da senha com BCrypt
      ↓
Geração do JWT
      ↓
Token retornado
      ↓
Authorization: Bearer {token}
      ↓
Acesso aos endpoints protegidos

O token possui validade de 2 horas.

Administrador padrão

Na inicialização da aplicação, caso não exista nenhum administrador cadastrado, o sistema cria automaticamente um administrador padrão.

As credenciais são configuradas no application.properties:

app.admin.nome=Administrador Principal
app.admin.email=admin@gerenciadoreventos.com
app.admin.senha=Admin@123456

Em produção, essas credenciais devem ser substituídas por valores seguros e não devem ser versionadas.

Endpoints

Autenticação

Método

Endpoint

Acesso

POST

/login

Público

Eventos

Método

Endpoint

Acesso

GET

/eventos

Público

GET

/eventos/{id}

Público

GET

/eventos/administrador/{administradorId}

Administrador

POST

/eventos

Administrador

PUT

/eventos/{id}

Administrador

DELETE

/eventos/{id}

Administrador

Administradores

Método

Endpoint

Acesso

GET

/administradores

Administrador

POST

/administradores

Administrador

PUT

/administradores/{id}

Administrador

DELETE

/administradores/{id}

Administrador

Exemplos

Login

POST /login

{
  "email": "admin@gerenciadoreventos.com",
  "senha": "Admin@123456"
}

Resposta:

{
  "token": "eyJhbGciOiJIUzM4NCJ9..."
}

Autenticação

Authorization: Bearer SEU_TOKEN

Cadastro de administrador

POST /administradores

{
  "nome": "Novo Administrador",
  "email": "novo@email.com",
  "senha": "Senha@123"
}

Cadastro de evento

POST /eventos

{
  "nomeEvento": "Show de Rock",
  "data": "2026-09-10",
  "hora": "19:30:00",
  "local": "Centro de Eventos",
  "descricao": "Grande show de rock",
  "imagem": "https://exemplo.com/imagem.jpg",
  "administradorId": 1
}

Códigos HTTP

Código

Significado

200

Operação realizada com sucesso

201

Recurso criado com sucesso

204

Recurso excluído com sucesso

400

Dados inválidos

401

Autenticação necessária ou credenciais inválidas

404

Recurso não encontrado

409

Conflito ou recurso já cadastrado

500

Erro interno do servidor

Resposta padrão de erro:

{
  "status": 404,
  "mensagem": "Evento não encontrado.",
  "dataHora": "2026-08-11T17:00:00"
}

Banco de dados

O projeto utiliza PostgreSQL.

spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciador_eventos
spring.datasource.username=postgres
spring.datasource.password=123456

Hibernate:

spring.jpa.hibernate.ddl-auto=update

Estrutura do projeto

src/
└── main/
    └── java/
        └── com/
            └── leonam/
                └── gerenciador_eventos/
                    ├── config/
                    ├── controller/
                    ├── dto/
                    │   ├── request/
                    │   └── response/
                    ├── entity/
                    ├── exception/
                    ├── repository/
                    ├── security/
                    ├── service/
                    └── GerenciadorEventosApplication.java

Responsabilidades

config: configurações de segurança, Swagger e administrador padrão.

controller: endpoints REST.

dto: objetos de entrada e saída.

entity: entidades do banco.

exception: exceções e tratamento global.

repository: acesso aos dados via JPA.

security: JWT, geração e validação de tokens.

service: regras de negócio.

Swagger

Após iniciar a aplicação:

http://localhost:8080/swagger-ui/index.html

O Swagger permite visualizar, testar e autenticar os endpoints da API.

Para endpoints protegidos:

Faça login em POST /login.

Copie o token.

Clique em Authorize.

Informe:

Bearer SEU_TOKEN

Execute o endpoint desejado.

Executando o projeto

Pré-requisitos

Java 17

Maven

PostgreSQL

Banco gerenciador_eventos

Banco

CREATE DATABASE gerenciador_eventos;

Configure as credenciais em:

src/main/resources/application.properties

Executar

Windows:

.\mvnw.cmd spring-boot:run

Ou:

mvn spring-boot:run

Testes

mvn clean test

Resultado esperado:

BUILD SUCCESS

Segurança

As senhas dos administradores são protegidas com BCrypt.

As requisições protegidas utilizam JWT:

Authorization: Bearer {token}
