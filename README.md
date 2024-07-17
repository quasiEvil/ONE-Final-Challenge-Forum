<p align="center"><img src="https://github.com/quasiEvil/ONE-DesafiosJava01/assets/140989367/629c3fbc-8343-4218-9383-cae3a8a329c1" height="60">
<br>
<img src="https://github.com/quasiEvil/ONE-DesafiosJava01/assets/140989367/ad683805-6a3c-4eb0-aee6-6c611b9d5340" height="10"> & <img src="https://github.com/quasiEvil/ONE-DesafiosJava01/assets/140989367/df751b45-3b7f-4297-a3c2-08d983be89b6" height="15">
</p>

# Fase 3 - Especialização Backend com Java e Spring

## Fórum Hub
Este projeto Java utiliza Spring Boot para criar um fórum interativo, permitindo aos usuários realizar diversas operações como criação de tópicos, busca por tópicos existentes, atualização e exclusão de tópicos. Utiliza um banco de dados PostgreSQL para armazenar informações dos usuários, tópicos e cursos relacionados, além de implementar operações completas de CRUD (Create, Read, Update, Delete) para gerenciar tópicos e outras entidades conforme necessário.

![Badge Spring Alura](https://github.com/user-attachments/assets/b9664c5b-8412-4d5b-b52c-cf4c34b7b437)

👩🏻‍🎓 [Certificado de conclusão](https://cursos.alura.com.br/certificate/quasiEvil/spring-framework-challenge-forum-hub)

## Funcionalidades
### Gerenciamento de Entidades `Topics`, `Users` e `Courses`
- Criar novos tópicos, usuários e cursos
- Atualizar informações de tópicos, usuários e cursos existentes
- Excluir tópicos, usuários e cursos
- Listar todos os tópicos, usuários e cursos cadastrados
- Buscar tópicos, usuários e cursos por ID

### Autenticação e Autorização:
- Registro e login de usuários
- Geração de tokens JWT para autenticação segura
- Controle de acesso baseado em papéis (roles)

### Validação de Dados:
- Validação de entrada de dados para evitar duplicações e inconsistências

### Integração com Banco de Dados:
- Utilização do PostgreSQL para persistência de dados

## Requisitos
- JDK (Kit de Desenvolvimento Java)
- IntelliJ IDEA ou qualquer IDE Java
- Banco de dados PostgreSQL

- ## Instalação e Configuração
1. Clone o repositório:
```bash
git clone https://github.com/quasiEvil/ONE-Final-Challenge-Forum.git

```
2. Abra o projeto no IntelliJ IDEA ou na sua IDE Java preferida.

3. Configure o arquivo `application.properties` no diretório `src/main/resources` com as configurações do banco de dados:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nome-do-banco
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
spring.jpa.hibernate.ddl-auto=update

api.security.token.secret=${JWT_SECRET}
```
4. Execute o projeto Spring Boot (`ForumApplication`).

## Uso
- Ao executar o projeto, o fórum estará disponível para interação via API REST.
- Utilize ferramentas como Insomnia ou Postman para interagir com endpoints específicos.

## Principais endpoints
```md
# Criar novo usuário
- Método: POST
- URL: /users/new

# Criar novo tópico
- Método: POST
- URL: /topics/new

# Atualizar tópico existente
- Método: PUT
- URL: /topics/{id}

# Deletar tópico
- Método: DELETE
- URL: /topics/{id}

# Atualizar STATUS de um tópíco existente
- Método: PUT
- URL: /topics/{id}/status

# Buscar todos os tópicos
- Método: GET
- URL: /topics

# Buscar tópico por ID
- Método: GET
- URL: /topics/{id}
```

## Demo
1. Criando novo tópico
![Java-Forum-CreateTopic](https://github.com/user-attachments/assets/18cda8ab-95d5-4961-b7b9-db3e1d515b25)

## Schemas
![swagger schema](https://github.com/user-attachments/assets/4e7fee66-6708-4195-8124-f115408ff88c)

## Controllers
![swagger controllers](https://github.com/user-attachments/assets/e9148d66-e571-4726-ba5a-958efe4d501a)

## Banco de dados (local)
<img width="1156" alt="Captura de Tela 2024-07-16 às 22 54 07" src="https://github.com/user-attachments/assets/04db9b13-1cf7-454c-ba04-ccf0c7da4489">
<img width="1156" alt="Captura de Tela 2024-07-16 às 22 53 51" src="https://github.com/user-attachments/assets/a48ee428-f620-4b80-ae34-db5b852a63c1">
<img width="1156" alt="Captura de Tela 2024-07-16 às 22 53 35" src="https://github.com/user-attachments/assets/2c71c8b6-45ba-4366-9b99-73588e4bda25">


