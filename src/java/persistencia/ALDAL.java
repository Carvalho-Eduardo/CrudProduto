
package persistencia;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * ALDAL - Automatic Data Access Layer.
 *
 * Responsável por gerar comandos SQL a partir
 * dos atributos de objetos Java.
 */
public class ALDAL {

    private static final String PREFIXO_TABELA = "Tab";

    /**
     * Cria uma tabela a partir dos atributos do objeto.
     */
    public static boolean geraTabela(Object obj) {

        if (obj == null) {
            Erro.setErro("O objeto não pode ser nulo.");
            return false;
        }

        Field[] campos = obj.getClass().getDeclaredFields();

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE ");
        sql.append(nomeTabela(obj));
        sql.append(" (");

        for (int i = 0; i < campos.length; i++) {

            sql.append(campos[i].getName());
            sql.append(" ");
            sql.append(tipoSQL(campos[i].getType()));

            if (i < campos.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(")");

        return AFDAL.executeSQL(sql.toString()) >= 0;
    }

    /**
     * Insere um objeto no banco de dados.
     */
    public static boolean set(Object obj) {

        if (obj == null) {
            Erro.setErro("O objeto não pode ser nulo.");
            return false;
        }

        Field[] campos = obj.getClass().getDeclaredFields();

        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO ");
        sql.append(nomeTabela(obj));
        sql.append(" (");

        for (int i = 0; i < campos.length; i++) {

            sql.append(campos[i].getName());

            if (i < campos.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(") VALUES (");

        for (int i = 0; i < campos.length; i++) {

            sql.append("?");

            if (i < campos.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(")");

        try {

            List<Object> valores = obterValores(obj, campos);

            return AFDAL.executeSQL(
                    sql.toString(),
                    valores.toArray()
            ) > 0;

        } catch (Exception e) {

            Erro.setErro(e.getMessage());
            return false;
        }
    }

    /**
     * Consulta um registro usando os valores
     * preenchidos no objeto como critérios.
     */
    public static boolean get(Object obj) {

        if (obj == null) {
            Erro.setErro("O objeto não pode ser nulo.");
            return false;
        }

        Field[] campos = obj.getClass().getDeclaredFields();

        List<String> condicoes = new ArrayList<>();
        List<Object> valores = new ArrayList<>();

        try {

            for (Field campo : campos) {

                Object valor = obterValor(obj, campo);

                if (valorEhCriterio(valor, campo.getType())) {

                    condicoes.add(campo.getName() + " = ?");
                    valores.add(valor);
                }
            }

            if (condicoes.isEmpty()) {

                Erro.setErro(
                        "Informe um campo para realizar a consulta."
                );

                return false;
            }

            String sql = "SELECT * FROM "
                    + nomeTabela(obj)
                    + " WHERE "
                    + String.join(" AND ", condicoes);

            ResultSet resultado = AFDAL.executeSelect(
                    sql,
                    valores.toArray()
            );

            if (resultado == null) {
                return false;
            }

            try {

                if (resultado.next()) {

                    preencherObjeto(obj, resultado);

                    return true;
                }

                Erro.setErro(
                        obj.getClass().getSimpleName()
                        + " não localizado."
                );

                return false;

            } finally {

                AFDAL.finalizaConsulta();
            }

        } catch (Exception e) {

            Erro.setErro(e.getMessage());

            AFDAL.finalizaConsulta();

            return false;
        }
    }

    /**
     * Exclui registros utilizando os critérios
     * preenchidos no objeto.
     */
    public static boolean delete(Object obj) {

        if (obj == null) {
            Erro.setErro("O objeto não pode ser nulo.");
            return false;
        }

        Field[] campos = obj.getClass().getDeclaredFields();

        List<String> condicoes = new ArrayList<>();
        List<Object> valores = new ArrayList<>();

        try {

            for (Field campo : campos) {

                Object valor = obterValor(obj, campo);

                if (valorEhCriterio(valor, campo.getType())) {

                    condicoes.add(campo.getName() + " = ?");
                    valores.add(valor);
                }
            }

            if (condicoes.isEmpty()) {

                Erro.setErro(
                        "Informe um critério para realizar a exclusão."
                );

                return false;
            }

            String sql = "DELETE FROM "
                    + nomeTabela(obj)
                    + " WHERE "
                    + String.join(" AND ", condicoes);

            return AFDAL.executeSQL(
                    sql,
                    valores.toArray()
            ) > 0;

        } catch (Exception e) {

            Erro.setErro(e.getMessage());
            return false;
        }
    }

    /**
     * Atualiza os dados de um registro.
     *
     * dados: objeto com os novos valores.
     * chaves: objeto com os critérios de identificação.
     */
    public static boolean update(Object dados, Object chaves) {

        if (dados == null || chaves == null) {

            Erro.setErro(
                    "Os objetos de dados e chaves são obrigatórios."
            );

            return false;
        }

        if (!dados.getClass().equals(chaves.getClass())) {

            Erro.setErro(
                    "Os objetos devem pertencer à mesma classe."
            );

            return false;
        }

        Field[] camposDados =
                dados.getClass().getDeclaredFields();

        Field[] camposChaves =
                chaves.getClass().getDeclaredFields();

        List<String> alteracoes = new ArrayList<>();
        List<Object> valores = new ArrayList<>();

        List<String> condicoes = new ArrayList<>();
        List<Object> valoresChaves = new ArrayList<>();

        try {

            // Monta os campos que serão atualizados.
            for (Field campo : camposDados) {

                Object valor = obterValor(dados, campo);

                alteracoes.add(campo.getName() + " = ?");
                valores.add(valor);
            }

            // Monta os critérios de identificação.
            for (Field campo : camposChaves) {

                Object valor = obterValor(chaves, campo);

                if (valorEhCriterio(valor, campo.getType())) {

                    condicoes.add(campo.getName() + " = ?");
                    valoresChaves.add(valor);
                }
            }

            if (alteracoes.isEmpty()) {

                Erro.setErro(
                        "Nenhum campo foi informado para atualização."
                );

                return false;
            }

            if (condicoes.isEmpty()) {

                Erro.setErro(
                        "Informe uma chave para localizar o registro."
                );

                return false;
            }

            valores.addAll(valoresChaves);

            String sql = "UPDATE "
                    + nomeTabela(dados)
                    + " SET "
                    + String.join(", ", alteracoes)
                    + " WHERE "
                    + String.join(" AND ", condicoes);

            return AFDAL.executeSQL(
                    sql,
                    valores.toArray()
            ) > 0;

        } catch (Exception e) {

            Erro.setErro(e.getMessage());
            return false;
        }
    }

    /**
     * Retorna o nome da tabela correspondente à classe.
     */
    private static String nomeTabela(Object obj) {

        return PREFIXO_TABELA
                + obj.getClass().getSimpleName();
    }

    /**
     * Converte tipos Java para tipos SQL.
     */
    private static String tipoSQL(Class<?> tipo) {

        if (tipo == String.class) {
            return "VARCHAR(255)";
        }

        if (tipo == int.class || tipo == Integer.class) {
            return "INTEGER";
        }

        if (tipo == double.class || tipo == Double.class) {
            return "DOUBLE";
        }

        if (tipo == float.class || tipo == Float.class) {
            return "FLOAT";
        }

        if (tipo == long.class || tipo == Long.class) {
            return "BIGINT";
        }

        if (tipo == boolean.class || tipo == Boolean.class) {
            return "BOOLEAN";
        }

        if (tipo == java.sql.Date.class) {
            return "DATE";
        }

        throw new IllegalArgumentException(
                "Tipo Java não suportado: " + tipo.getName()
        );
    }

    /**
     * Obtém os valores de todos os atributos.
     */
    private static List<Object> obterValores(
            Object obj,
            Field[] campos) throws Exception {

        List<Object> valores = new ArrayList<>();

        for (Field campo : campos) {

            valores.add(obterValor(obj, campo));
        }

        return valores;
    }

    /**
     * Obtém um valor utilizando o getter do atributo.
     */
    private static Object obterValor(
            Object obj,
            Field campo) throws Exception {

        String primeiraLetra =
                campo.getName().substring(0, 1).toUpperCase();

        String nomeGetter =
                "get" + primeiraLetra
                + campo.getName().substring(1);

        Method getter;

        try {

            getter = obj.getClass().getMethod(nomeGetter);

        } catch (NoSuchMethodException e) {

            if (campo.getType() == boolean.class
                    || campo.getType() == Boolean.class) {

                nomeGetter = "is" + primeiraLetra
                        + campo.getName().substring(1);

                getter = obj.getClass().getMethod(nomeGetter);

            } else {

                throw e;
            }
        }

        return getter.invoke(obj);
    }

    /**
     * Verifica se um valor pode ser usado como critério.
     */
    private static boolean valorEhCriterio(
            Object valor,
            Class<?> tipo) {

        if (valor == null) {
            return false;
        }

        if (valor instanceof String) {
            return !((String) valor).trim().isEmpty();
        }

        if (tipo == int.class || tipo == Integer.class) {
            return ((Number) valor).intValue() != 0;
        }

        if (tipo == long.class || tipo == Long.class) {
            return ((Number) valor).longValue() != 0;
        }

        if (tipo == double.class || tipo == Double.class) {
            return ((Number) valor).doubleValue() != 0;
        }

        if (tipo == float.class || tipo == Float.class) {
            return ((Number) valor).floatValue() != 0;
        }

        return true;
    }

    /**
     * Preenche um objeto com os dados retornados
     * pela consulta ao banco.
     */
    private static void preencherObjeto(
            Object obj,
            ResultSet resultado) throws Exception {

        Field[] campos = obj.getClass().getDeclaredFields();

        for (Field campo : campos) {

            String primeiraLetra =
                    campo.getName().substring(0, 1).toUpperCase();

            String nomeSetter =
                    "set" + primeiraLetra
                    + campo.getName().substring(1);

            Object valor = resultado.getObject(
                    campo.getName()
            );

            if (valor == null) {
                continue;
            }

            Object valorConvertido = converterValor(
                    valor,
                    campo.getType()
            );

            Method setter = obj.getClass().getMethod(
                    nomeSetter,
                    campo.getType()
            );

            setter.invoke(obj, valorConvertido);
        }
    }

    /**
     * Converte os valores retornados pelo banco
     * para os tipos definidos no modelo Java.
     */
    private static Object converterValor(
            Object valor,
            Class<?> tipo) {

        if (tipo == String.class) {
            return valor.toString();
        }

        if (tipo == int.class || tipo == Integer.class) {
            return ((Number) valor).intValue();
        }

        if (tipo == double.class || tipo == Double.class) {
            return ((Number) valor).doubleValue();
        }

        if (tipo == float.class || tipo == Float.class) {
            return ((Number) valor).floatValue();
        }

        if (tipo == long.class || tipo == Long.class) {
            return ((Number) valor).longValue();
        }

        if (tipo == boolean.class || tipo == Boolean.class) {
            return valor;
        }

        return valor;
    }
}
