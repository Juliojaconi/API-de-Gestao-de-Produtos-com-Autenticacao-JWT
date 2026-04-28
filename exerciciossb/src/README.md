# 📦 API de Gestão de Produtos com Autenticação JWT

Esta é uma API RESTful desenvolvida com **Spring Boot 3** para o gerenciamento de produtos. O sistema conta com um fluxo completo de autenticação utilizando **Spring Security** e **JWT (JSON Web Token)**, além de persistência de dados e documentação interativa.

## 🚀 Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3**
* **Spring Security** (Autenticação e Autorização)
* **JWT (auth0)** (Geração e validação de tokens)
* **Spring Data JPA** (Persistência de dados)
* **H2 Database** (Banco de dados em memória para testes rápidos)
* **Swagger (OpenAPI 3)** (Documentação da API)
* **Bean Validation** (Validação de dados de entrada)

## 🛠️ Funcionalidades

* **Autenticação:** Registro de novos usuários e login com geração de Token Bearer.
* **Segurança:** Rotas protegidas por nível de acesso (ex: apenas ADMIN pode cadastrar produtos).
* **CRUD de Produtos:** Cadastro, listagem, busca por ID, busca por nome e exclusão.
* **Paginação Inteligente:** Listagem de produtos paginada para otimização de performance (limite de 10 itens por página).
* **Criptografia:** Senhas de usuários armazenadas com BCrypt.

## 📋 Como Executar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/nome-do-repositorio.git](https://github.com/seu-usuario/nome-do-repositorio.git)
    ```
2.  **Importe o projeto** na sua IDE favorita (IntelliJ, Eclipse ou VS Code).
3.  **Execute a aplicação:** Rode a classe principal `ExerciciossbApplication.java`.
4.  A API estará disponível em: `http://localhost:8080`

## 📖 Documentação da API (Swagger)

Com a aplicação rodando, você pode visualizar e testar todos os endpoints através da interface do Swagger:

🔗 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🔗 Exemplos de Endpoints

### Autenticação
* `POST /api/auth/register` - Cria um novo usuário.
* `POST /api/auth/login` - Autentica e retorna o Token JWT.

### Produtos
* `GET /api/produtos/listar` - Lista todos os produtos (sem paginação).
* `GET /api/produtos/pagina?page=0&size=10` - Lista produtos paginados.
* `GET /api/produtos/{id}` - Busca um produto específico.
* `POST /api/produtos` - Cadastra um novo produto (Requer Token ADMIN).

---

## 👨‍💻 Autor

**Julio Garcia Jaconi** Estudante de Análise e Desenvolvimento de Sistemas.