
package dao;

import modelo.Produto;
import persistencia.AFDAL;
import persistencia.ALDAL;
import persistencia.ConfiguracaoBanco;
import persistencia.Erro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // Cadastra um produto.
    public boolean inserir(Produto produto) {

        if (!conectar()) {
            return false;
        }

        try {
            return ALDAL.set(produto);
        } finally {
            desconectar();
        }
    }

    // Consulta um produto pelo código.
    public Produto consultar(int codigo) {

        Produto produto = new Produto();
        produto.setCodigo(codigo);

        if (!conectar()) {
            return null;
        }

        try {
            boolean encontrado = ALDAL.get(produto);

            if (encontrado) {
                return produto;
            }

            return null;

        } finally {
            desconectar();
        }
    }

    // Atualiza os dados de um produto.
    public boolean atualizar(Produto produto) {

        Produto chaves = new Produto();
        chaves.setCodigo(produto.getCodigo());

        if (!conectar()) {
            return false;
        }

        try {
            return ALDAL.update(produto, chaves);
        } finally {
            desconectar();
        }
    }

    // Exclui um produto pelo código.
    public boolean excluir(int codigo) {

        Produto produto = new Produto();
        produto.setCodigo(codigo);

        if (!conectar()) {
            return false;
        }

        try {
            return ALDAL.delete(produto);
        } finally {
            desconectar();
        }
    }

    // Lista todos os produtos cadastrados.
    public List<Produto> listarTodos() {

        List<Produto> produtos = new ArrayList<>();

        if (!conectar()) {
            return produtos;
        }

        try {

            String sql = "SELECT * FROM TabProduto ORDER BY codigo";

            ResultSet resultado = AFDAL.executeSelect(sql);

            if (resultado == null) {
                return produtos;
            }

            while (resultado.next()) {

                Produto produto = new Produto();

                produto.setCodigo(
                        resultado.getInt("codigo")
                );

                produto.setDescricao(
                        resultado.getString("descricao")
                );

                produto.setFabricante(
                        resultado.getString("fabricante")
                );

                produto.setQtd(
                        resultado.getInt("qtd")
                );

                produto.setPreco(
                        resultado.getDouble("preco")
                );

                produtos.add(produto);
            }

        } catch (SQLException e) {

            Erro.setErro(e.getMessage());

        } finally {

            AFDAL.finalizaConsulta();
            desconectar();
        }

        return produtos;
    }

    // Abre a conexão com o banco.
    private boolean conectar() {

        Erro.limpar();

        return AFDAL.conecta(
                ConfiguracaoBanco.getDriver(),
                ConfiguracaoBanco.getUrl(),
                ConfiguracaoBanco.getUsuario(),
                ConfiguracaoBanco.getSenha()
        );
    }

    // Fecha a conexão com o banco.
    private void desconectar() {
        AFDAL.desconecta();
    }
}
