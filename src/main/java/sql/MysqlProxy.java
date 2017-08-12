package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import config.Configuration;

/**
 * The MysqlProxy class
 *
 * @author Alireza Aghamohammadi
 */
public class MysqlProxy {
  private Connection connection;

  /**
   * Connect.
   */
  public void connect() {
    System.err.println("-------- Mysql JDBC Connection Testing --------");
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    System.err.println("Mysql JDBC Driver Registered.");

    try {
      this.setConnection(DriverManager.getConnection("jdbc:mysql://localhost:3306/" + Configuration.DATABASE_NAME.get(), Configuration.DATABASE_USER.get(), Configuration.DATABASE_PASSWORD.get()));
    } catch (SQLException e) {
      System.err.println("Connection Failed!");
      e.printStackTrace();
    }
  }

  /**
   * Gets connection.
   *
   * @return the connection
   */
  public Connection getConnection() {
    return connection;
  }

  /**
   * Sets connection.
   *
   * @param connection the connection
   */
  public void setConnection(Connection connection) {
    this.connection = connection;
  }
}
