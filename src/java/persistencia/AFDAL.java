package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AFDAL {

    private static Connection conexao;
    private static PreparedStatement consultaAtual;
    private static ResultSet resultadoAtual;

    // Abre a conexão com o banco de dados.
    public static boolean conecta(
            String driver,
            String url,
            String usuario,
            String senha) {

        try {
            Class.forName(driver);

            conexao = DriverManager.getConnection(
                    url,
                    usuario,
                    senha
            );

            return true;

        } catch (Exception e) {
            Erro.setErro(e.getMessage());
            return false;
        }
    }

    // Fecha a conexão com o banco.
    public static void desconecta() {

        finalizaConsulta();

        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }

            conexao = null;

        } catch (SQLException e) {
            Erro.setErro(e.getMessage());
        }
    }

    // Verifica se existe uma conexão ativa.
    public static boolean estaConectado() {

        try {
            return conexao != null && !conexao.isClosed();

        } catch (SQLException e) {
            Erro.setErro(e.getMessage());
            return false;
        }
    }

    // Executa INSERT, UPDATE, DELETE e comandos SQL.
    public static int executeSQL(
            String sql,
            Object... parametros) {

        if (!estaConectado()) {
            Erro.setErro("Não existe conexão com o banco de dados.");
            return -1;
        }

        try (PreparedStatement comando =
                conexao.prepareStatement(sql)) {

            for (int i = 0; i < parametros.length; i++) {
                comando.setObject(i + 1, parametros[i]);
            }

            return comando.executeUpdate();

        } catch (SQLException e) {
            Erro.setErro(e.getMessage());
            return -1;
        }
    }

    // Executa SELECT e retorna o resultado.
    public static ResultSet executeSelect(
            String sql,
            Object... parametros) {

        if (!estaConectado()) {
            Erro.setErro("Não existe conexão com o banco de dados.");
            return null;
        }

        finalizaConsulta();

        try {
            consultaAtual = conexao.prepareStatement(sql);

            for (int i = 0; i < parametros.length; i++) {
                consultaAtual.setObject(i + 1, parametros[i]);
            }

            resultadoAtual = consultaAtual.executeQuery();

            return resultadoAtual;

        } catch (SQLException e) {
            Erro.setErro(e.getMessage());
            finalizaConsulta();
            return null;
        }
    }

    // Fecha o resultado e o comando de uma consulta.
    public static void finalizaConsulta() {

        try {
            if (resultadoAtual != null) {
                resultadoAtual.close();
            }
        } catch (SQLException e) {
            Erro.setErro(e.getMessage());
        }

        try {
            if (consultaAtual != null) {
                consultaAtual.close();
            }
        } catch (SQLException e) {
            Erro.setErro(e.getMessage());
        }

        resultadoAtual = null;
        consultaAtual = null;
    }
}