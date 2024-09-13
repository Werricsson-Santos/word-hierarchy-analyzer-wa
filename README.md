<h1 align="center">Word Hierarchy Analyzer</h1>

<p align='center'> 
    <img src="https://img.shields.io/badge/Java-4d524e?style=for-the-badge&logo=spring"/>
    <img src="https://img.shields.io/badge/spring boot-4d524e?style=for-the-badge&logo=spring-boot"/>
    <img src="https://img.shields.io/badge/Node-4d524e?style=for-the-badge&logo=node.js"/>
    <img src="https://img.shields.io/badge/Typescript-4d524e?style=for-the-badge&logo=typescript"/>
    <img src="https://img.shields.io/badge/Next-4d524e?style=for-the-badge&logo=next.js"/>
    <img src="https://img.shields.io/badge/mongodb-4d524e?style=for-the-badge&logo=mongodb"/>  
</p>

Este projeto contém uma CLI (Interface de Linha de Comando) e uma aplicação web para criar (API pronta, frontend em construção), visualizar e analisar hierarquias de palavras.

## Funcionalidades

### CLI para análise de palavras em hierarquia:
- O usuário pode carregar uma árvore hierárquica de palavras onde cada nível da árvore representa uma profundidade específica.
- A CLI permite analisar frases e contar as palavras em um nível de profundidade específico da hierarquia.
- Parâmetros opcionais como `verbose` para exibir o tempo de execução.

### Interface Web (Frontend):
- Permite criar uma hierarquia de palavras com múltiplos níveis.
- Exibe a hierarquia de palavras visualmente.
- Oferece a opção de fazer o download do arquivo JSON contendo a análise criada.

---

## Requisitos Técnicos

### Linguagens e Frameworks:
- **Backend:** Java 17+, Spring Boot
- **Frontend:** Next.js, TypeScript
- **Banco de Dados:** MongoDB

### Como rodar o projeto:

#### Clonando o Repositório:
Para clonar o repositório, use o seguinte comando:

```bash
git clone https://github.com/Werricsson-Santos/word-hierarchy-analyzer-wa.git
```

#### Iniciando o Backend:
1. Navegue até o diretório do backend:
```bash
   cd backend
```

2. Compile e inicie o backend:
```bash
   mvn clean install
   mvn spring-boot:run
```
O maven deve estar instalado.

O backend estará disponível em `http://localhost:8080`.

#### Iniciando o Frontend:
1. Navegue até o diretório do frontend:
```bash
   cd frontend
```

2. Instale as dependências e inicie o frontend:
```bash
   npm install
   npm run dev
```
Obs.: Se for linux, usar yarn...

O frontend estará disponível em `http://localhost:3000`.

#### Configuração de Variáveis de Ambiente:
No backend, a senha do banco de dados deve ser configurada como uma variável de ambiente. Adicione a seguinte linha ao seu arquivo `application.properties`:
```bash
   spring.data.mongodb.password=${MONGO_DB_PASSWORD}
```

No frontend, a URL do backend deve ser configurada com a variável de ambiente `NEXT_PUBLIC_API_URL`.

---

## CLI - Uso

### Analisar uma Frase:
Você pode usar a CLI para analisar uma frase e identificar a profundidade das palavras dentro da hierarquia.

Comando:
```bash
   java -jar cli.jar analyze --depth <n> "{frase}" --verbose (opcional) 
```

- **--depth <n>:** Nível de profundidade da árvore para exibir a contagem de palavras.
- **--verbose:** (Opcional) Exibe o tempo de execução das operações.

Exemplo de uso:
```bash
   java -jar cli.jar analyze --depth 2 "Eu amo papagaios" --verbose
```

Saída esperada:

   Aves: 1

   Tempo de carregamento dos parâmetros: 50ms

   Tempo de verificação da frase: 10ms

---

## Estrutura do Projeto

- **backend/**: Código-fonte da API Restful construída com Spring Boot, onde também contém o código para permitir a execução pelo CLI.
- **frontend/**: Código-fonte da aplicação web construída com Next.js e TypeScript.

---

## Testes

Ainda não foram implementados os testes unitários e de integração.

---

## Considerações Finais
Este projeto visa analisar palavras em uma estrutura hierárquica, fornecendo insights com base na profundidade das palavras em relação à frase fornecida. Tanto a CLI quanto a interface web foram projetadas para serem extensíveis e fáceis de usar.
