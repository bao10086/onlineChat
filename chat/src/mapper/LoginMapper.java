package mapper;

import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 曾佳宝
 * @date 2022/11/25 19:38
 */
public class LoginMapper {

  public static Boolean login(User user) {
    Connection connection = DbConnection.getConnection();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      String sql = "select * from user where name=? and password=?";
      assert connection != null;
      stmt = connection.prepareStatement(sql);
      stmt.setString(1, user.getUserName());
      stmt.setString(2, user.getPassword());
      rs = stmt.executeQuery();
      // 若用户名密码匹配，则next指向该行数据，返回true，否则返回false
      return rs.next();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      // 关闭数据库连接操作
      try {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          stmt.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static String register(User user) {
    String userName = user.getUserName();
    String userPassword = user.getPassword();
    System.out.println("用户注册" + userName);

    Connection connection = DbConnection.getConnection();
    assert connection != null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      // 查找用户，判断该用户是否已注册
      String sql = "select * from user where name=?";
      stmt = connection.prepareStatement(sql);
      stmt.setString(1, userName);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // 插入用户数据
        String sql2 = "insert into user(name, password) values(?, ?)";
        stmt = connection.prepareStatement(sql2);
        stmt.setString(1, userName);
        stmt.setString(2, userPassword);
        int result = stmt.executeUpdate();
        System.out.println("插入新用户结果：" + result);
        if (result == 1) {
          return "注册成功！";
        }
      } else {
        System.out.println("注册失败，用户已存在！");
        return "用户已存在！";
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          stmt.close();
        }
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return "注册失败";
  }
}
