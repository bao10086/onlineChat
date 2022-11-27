package controller;

import entity.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import mapper.LoginMapper;
import view.LoginActivity;

/**
 * @author ���ѱ�
 * @date 2022/11/25 19:52
 */
public class SignListener implements ActionListener {

  private JTextField userName;
  private JPasswordField passwordField;
  private JPasswordField surePasswordInput;

  public SignListener(JTextField userName, JPasswordField passwordField,
      JPasswordField surePasswordInput) {
    this.userName = userName;
    this.passwordField = passwordField;
    this.surePasswordInput = surePasswordInput;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String name = userName.getText();
    String password1 = new String(passwordField.getPassword());
    String password2 = new String(surePasswordInput.getPassword());
    System.out.println(password1);
    System.out.println(password2);
    // ��֤��������������Ƿ�һ��
    if (password1.equals(password2)) {
      User user = new User(name, password1);
      // �Խ����ݿ�
      String ans = LoginMapper.register(user);
      LoginActivity.dialog(ans);
    } else {
      LoginActivity.dialog("�����������벻һ�£����������룡");
    }
  }
}
