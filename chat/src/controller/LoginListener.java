package controller;

import entity.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import mapper.LoginMapper;
import view.ChatActivity;
import view.LoginActivity;

/**
 * @author ���ѱ�
 * @date 2022/11/23 15:00
 */
public class LoginListener implements ActionListener {
  private JTextField userName;
  private JPasswordField passwordField;
  private JFrame jf;

  public LoginListener(JTextField userName, JPasswordField passwordField, JFrame jf) {
    this.userName = userName;
    this.passwordField = passwordField;
    this.jf = jf;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String name = userName.getText();
    String password = new String(passwordField.getPassword());
    User user = new User(name, password);
    System.out.println("�û�" + name + "���ڵ�¼�С���");
    if (LoginMapper.login(user)) {
      System.out.println("�û�" + name + "��¼�ɹ���");
      jf.setVisible(false);
      new ChatActivity(name);
    }
    else {
      LoginActivity.dialog("�û��������벻ƥ�䣡");
    }
  }
}
