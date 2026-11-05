
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="modelo.Produto"%>
<%@page import="dao.ProdutoDAO"%>

<%
    request.setCharacterEncoding("UTF-8");

    Produto produto = null;
    String mensagem = "";

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        try {
            int codigo = Integer.parseInt(request.getParameter("codigo"));

            ProdutoDAO dao = new ProdutoDAO();
            produto = dao.consultar(codigo);

            if (produto == null) {
                mensagem = "Produto não encontrado!";
            }

        } catch (Exception e) {
            mensagem = "Erro na consulta: " + e.getMessage();
        }
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <title>Consultar Produto</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <style>
        * { box-sizing: border-box; }

        body {
            font-family: Arial, Helvetica, sans-serif;
            background: #0d1117;
            color: #e6edf3;
            min-height: 100vh;
            margin: 0;
            padding: 30px 16px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            width: 100%;
            max-width: 480px;
            padding: 32px;
            background: #161b22;
            border: 1px solid #30363d;
            border-radius: 14px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.25);
        }

        .cabecalho {
            text-align: center;
            margin-bottom: 26px;
        }

        .marca {
            color: #58a6ff;
            font-size: 12px;
            font-weight: bold;
            letter-spacing: 1.5px;
            text-transform: uppercase;
            margin-bottom: 12px;
        }

        h1 {
            font-size: 25px;
            margin: 0 0 8px;
        }

        .subtitulo {
            color: #8b949e;
            font-size: 14px;
            line-height: 1.5;
            margin: 0;
        }

        label {
            display: block;
            margin: 17px 0 7px;
            font-size: 14px;
            font-weight: bold;
            color: #c9d1d9;
        }

        input[type="number"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #30363d;
            border-radius: 7px;
            font-size: 15px;
            color: #e6edf3;
            background: #0d1117;
        }

        input[type="number"]:focus {
            outline: none;
            border-color: #2f81f7;
            box-shadow: 0 0 0 3px rgba(47, 129, 247, 0.15);
        }

        .botao {
            display: block;
            width: 100%;
            padding: 12px;
            margin-top: 22px;
            border: 1px solid #2f81f7;
            border-radius: 7px;
            background: #2f81f7;
            color: #ffffff;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
        }

        .botao:hover { background: #1f6feb; }

        .resultado {
            margin-top: 25px;
            padding: 20px;
            background: #0d1117;
            border: 1px solid #30363d;
            border-radius: 9px;
        }

        .resultado h2 {
            color: #58a6ff;
            font-size: 18px;
            margin: 0 0 16px;
        }

        .resultado p {
            margin: 10px 0;
            font-size: 14px;
            line-height: 1.5;
            overflow-wrap: anywhere;
        }

        .resultado strong { color: #c9d1d9; }

        .preco {
            color: #3fb950;
            font-weight: bold;
        }

        .mensagem {
            margin-top: 20px;
            padding: 12px;
            border-radius: 7px;
            text-align: center;
            font-size: 14px;
            line-height: 1.5;
            overflow-wrap: anywhere;
            background: #2d1618;
            border: 1px solid #da3633;
            color: #ff7b72;
        }

        .voltar {
            display: block;
            margin-top: 22px;
            color: #8b949e;
            text-decoration: none;
            text-align: center;
            font-size: 14px;
        }

        .voltar:hover { color: #58a6ff; }

        @media (max-width: 480px) {
            body { padding: 16px 10px; }

            .container { padding: 24px 20px; }

            h1 { font-size: 22px; }

            .resultado { padding: 16px; }
        }

        @media (max-width: 320px) {
            .container { padding: 20px 14px; }
            h1 { font-size: 20px; }
        }
    </style>
</head>

<body>

    <main class="container">

        <header class="cabecalho">
            <div class="marca">Sistema de Estoque</div>

            <h1>Consultar Produto</h1>

            <p class="subtitulo">
                Informe o código para localizar um produto cadastrado.
            </p>
        </header>

        <form action="consultar.jsp" method="POST">

            <label for="codigo">Código do produto</label>

            <input
                type="number"
                id="codigo"
                name="codigo"
                required
                step="1"
                min="1"
                placeholder="Ex.: 101">

            <input
                type="submit"
                class="botao"
                value="Consultar Produto">

        </form>

        <% if (produto != null) { %>

            <section class="resultado">
                <h2>Resultado da consulta</h2>

                <p>
                    <strong>Código:</strong>
                    <%= produto.getCodigo() %>
                </p>

                <p>
                    <strong>Descrição:</strong>
                    <%= produto.getDescricao() %>
                </p>

                <p>
                    <strong>Fabricante:</strong>
                    <%= produto.getFabricante() %>
                </p>

                <p>
                    <strong>Quantidade:</strong>
                    <%= produto.getQtd() %>
                </p>

                <p>
                    <strong>Preço:</strong>
                    <span class="preco">
                        R$ <%= String.format(
                            java.util.Locale.forLanguageTag("pt-BR"),
                            "%.2f",
                            produto.getPreco()
                        ) %>
                    </span>
                </p>
            </section>

        <% } else if (!mensagem.isEmpty()) { %>

            <div class="mensagem">
                <%= mensagem %>
            </div>

        <% } %>

        <a href="index.html" class="voltar">
            &#8592; Voltar ao início
        </a>

    </main>

</body>
</html>
