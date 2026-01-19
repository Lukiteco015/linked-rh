# 🚀 Linked-RH / TrainHub API

API REST desenvolvida para gerenciamento de treinamentos corporativos e alocação de funcionários em turmas. O projeto foca em alta performance e código limpo, utilizando Spring Boot sem a necessidade de ORMs pesados.

## 🛠️ Tecnologias
- **Java 21**
- **Spring Boot 3**
- **Spring JDBC Template** (Consultas SQL otimizadas)
- **PostgreSQL** (Banco de dados relacional)
- **Flyway** (Gerenciamento de migrations)
- **Lombok** (Produtividade no desenvolvimento)

## 🏗️ Diferenciais de Arquitetura (Boas Práticas)
- **Generic DAO (BaseRepository):** Implementação de uma classe base genérica para operações de CRUD, reduzindo a repetição de código SQL em mais de 40%.
- **Camada de Service:** Centralização total das regras de negócio (ex: validação de datas de turmas e verificação de dependências).
- **Tratamento de Erros Global:** Uso de `@RestControllerAdvice` para padronizar as respostas de erro em JSON, incluindo timestamps, status HTTP e mensagens amigáveis.
- **DTO Pattern:** Separação total entre as entidades do banco de dados e os dados trafegados na API para maior segurança.

## 📋 Funcionalidades Principais
- [x] **Gestão de Cursos:** CRUD completo com paginação.
- [x] **Gestão de Turmas:** Filtros inteligentes por curso e ordenação por data.
- [x] **Regras de Data:** Bloqueio de criação de turmas em meses passados ou anos diferentes do corrente.
- [x] **Consultas Avançadas:** Listagem de turmas utilizando `LEFT JOIN` e `GROUP BY` para exibir a quantidade de participantes em tempo real.

## 🚀 Como executar
1. Clone o repositório: `git clone https://github.com/Lukiteco015/linked-rh.git`
2. Configure o banco PostgreSQL no `application.properties`.
3. Execute via Maven: `mvn spring-boot:run`
