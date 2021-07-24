package zzz.study.sql.calcite;

import org.apache.calcite.config.Lex;
import org.apache.calcite.jdbc.CalciteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 预编译语句缓存
 *
 * NOTE: 具体应用中可作为 @Component 组件
 *
 * Created by qinshu on 2021/7/13
 */
public class PreparedStatementWrapper {

    private static CalciteConnection calciteConnection;

    static {

        try {
            Class.forName("org.apache.calcite.jdbc.Driver");

            Properties info = new Properties();
            info.setProperty("lex", Lex.JAVA.name());

            Connection connection =
                    DriverManager.getConnection("jdbc:calcite:model=/Users/qinshu/workspace/ALLIN/src/main/resources/vt.json", info);

            calciteConnection =
                    connection.unwrap(CalciteConnection.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement create(String sql) throws SQLException {
        return calciteConnection.prepareStatement(sql);
    }

    public static ResultSet execute(PreparedStatement st, List<PrepareStatementParamObject> params) throws SQLException {
        if (params != null) {
            for (PrepareStatementParamObject paramObject: params) {
                st.setObject(paramObject.getIndex(), paramObject.getValue());
            }
        }
        ResultSet result = st.executeQuery();
        st.clearParameters();
        return result;
    }

    public static List<List<Object>> getResult(ResultSet r) throws SQLException {
        ResultSetMetaData metaData = r.getMetaData();
        int colCount = metaData.getColumnCount();
        List<List<Object>> objs = new ArrayList<>();
        List<Object> o = new ArrayList<>();
        while(r.next()) {
            for (int i=1; i <= colCount; i++) {
                o.add(r.getString(i));
            }
            objs.add(o);
        }
        r.close();
        return objs;
    }

    public static void logResult(ResultSet resultSet) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colCount = metaData.getColumnCount();
            while(resultSet.next()) {
                for (int i=1; i <= colCount; i++) {
                    System.out.printf(resultSet.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception ex) {
            //
        }
    }

}
