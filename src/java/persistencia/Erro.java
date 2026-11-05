package persistencia;

public class Erro {

    private static boolean erro = false;
    private static String mensagem = "";

    public static boolean isErro() {
        return erro;
    }

    public static String getMensagem() {
        return mensagem;
    }

    public static void setErro(boolean valor) {
        erro = valor;

        if (!valor) {
            mensagem = "";
        }
    }

    public static void setErro(String texto) {
        erro = true;
        mensagem = texto;
    }

    public static void limpar() {
        erro = false;
        mensagem = "";
    }
}