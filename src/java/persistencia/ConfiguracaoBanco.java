package persistencia;

public class ConfiguracaoBanco {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String URL =
            System.getenv("CRUD_DB_URL") != null
            ? System.getenv("CRUD_DB_URL")
            : "jdbc:mysql://localhost:3306/crud_jsp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USUARIO =
            System.getenv("CRUD_DB_USER") != null
            ? System.getenv("CRUD_DB_USER")
            : "root";

    private static final String SENHA =
            System.getenv("CRUD_DB_PASSWORD") != null
            ? System.getenv("CRUD_DB_PASSWORD")
            : "";

    public static String getDriver() {
        return DRIVER;
    }

    public static String getUrl() {
        return URL;
    }

    public static String getUsuario() {
        return USUARIO;
    }

    public static String getSenha() {
        return SENHA;
    }
}