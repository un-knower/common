package javax.annotation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception {
        Class.forName("com.facebook.presto.jdbc.PrestoDriver");
        Connection connection = DriverManager.getConnection("jdbc:presto://192.168.52.38:18080/hive", "root", null);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("show tables");
        int columnCount = rs.getMetaData().getColumnCount();
        List<Object> list = new ArrayList<>();
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                list.add(rs.getObject(i));
            }
        }
        rs.close();
        connection.close();
        System.out.println(list);
    }
}
