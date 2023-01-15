# Attornatus Challenge

Olá, esse projeto foi desenvolvido para o teste técnico da [Attornatus](https://www.linkedin.com/company/attornatus-procuradoria-digital/).


# Sumário
- ⚙️ [Requisitos avaliativos do desafio](#requisitos)
- 🐋 [Rodando a aplicação localmente com Docker](#rodando-com-dockerfile)
- 🌐 [Rotas da aplicação](#configurando-o-arquivo-nginxconf-apenas-em-caso-de-deploy)

## Requisitos:
- [X] Utilizar Java, Spring Boot
- [X] Criação da API
- [X] Rota para criar uma pessoa
- [X] Rota para Editar uma pessoa
- [X] Rota para Consultar uma pessoa
- [X] Rota para Listar pessoas
- [X] Rota para Criar endereço para pessoa
- [X] Rota para Listar endereços da pessoa
- [X] Rota para Poder informar qual endereço é o principal da pessoa
- [X] Testes
- [X] Todas as respostas da API devem ser JSON  
- [X] Banco de dados H2
 
 **Opcional:**
- [X] [Realizar build da aplicação](https://attornatus-project-o4muvqm46a-uc.a.run.app/api)

# Rodando com Dockerfile

Na raiz do projeto digite o comando:

``` dockerfile
docker run -p 8080:8080 luizportel4/attornatus-api:2.0.0
```

# Rotas

### Rota para criar uma pessoa [POST (_domain_/api/person)]
### Rota para Editar uma pessoa [PUT (_domain_/api/person/{id})]
### Rota para Consultar uma pessoa [GET (_domain_/api/person/{id})]
### Rota para Listar pessoas [GET (_domain_/api/people)]
### Rota para Criar endereço para pessoa [POST (_domain_/api/address/{id})]
### Rota para Listar endereços da pessoa [GET (_domain_/api/addresses/{id})]
### Rota para Poder informar qual endereço é o principal da pessoa [GET (_domain_/api/person)]
