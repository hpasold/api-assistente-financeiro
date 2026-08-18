# API Assistente Financeiro

API REST com reconhecimento de fala, chat com IA e resumo de gastos por categoria, desenvolvida no módulo "Desenvolvendo sua API Inteligente com Reconhecimento de Fala e Spring Boot" do curso Santander 2026 - AI Java Back-end da DIO.

## O que o projeto faz

A API permite enviar um áudio com uma pergunta financeira. O áudio é transcrito por modelo da OpenAI, a pergunta é processada por um assistente conversacional via Spring AI, e a resposta pode ser devolvida também em áudio (text-to-speech). Quando o usuário pergunta sobre seus gastos, a IA aciona automaticamente uma função Java via "tool calling" que consulta o banco de dados e retorna o resumo do mês anterior agrupado por categoria.

## Tecnologias

Java 21, Spring Boot 3, Spring AI, OpenAI API, MySQL, Docker Compose e Maven.

## Como executar

**Pré-requisitos:** Java 21+, Docker e uma chave de API da OpenAI.

```bash
git clone https://github.com/hpasold/api-assistente-financeiro.git
cd api-assistente-financeiro
```

Crie um arquivo ".env" na raiz com as seguintes variáveis:

```env
MYSQL_ROOT_PASSWORD=sua_senha_root
MYSQL_DATABASE=financeiro
MYSQL_USER=usuario
MYSQL_PASSWORD=sua_senha
OPENAI_API_KEY=sua_api_key
```

Suba o banco e execute a aplicação:

```bash
docker compose up -d
./mvnw spring-boot:run
```


## Como testar

**Chat com o assistente:**
```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Qual foi meu gasto do mês passado por categoria?"}'
```

**Transcrição de áudio:**
```bash
curl -X POST http://localhost:8080/api/transcription \
  -F "file=@audio.mp3"
```

**Registrar uma transação:**
```bash
curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
  -d '{"description": "Supermercado", "amount": 15050, "category": "Alimentação"}'
```

O campo "date" é preenchido automaticamente no registro.

## Melhoria implementada

Adicionei o campo "date" à entidade "Transaction" para registrar automaticamente a data de cada transação. Com isso, implementei uma nova ferramenta de tool calling que retorna o resumo dos gastos do mês anterior agrupados por categoria. A IA passa a ter acesso a dados do banco sem que o usuário precise perguntar. Também corrigi a conversão de valores monetários de centavos para reais, movi as credenciais do banco para variáveis de ambiente e atualizei o ".gitignore" para evitar que arquivos sensíveis sejam enviados ao repositório.

## O que aprendi

O conceito mais novo para mim foi o "tool calling". Entender que a IA pode decidir sozinha quando chamar uma função Java com base na intenção da pergunta foi o ponto mais interessante do projeto. Também aprendi na prática a importância de não expor credenciais no repositório. Além disso, consolidei o uso do Docker Compose para subir ambientes e entendi melhor como organizar um projeto Spring Boot.
