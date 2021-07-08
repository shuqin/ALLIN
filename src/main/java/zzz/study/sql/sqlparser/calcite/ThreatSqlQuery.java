package zzz.study.sql.sqlparser.calcite;

import java.sql.*;
import java.util.Properties;

/**
 * TODO
 * <p>
 * Created by qinshu on 2021/7/8
 */
public class ThreatSqlQuery {

    public static void main(String[]args) {

        try {
            Class.forName("org.apache.calcite.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        Properties info = new Properties();
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:calcite:model=/Users/qinshu/workspace/ALLIN/src/main/resources/vt.json", info);

            Statement st = connection.createStatement();
            long start = System.currentTimeMillis();
            ResultSet result = st.executeQuery("select p.\"pid\", p.\"pname\", f.\"fname\", f.\"path\" from \"process_event\" as p inner join \"file\" as f on p.\"fname\" = f.\"fname\" where p.\"pid\" = 1234 ");
            while(result.next()) {
                ResultSetMetaData metaData = result.getMetaData();
                int colCount = metaData.getColumnCount();
                for (int i=1; i <= colCount; i++) {
                    System.out.printf(result.getString(i) + " ");
                }
            }
            result.close();
            connection.close();
            long end = System.currentTimeMillis();
            System.out.println((end-start) + " ms");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
