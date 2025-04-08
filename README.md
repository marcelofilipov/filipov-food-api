![GitHub](https://img.shields.io/github/license/marcelofilipov/filipov-food-api)
![GitHub repo size](https://img.shields.io/github/repo-size/marcelofilipov/filipov-food-api)

# Filipov Food API

A **Filipov Food API** é uma aplicação desenvolvida em Java com Spring Boot, como parte de um projeto de estudo e prova de conceito para construção de APIs RESTful no domínio de gerenciamento de alimentos.

## 🛠 Tecnologias Utilizadas

- **Java**
- **Spring Boot**
- **Spring Data JPA (Hibernate)**
- **Spring Fox (Swagger/OpenAPI 3)**
- **Rest Assured**
- **Fixture Factory**

## ✅ Funcionalidades

- Endpoints REST para operações CRUD de entidades relacionadas à gestão de alimentos.
- Testes automatizados (unitários e de integração).
- Documentação da API via Swagger.

## 🚀 Como Executar

### Pré-requisitos

- JDK 11+
- Maven

### Passos

```bash
# Clone o repositório
git clone https://github.com/marcelofilipov/filipov-food-api.git

# Acesse o diretório do projeto
cd filipov-food-api

# Compile e instale as dependências
mvn clean install

# Execute a aplicação
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## 📄 Documentação

Acesse `http://localhost:8080/swagger-ui.html` para visualizar a documentação interativa gerada pelo Swagger (Spring Fox).

## 🧪 Testes

O projeto possui testes de integração com `Rest Assured` e objetos de teste criados com `Fixture Factory`.

## 📝 Licença

Este projeto está licenciado sob a licença MIT.

## 👤 Autor

Desenvolvido por [Marcelo Filipov](https://github.com/marcelofilipov)

