# Product Management System | CRUD

A web application for managing products, built with Java, JSP, JDBC, and MySQL. The system provides complete CRUD operations through a responsive and user-friendly interface.

🇺🇸 English | 🇧🇷 [Português](#português)

---

## 🇺🇸 English

### About the Project

This project is a web-based product management system developed to practice Java web development, database integration, and software architecture.

It allows users to manage product records through a simple and responsive interface, with data stored in a MySQL database.

### Features

- Create new products.
- Search for products by code.
- List registered products.
- Update product information.
- Delete products.
- Persist data using MySQL.
- Responsive interface with a dark theme.
- Database connection using JDBC.

### Technologies

| Technology | Purpose |
|---|---|
| Java | Application logic |
| JSP | Dynamic web pages |
| JDBC | Database connectivity |
| MySQL | Data storage |
| HTML5 | Page structure |
| CSS3 | Styling and responsiveness |
| Apache Tomcat | Web application server |
| NetBeans | Development environment |

### Project Structure

```text
CrudProduto/
├── src/
│   └── java/
│       ├── dao/
│       │   └── ProdutoDAO.java
│       ├── modelo/
│       │   └── Produto.java
│       └── persistencia/
│           ├── AFDAL.java
│           ├── ALDAL.java
│           ├── ConfiguracaoBanco.java
│           └── Erro.java
├── web/
├── nbproject/
├── .gitignore
└── README.md
```

### Architecture

The application follows a layered structure to separate responsibilities:

- **Model:** represents product data.
- **DAO:** handles product data access operations.
- **Persistence:** manages database connections and SQL operations.
- **JSP:** provides the web interface.

### Database Setup

The application uses MySQL.

Create the database:

```sql
CREATE DATABASE crud_jsp;
```

Select the database:

```sql
USE crud_jsp;
```

Create the products table:

```sql
CREATE TABLE TabProduto (
    codigo INT PRIMARY KEY,
    descricao VARCHAR(150) NOT NULL,
    fabricante VARCHAR(100) NOT NULL,
    qtd INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL
);
```

> The SQL script represents the expected table structure based on the fields used by the application. If the table already exists, there is no need to create it again.

### Database Configuration

Database credentials are configured through environment variables instead of being stored directly in the source code.

| Variable | Description |
|---|---|
| `CRUD_DB_URL` | MySQL connection URL |
| `CRUD_DB_USER` | Database username |
| `CRUD_DB_PASSWORD` | Database password |

Example connection URL:

```text
jdbc:mysql://localhost:3306/crud_jsp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

Configure these environment variables on your system before running the application.

**Security note:** never commit real database credentials to a public repository.

### How to Run

1. Install the JDK and Apache NetBeans.
2. Install
