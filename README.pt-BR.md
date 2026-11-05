# Sistema de Gerenciamento de Produtos | CRUD

Aplicação web para gerenciamento de produtos, desenvolvida com Java, JSP, JDBC e MySQL. O sistema oferece operações completas de CRUD por meio de uma interface responsiva e intuitiva.

<p align="center">
  🇧🇷 Português | 🇺🇸 <a href="README.md">English</a>
</p>

---

## Sobre o Projeto

Este projeto é um sistema web de gerenciamento de produtos desenvolvido para praticar desenvolvimento web com Java, integração com banco de dados e organização de software.

O sistema permite gerenciar registros de produtos por meio de uma interface simples e responsiva, com os dados armazenados em um banco de dados MySQL.

## Funcionalidades

- Cadastro de produtos.
- Consulta de produtos pelo código.
- Listagem de produtos cadastrados.
- Atualização das informações dos produtos.
- Exclusão de produtos.
- Persistência de dados utilizando MySQL.
- Interface responsiva com tema escuro.
- Conexão com banco de dados utilizando JDBC.

## Tecnologias Utilizadas

| Tecnologia | Utilização |
|---|---|
| Java | Lógica da aplicação |
| JSP | Páginas web dinâmicas |
| JDBC | Conexão com o banco de dados |
| MySQL | Armazenamento de dados |
| HTML5 | Estrutura das páginas |
| CSS3 | Estilização e responsividade |
| Apache Tomcat | Servidor de aplicação web |
| NetBeans | Ambiente de desenvolvimento |

## Estrutura do Projeto

A aplicação utiliza uma organização em camadas para separar as responsabilidades:

- **Modelo:** representa os dados dos produtos.
- **DAO:** concentra as operações de acesso aos dados.
- **Persistência:** gerencia as conexões e operações SQL.
- **JSP:** fornece a interface web.

## Configuração do Banco de Dados

O sistema utiliza MySQL.

Crie o banco de dados:

```sql
CREATE DATABASE crud_jsp;
```

Selecione o banco:

```sql
USE crud_jsp;
```

Crie a tabela de produtos:

```sql
CREATE TABLE TabProduto (
    codigo INT PRIMARY KEY,
    descricao VARCHAR(150) NOT NULL,
    fabricante VARCHAR(100) NOT NULL,
    qtd INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL
);
```

> O script representa a estrutura esperada com base nos campos utilizados pela aplicação. Caso a tabela já exista, não é necessário criá-la novamente.

## Configuração da Conexão

As credenciais do banco são configuradas por meio de variáveis de ambiente, evitando que sejam armazenadas diretamente no código-fonte.

| Variável | Descrição |
|---|---|
| `CRUD_DB_URL` | URL de conexão com o MySQL |
| `CRUD_DB_USER` | Usuário do banco |
| `CRUD_DB_PASSWORD` | Senha do banco |

Exemplo de URL:

```text
jdbc:mysql://localhost:3306/crud_jsp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

Configure as variáveis de ambiente no sistema antes de executar a aplicação.

**Nota de segurança:** nunca publique credenciais reais do banco de dados em um repositório público.

## Como Executar

1. Instale o JDK e o Apache NetBeans.
2. Instale e configure o MySQL.
3. Crie o banco de dados e a tabela.
4. Configure as variáveis de ambiente.
5. Abra o projeto no NetBeans.
6. Configure o Apache Tomcat.
7. Execute o projeto pelo NetBeans.

## Aprendizados

Este projeto proporcionou experiência prática com:

- Desenvolvimento web com Java e JSP.
- Operações CRUD utilizando JDBC.
- Integração com banco de dados MySQL.
- Organização de aplicações em camadas.
- Desenvolvimento de interfaces responsivas.
- Configuração por variáveis de ambiente.

## Autor

**Eduardo Carvalho**

Estudante de Análise e Desenvolvimento de Sistemas.

- GitHub: [Carvalho-Eduardo](https://github.com/Carvalho-Eduardo)
- LinkedIn: [Eduardo Carvalho](https://linkedin.com/in/e-duardo-carvalho/)

---

Desenvolvido como projeto de estudo e prática de desenvolvimento web com Java.
