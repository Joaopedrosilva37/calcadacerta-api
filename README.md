# CalçadaCerta - API RESTful

## Ficha da CitizenTech

| Campo | Conteúdo |
|---|---|
| Nome da CitizenTech | CalçadaCerta |
| Cidade e bairro | São Paulo - Bela Vista / Av. Paulista |
| Cidadão beneficiado | Idosos, cadeirantes, gestantes e pessoas com carrinho de bebê que se deslocam a pé |
| Problema, em uma frase | Quem tem mobilidade reduzida não sabe qual caminho a pé é realmente acessível, e o poder público não sabe quais obras beneficiam mais gente. |
| Como funciona | A API guarda locais, trechos de calçada, usuários e relatos de problemas, e calcula a rota mais acessível para cada perfil, com ou sem chuva. |
| O que cada parte faz | Java: API RESTful com Spring Boot e Oracle. Demais disciplinas: modelagem do banco, rotas em Python, rede, análise de dados, vídeo e documentação ágil. |
| Link do repositório | _(preencher)_ |
| Indicador de impacto | Pessoas por dia beneficiadas pelas obras priorizadas e relatos resolvidos. |
| ODS conectados | ODS 3, ODS 10 e ODS 11 |

## Integrantes

| Nome completo | RM |
|---|---|
| João Pedro Menezes | 566070 |
| Patrick Correa | 564088 |

## Tecnologias

Java 21, Spring Boot 3.3.5, Spring JDBC, Bean Validation, Oracle Database (FIAP), Maven e Docker.

## Arquitetura em camadas

| Pacote | Responsabilidade |
|---|---|
| `model` | Classes do domínio, com validações |
| `dao` | Acesso ao banco Oracle com JDBC |
| `bo` | Regras de negócio |
| `controller` | Endpoints REST |
| `exception` | Exceções e tratamento global de erros |

## Endpoints

Cada recurso (`usuarios`, `locais`, `trechos`, `relatos`) possui os cinco endpoints abaixo.

| Método | Caminho | Resposta de sucesso |
|---|---|---|
| GET | `/api/{recurso}` | 200 |
| GET | `/api/{recurso}/{id}` | 200 |
| POST | `/api/{recurso}` | 201 |
| PUT | `/api/{recurso}/{id}` | 200 |
| DELETE | `/api/{recurso}/{id}` | 204 |

Endpoints adicionais:

| Método | Caminho | Descrição |
|---|---|---|
| GET | `/api/rotas?origem=1&destino=7&perfil=CADEIRANTE&chuva=true` | Calcula a rota mais acessível |
| GET | `/api/status` | Verifica se a API está no ar |

Erros: 400 (dados inválidos), 404 (não encontrado), 409 (registro duplicado ou em uso), 422 (regra de negócio) e 500.

Exemplo de corpo para `POST /api/trechos`:

```json
{
  "idLocalOrigem": 1,
  "idLocalDestino": 5,
  "distanciaM": 350,
  "temEscada": false,
  "nivelCalcada": 1,
  "riscoAlagamento": 0
}
```

## Como executar localmente

1. Execute `banco/01_criar_tabelas.sql` e depois `banco/02_dados_exemplo.sql` no Oracle da FIAP.
2. Defina as variáveis de ambiente `DB_USER` e `DB_PASSWORD`. Se necessário, defina também `DB_URL` (padrão: `jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL`).
3. Execute `mvn spring-boot:run`.
4. Teste em `http://localhost:8080/api/status`.

## Deploy no Render

1. Crie um Web Service no Render conectado a este repositório.
2. Escolha o ambiente Docker. O Render usa o `Dockerfile` da raiz.
3. Em Environment, cadastre `DB_USER`, `DB_PASSWORD` e, se necessário, `DB_URL`.
4. Faça o deploy e teste em `https://SEU-APP.onrender.com/api/status`.

## Dados

Todos os dados de exemplo são fictícios. Nenhum dado pessoal real é usado.
