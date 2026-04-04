# Deploy Manager

Sistema web fullstack para gerenciamento de deploys de software em clientes remotos. Permite controlar versões do sistema, cadastrar clientes, e gerenciar conexões de acesso remoto (TeamViewer, AnyDesk, AnyViewer) de forma centralizada e segura.

---

## Tecnologias

### Backend
| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.5 | Framework base |
| Spring Security | — | Autenticação e autorização |
| Spring Data JPA | — | Persistência de dados |
| PostgreSQL | 15 | Banco de dados relacional |
| Flyway | — | Versionamento e migração do banco |
| JWT (Auth0) | 4.5.1 | Autenticação stateless |
| SpringDoc OpenAPI | 3.0.2 | Documentação automática da API (Swagger) |
| Lombok | — | Redução de boilerplate |
| Docker Compose | — | Orquestração de serviços |

### Frontend
| Tecnologia | Versão | Uso |
|---|---|---|
| Angular | 21.2 | Framework SPA |
| TypeScript | 5.9 | Linguagem principal |
| SCSS | — | Estilização com variáveis e aninhamento |
| RxJS | 7.8 | Programação reativa e chamadas HTTP |
| Angular Router | — | Navegação e guards de rota |

---

## Arquitetura

```
deploy_manager/
├── src/                          # Backend Java (Spring Boot)
│   └── main/java/.../
│       ├── controller/           # Endpoints REST
│       ├── service/              # Regras de negócio
│       ├── repository/           # Acesso ao banco (JPA)
│       ├── domain/               # Entidades e DTOs
│       └── infra/security/       # JWT, filtros e CORS
│
├── frontend/                     # Frontend Angular
│   └── src/app/
│       ├── core/
│       │   ├── models/           # Interfaces TypeScript (DTOs)
│       │   ├── services/         # Chamadas HTTP ao backend
│       │   ├── guards/           # Proteção de rotas
│       │   └── interceptors/     # Injeção automática do JWT
│       ├── features/
│       │   ├── auth/             # Login e cadastro
│       │   ├── dashboard/        # Visão geral
│       │   ├── clients/          # CRUD de clientes
│       │   ├── versions/         # CRUD de versões
│       │   └── connections/      # CRUD de conexões remotas
│       └── shared/
│           └── components/       # Sidebar e layout
│
└── compose.yaml                  # PostgreSQL via Docker
```

---

## Funcionalidades

- **Autenticação JWT** — login seguro com token, expiração de 2 horas e interceptor automático que injeta o token em todas as requisições
- **Controle de acesso por roles** — apenas usuários `ADMIN` podem criar, editar e deletar registros
- **Gestão de Versões** — cadastro de versões do software com número, data de release e changelog
- **Gestão de Clientes** — cadastro de clientes com cidade, estado, contato e versão atual em uso
- **Gestão de Conexões Remotas** — armazenamento seguro de credenciais de acesso remoto (máquina, banco de dados e ferramenta de conexão)
- **Soft Delete** — registros são desativados, não excluídos permanentemente
- **Paginação** — todas as listagens suportam paginação via Spring Pageable
- **Busca** — busca de clientes por nome
- **Dashboard** — visão consolidada com totais de clientes, versões e conexões

---

## Diferenciais

### Banco versionado com Flyway
O schema do banco é gerenciado por migrations SQL versionadas. Ao subir a aplicação, o Flyway aplica automaticamente qualquer migration pendente — sem necessidade de scripts manuais.

```
V1__create_table_versions.sql
V2__create_table_clients.sql
V3__create_table_connections.sql
V4__create_table_users.sql
```

### Documentação automática com Swagger
A API é totalmente documentada via SpringDoc OpenAPI. Após subir o backend, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

### Docker Compose integrado
O banco de dados sobe automaticamente junto com a aplicação Spring Boot via `spring-boot-docker-compose`. Basta ter o Docker rodando — sem configuração manual.

### Arquitetura Standalone (Angular)
O frontend utiliza a arquitetura standalone do Angular 21, sem NgModules. Componentes, guards e interceptors são declarados de forma independente, deixando o código mais limpo e modular.

### Interceptor JWT automático
O token de autenticação é injetado automaticamente em todas as requisições HTTP via `HttpInterceptorFn`, sem necessidade de configuração manual por chamada.

---

## Como rodar

### Pré-requisitos
- Java 21+
- Node.js 18+ e Angular CLI 21+
- Docker

### 1. Clone o repositório
```bash
git clone https://github.com/hugaojanuario/deploy.manager-v2.git
cd deploy.manager-v2
```

### 2. Configure as variáveis de ambiente
Copie o arquivo de exemplo e preencha com suas credenciais:
```bash
cp .env.example .env
```

### 3. Suba o banco de dados
```bash
docker compose up -d
```

### 4. Suba o backend
```bash
./mvnw spring-boot:run
```

### 5. Suba o frontend
```bash
cd frontend
npm install
npm start
```

### 6. Acesse
| Serviço | URL |
|---|---|
| Frontend | http://localhost:4200 |
| Backend API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |

---

## Endpoints da API

### Auth
| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| POST | `/auth/login` | Público | Autenticação, retorna JWT |
| POST | `/auth/register` | Público | Cadastro de novo usuário |

### Versões
| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| GET | `/deploy/version` | Autenticado | Listar versões (paginado) |
| GET | `/deploy/version/{id}` | Autenticado | Buscar por ID |
| POST | `/deploy/version` | Admin | Criar versão |
| PUT | `/deploy/version/{id}` | Admin | Atualizar versão |
| DELETE | `/deploy/version/{id}` | Admin | Desativar versão |

### Clientes
| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| GET | `/deploy/client` | Público | Listar clientes (paginado) |
| GET | `/deploy/client/{id}` | Público | Buscar por ID |
| GET | `/deploy/client/search?name=` | Público | Buscar por nome |
| POST | `/deploy/client` | Admin | Criar cliente |
| PUT | `/deploy/client/{id}` | Admin | Atualizar cliente |
| DELETE | `/deploy/client/{id}` | Admin | Desativar cliente |

### Conexões
| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| GET | `/deploy/connection` | Autenticado | Listar conexões (paginado) |
| GET | `/deploy/connection/{id}` | Autenticado | Buscar por ID |
| POST | `/deploy/connection` | Admin | Criar conexão |
| PUT | `/deploy/connection/{id}` | Admin | Atualizar conexão |
| DELETE | `/deploy/connection/{id}` | Admin | Desativar conexão |

---

## Autor

Desenvolvido por [Hugo Januario](https://github.com/hugaojanuario)
