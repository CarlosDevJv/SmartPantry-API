🥗 SmartPantry API

Status do Projeto: ⚠️ Em Desenvolvimento (Work in Progress)

Uma API RESTful inteligente construída com Java 21 e Spring Boot 3 que transforma a maneira como você gerencia os alimentos da sua casa. O SmartPantry monitora a validade dos seus insumos, evita o desperdício de comida com alertas automatizados e utiliza Inteligência Artificial (Spring AI) para sugerir receitas personalizadas com base nos ingredientes disponíveis na sua geladeira.

🚀 Funcionalidades Planejadas & Em Desenvolvimento

[x] Gestão de Estoque (CRUD): Cadastro e controle de insumos, quantidades, unidades de medida e datas de validade.

[ ] Alertas Diários de Validade: Notificações automáticas por e-mail (via @Scheduled e Spring Mail) alertando sobre itens prestes a vencer.

[ ] Chef IA Integrado: Gerador de receitas personalizadas integrado via Spring AI (OpenAI / Gemini) baseado exclusivamente nos itens disponíveis.

[ ] Autenticação & Segurança: Autenticação de usuários via JWT (JSON Web Tokens) com controle de acesso por roles.

[ ] Relatórios de Desperdício: Histórico de consumo vs. itens descartados por vencimento.

🛠️ Tecnologias Utilizadas

Linguagem: Java 21

Framework Principal: Spring Boot 3.3+

Módulos Spring:

Spring Data JPA (Persistência de Dados)

Spring Security + JWT (Autenticação e Autorização)

Spring AI (Integração com LLMs)

Spring Mail (Envio de e-mails)

Spring Validation (Validação de DTOs)

Banco de Dados: PostgreSQL (Produção/Dev) e H2 Database (Testes)

Documentação: OpenAPI 3 / Swagger

Containers: Docker & Docker Compose

Ferramentas Auxiliares: Lombok, Flyway (Migration)

📋 Pré-requisitos

Antes de começar, garanta que você possui instalado em sua máquina:

JDK 21 ou superior

Maven 3.8+

Docker & Docker Compose (opcional, para rodar o banco PostgreSQL localmente)

Chave de API de LLM (OpenAI ou Google Gemini) para funcionalidades de AI.

🔧 Como Executar o Projeto (Ambiente Local)

Clone o repositório:

git clone https://github.com/seu-usuario/smartpantry-api.git
cd smartpantry-api


[//]: # (Suba o banco de dados &#40;Docker&#41;:)

[//]: # ()
[//]: # (docker-compose up -d)

[//]: # ()
[//]: # ()
[//]: # (Configure as Variáveis de Ambiente:)

[//]: # (Crie um arquivo application-dev.yml ou configure suas variáveis de ambiente:)

[//]: # ()
[//]: # (SPRING_AI_OPENAI_API_KEY: "sua-chave-api-aqui")

[//]: # (SPRING_MAIL_USERNAME: "seu-email@gmail.com")

[//]: # (SPRING_MAIL_PASSWORD: "sua-senha-de-app")


Execute a aplicação:

mvn spring-boot:run


Acesse a Documentação (Swagger UI):
Após iniciar, acesse no navegador: http://localhost:8080/swagger-ui.html

🗺️ Roadmap de Desenvolvimento

[ ] Sprint 1: Estrutura base, configuração do banco de dados e Entidades JPA.

[ ] Sprint 2: Implementação do Spring Security e autenticação JWT.

[ ] Sprint 3: Lógica de negócios de estoque e endpoints RESTful.

[ ] Sprint 4: Agendador de tarefas (@Scheduled) e envio de notificações por e-mail.

[ ] Sprint 5: Integração do Spring AI para geração inteligente de receitas.

[ ] Sprint 6: Testes unitários/integração e containerização final.

📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.