# JGate

## Visão Geral

Este é um projeto de API REST com Spring Boot para autenticação de usuários e geração, verificação e gerenciamento de JWT (JSON Web Token). A API permite o registro de novos usuários, login e verificação de tokens JWT. O projeto já inclui integração com banco de dados MySQL para persistência de dados e controle de acesso baseado em papéis de usuário (ex.: ADMIN, USER).

## Funcionalidades

- **Registro de Usuário**: Registra novos usuários com nome de usuário e senha no banco de dados.
- **Login de Usuário**: Autentica os usuários com base em suas credenciais e retorna um token JWT.
- **Verificação de Token**: Verifica se um token JWT é válido.
- **Controle de Acesso por Papéis**: Atribuição de papéis aos usuários durante o registro (ex.: ADMIN, USER) para controlar o acesso a determinadas funcionalidades.
- **Persistência no Banco de Dados MySQL**: Os dados dos usuários são armazenados em um banco de dados MySQL.
- **Teste de API via interface gráfica**: Você pode testar a API usando uma interface gráfica Swagger.

## Endpoints

### 1. **Registro de Usuário**

- **URL**: `/register`
- **Método**: `POST`
- **Descrição**: Registra um novo usuário e retorna um token JWT.
- **Corpo da Requisição** (JSON):
```json
{
    "username": "seu_nome_de_usuário",
    "password": "sua_senha"
}
```
- Resposta (Sucesso):
```json
{
    "token": "seu_token_jwt_gerado"
}
```
- Resposta (Erro - Nome de usuário já registrado):
```json
"Nome de usuário já está em uso"
```
### 2. Login de Usuário
- URL: /auth
- Método: POST
- Descrição: Autentica o usuário e retorna um token JWT se as credenciais forem válidas.
- Corpo da Requisição (JSON):
```json
{
    "username": "seu_nome_de_usuário",
    "password": "sua_senha"
}
```
- Resposta (Sucesso):
```json
{
    "token": "seu_token_jwt_gerado"
}
```
- Resposta (Erro - Credenciais inválidas):
```json
"Credenciais inválidas!"
```
### 3. Verificar Token JWT
- URL: /verifyToken
- Método: POST
- Descrição: Verifica se o token JWT fornecido é válido.
- Corpo da Requisição (JSON):
```json
{
    "token": "seu_token_jwt"
}
```
- Resposta (Token Válido):
```json
"Token é válido!"
```
Resposta (Token Inválido ou Expirado):
```json
"Token inválido ou expirado!"
```

# Estrutura do Projeto
O projeto contém os seguintes componentes:

## Controller:
Gerencia os endpoints da API para registro, login e verificação de tokens.
## Serviço (AuthenticationService):
Gerencia o registro de usuários, login e validação de senhas.
## Provedor JWT (JwtProvider):
Responsável pela geração e validação de tokens JWT.
## Modelo:
Objetos de Transferência de Dados (DTO) como LoginRequest, TokenRequest, e AuthResponse para requisições e respostas de usuários.
## Repositório:
Repositório para persistência e consulta de usuários no banco de dados MySQL.
## Segurança:
Uso de PasswordEncoder para criptografia de senhas.
## Pré-requisitos
Antes de construir e executar o projeto, certifique-se de ter instalado o seguinte:
- JDK 8 ou superior
- Maven (para construir e executar o projeto)
- Banco de dados MySQL (para persistência de usuários)
## Como Executar o Projeto
### 1. Clonar o Repositório
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```
### 2. Configurar Banco de Dados MySQL
Configure o arquivo application.properties ou application.yml com as credenciais e URL do seu banco de dados MySQL:

Exemplo application.properties:
```cfg
spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```
### 3. Construir o Projeto
Use o Maven para construir o projeto:

```bash
mvn clean install
```
### 4. Executar o Projeto
Após a construção bem-sucedida, você pode executar a aplicação Spring Boot com:

```bash
mvn spring-boot:run
```
- Ou usando o arquivo JAR gerado:

```bash
java -jar target/seu-projeto-0.0.1-SNAPSHOT.jar
```
### 5. Executar Testes Unitários
Para rodar os testes unitários, use o seguinte comando Maven:

```bash
mvn test
```
# Testar a API
Você pode testar a API usando ferramentas como Postman ou curl. Abaixo estão exemplos de comandos curl para testar os endpoints.

##Registrar um Usuário
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "seu_nome_de_usuário", "password": "sua_senha"}'
```
## Login de Usuário
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "seu_nome_de_usuário", "password": "sua_senha"}'
```
## Verificar Token JWT
```bash
curl -X POST http://localhost:8080/auth/verifyToken \
  -H "Content-Type: application/json" \
  -d '{"token": "seu_token_jwt"}'
```

## Testar usando Swagger

Este projeto foi configurado com Swagger, logo permitindo testes da API usando a página ```swagger-ui.html```

# Dependências
- Spring Boot Starter Web: Para construir serviços RESTful.
- Spring Boot Starter Security: Para autenticação e criptografia de senhas.
- Spring Boot Starter Data JPA: Para persistência de dados com MySQL.
- JWT (Json Web Token): Para autenticação baseada em tokens.
- MySQL: Banco de dados relacional para armazenamento de dados dos usuários.
- JUnit e Mockito: Para testes unitários.

# Melhorias Futuras
- Implementar verificação de email para confirmar o registro de novos usuários.
- Adicionar autenticação com dois fatores (2FA).
- Controle de acesso mais refinado baseado em papéis com endpoints exclusivos para ADMIN e USER.
- Melhorar as mensagens de erro com internacionalização (i18n).

# Licença
Este projeto está licenciado sob a Licença MIT - veja o arquivo LICENSE para mais detalhes.

