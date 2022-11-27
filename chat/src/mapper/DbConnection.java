package mapper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author 曾佳宝
 * @date 2022/11/25 19:30
 */
public class DbConnection {

  public static Connection getConnection() {
    String name = "root";
    String password = "zjb15973958319";
    String url = "jdbc:mysql://localhost:3306/chat";
    Connection connection;
    // 1、加载驱动
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      // 2、建立连接
      connection = DriverManager.getConnection(url, name, password);
      return connection;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
