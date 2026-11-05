
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Produto"%>
<%@page import="dao.ProdutoDAO"%>

<%
    request.setCharacterEncoding("UTF-8");

    ProdutoDAO dao = new ProdutoDAO();
    List<Produto> produtos = dao.listarTodos();
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <title>Produtos cadastrados</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <style>
        * { box-sizing: border-box; }

        body {
            font-family: Arial, Helvetica, sans-serif;
            background: #0d1117;
            color: #e6edf3;
            margin: 0;
            padding: 35px 20px;
        }

        .container {
            width: 100%;
            max-width: 1100px;
            margin: 0 auto;
            padding: 30px;
            background: #161b22;
            border: 1px solid #30363d;
            border-radius: 12px;
        }

        .cabecalho {
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 18px;
            margin-bottom: 25px;
        }

        h1 {
            color: #e6edf3;
            font-size: 27px;
            margin: 0;
        }

        .subtitulo {
            color: #8b949e;
            font-size: 14px;
            line-height: 1.5;
            margin: 8px 0 0;
        }

        .botao {
            display: inline-block;
            padding: 12px 16px;
            border-radius: 7px;
            text-decoration: none;
            font-weight: bold;
            font-size: 14px;
            text-align: center;
        }

        .cadastrar {
            background: #238636;
            color: #ffffff;
            border: 1px solid #238636;
        }

        .cadastrar:hover { background: #2ea043; }

        .tabela-container {
            width: 100%;
            overflow-x: auto;
            border: 1px solid #30363d;
            border-radius: 8px;
            -webkit-overflow-scrolling: touch;
        }

        table {
            width: 100%;
            min-width: 650px;
            border-collapse: collapse;
        }

        th {
            background: #21262d;
            color: #c9d1d9;
            text-align: left;
            padding: 15px;
            font-size: 13px;
            border-bottom: 1px solid #30363d;
            white-space: nowrap;
        }

        td {
            padding: 15px;
            border-bottom: 1px solid #30363d;
            font-size: 14px;
        }

        tbody tr:hover { background: #1c2128; }
        tbody tr:last-child td { border-bottom: none; }

        .codigo { color: #8b949e; }

        .preco {
            color: #3fb950;
            font-weight: bold;
            white-space: nowrap;
        }

        .vazio {
            color: #8b949e;
            text-align: center;
            padding: 35px 15px;
        }

        .voltar {
            display: inline-block;
            margin-top: 24px;
            color: #8b949e;
            text-decoration: none;
            font-size: 14px;
        }

        .voltar:hover { color: #58a6ff; }

        @media (max-width: 600px) {
            body { padding: 16px 10px; }

            .container { padding: 22px 15px; }

            .cabecalho {
                flex-direction: column;
                align-items: stretch;
            }

            h1 { font-size: 23px; }

            .cadastrar { width: 100%; }

            th, td { padding: 12px; }
        }
    </style>
</head>

<body>

    <main class="container">

        <header class="cabecalho">
            <div>
                <h1>Produtos cadastrados</h1>
                <p class="subtitulo">
                    Consulte todos os produtos registrados no sistema.
                </p>
            </div>

            <a href="cadastrar.jsp" class="botao cadastrar">
                + Novo produto
            </a>
        </header>

        <div class="tabela-container">
            <table>
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Descrição</th>
                        <th>Fabricante</th>
                        <th>Quantidade</th>
                        <th>Preço</th>
                    </tr>
                </thead>

                <tbody>
                    <% if (produtos.isEmpty()) { %>
                        <tr>
                            <td colspan="5" class="vazio">
                                Nenhum produto cadastrado.
                            </td>
                        </tr>
                    <% } else { %>

                        <% for (Produto produto : produtos) { %>
                            <tr>
                                <td class="codigo">
                                    <%= produto.getCodigo() %>
                                </td>

                                <td>
                                    <%= produto.getDescricao() %>
                                </td>

                                <td>
                                    <%= produto.getFabricante() %>
                                </td>

                                <td>
                                    <%= produto.getQtd() %>
                                </td>

                                <td class="preco">
                                    R$ <%= String.format(
                                        java.util.Locale.forLanguageTag("pt-BR"),
                                        "%.2f",
                                        produto.getPreco()
                                    ) %>
                                </td>
                            </tr>
                        <% } %>

                    <% } %>
                </tbody>
            </table>
        </div>

        <a href="index.html" class="voltar">
            &#8592; Voltar ao início
        </a>

    </main>

</body>
</html>
