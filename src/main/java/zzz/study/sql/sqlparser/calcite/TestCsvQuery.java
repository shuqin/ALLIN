package zzz.study.sql.sqlparser.calcite;

import java.sql.*;
import java.util.Properties;

/**
 * TODO
 * <p>
 * Created by qinshu on 2021/7/8
 */
public class TestCsvQuery {

    public static void main(String[]args) {

        try {
            Class.forName("org.apache.calcite.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        Properties info = new Properties();
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:calcite:model=/Users/qinshu/workspace/ALLIN/src/main/resources/model.json", info);
            ResultSet result = connection.getMetaData().getTables(null, null, null, null);
            while(result.next()) {
                System.out.println("Catalog : " + result.getString(1) + ",Database : " + result.getString(2) + ",Table : " + result.getString(3));
            }
            result.close();
            result = connection.getMetaData().getColumns(null, null, "Student", null);
            while(result.next()) {
                System.out.println("name : " + result.getString(4) + ", type : " + result.getString(5) + ", typename : " + result.getString(6));
            }
            result.close();

            Statement st = connection.createStatement();
            long start = System.currentTimeMillis();
            result = st.executeQuery("select * from \"test\" where \"stu_id\" = 2321 ");
            while(result.next()) {
                System.out.println(result.getString(1) + "\t" + result.getString(2) + "\t");
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
