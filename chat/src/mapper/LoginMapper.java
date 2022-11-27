package mapper;

import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author ���ѱ�
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
      // ���û�������ƥ�䣬��nextָ��������ݣ�����true�����򷵻�false
      return rs.next();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      // �ر����ݿ����Ӳ���
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
    System.out.println("�û�ע��" + userName);

    Connection connection = DbConnection.getConnection();
    assert connection != null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      // �����û����жϸ��û��Ƿ���ע��
      String sql = "select * from user where name=?";
      stmt = connection.prepareStatement(sql);
      stmt.setString(1, userName);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // �����û�����
        String sql2 = "insert into user(name, password) values(?, ?)";
        stmt = connection.prepareStatement(sql2);
        stmt.setString(1, userName);
        stmt.setString(2, userPassword);
        int result = stmt.executeUpdate();
        System.out.println("�������û������" + result);
        if (result == 1) {
          return "ע��ɹ���";
        }
      } else {
        System.out.println("ע��ʧ�ܣ��û��Ѵ��ڣ�");
        return "�û��Ѵ��ڣ�";
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
    return "ע��ʧ��";
  }
}
