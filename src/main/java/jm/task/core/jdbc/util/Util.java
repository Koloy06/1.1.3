package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    // Connect to MySQL
    public static Connection getMySQLConnection() {
        String hostName = "localhost";
        String dbName = "MyBase?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
        String userName = "root";
        String password = "12345";
        Connection connection;


        connection = getMySQLConnection(hostName, dbName, userName, password);

        return connection;
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password){
        // Declare the class Driver for MySQL DB
        // This is necessary with Java 5 (or older)
        // Java6 (or newer) automatically find the appropriate driver.
        // If you use Java> 5, then this line is not needed.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionURL, userName,
                    password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }
}
