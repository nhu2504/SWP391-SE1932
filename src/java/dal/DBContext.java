package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private static DBContext instance;
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=eduraFINALFINALFINAL";
    private String user = "sa";
    private String password = "123";

    private DBContext() {}

    public static DBContext getInstance() {
        if (instance == null) {
            instance = new DBContext();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC không tìm thấy.", e);
        }
    }
}