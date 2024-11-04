import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class THE_Connection {

    private static String servername = "localhost";
    private static String dbname = "personal_tracker"; // Ensure this matches your database name
    private static String username = "root";
    private static Integer portnumber = 3306; // Default port for MySQL
    private static String password = ""; // Default password

    public static Connection getTheConnection() throws SQLException {
        MysqlDataSource datasource = new MysqlDataSource();
        datasource.setServerName(servername);
        datasource.setUser(username);
        datasource.setDatabaseName(dbname);
        datasource.setPortNumber(portnumber);
        datasource.setPassword(password);

        return datasource.getConnection();
    }
}
