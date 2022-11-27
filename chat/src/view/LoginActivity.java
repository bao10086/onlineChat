package view;

import controller.LoginListener;
import controller.SignListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * @author ���ѱ�
 * @date 2022/11/23 14:54
 */
public class LoginActivity {
  private final JFrame jfIndex;
  private JLabel userText;

  public void index(){
    jfIndex.setVisible(true);
  }

  public LoginActivity() {
    JFrame jf = new JFrame();
    jf.setSize(400, 300  );
    jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    // ���ھ���
    jf.setLocationRelativeTo(null);

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 200, 40));

    JButton btnLogin = new JButton("��¼");
    JButton btnSign = new JButton("ע��");
    btnLogin.setPreferredSize(new Dimension(150, 50));
    btnSign.setPreferredSize(new Dimension(150, 50));

    panel.add(btnLogin);
    panel.add(btnSign);
    jf.setContentPane(panel);
    jf.setVisible(true);
    jfIndex = jf;

    // ��¼
    btnLogin.addActionListener(e -> {
      jf.setVisible(false);
      login();
    });

    // ע��
    btnSign.addActionListener(e -> {
      jf.setVisible(false);
      sign();
    });
  }

  private void login() {
    JFrame jf = new JFrame();
    jf.setSize(400, 300  );
    jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    jf.setLocationRelativeTo(null);

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 25));

    // �û�
    JLabel userText = new JLabel("�û���");
    JTextField userInput = new JTextField(20);
    userText.setPreferredSize(new Dimension(50, 50));

    // ����
    JLabel passwordText = new JLabel("����");
    JPasswordField passwordInput = new JPasswordField(20);
    passwordText.setPreferredSize(new Dimension(50, 50));

    // ��¼
    JButton btnLogin = new JButton("��¼");
    JButton btnReturn = new JButton("����");


    panel.add(userText);
    panel.add(userInput);
    panel.add(passwordText);
    panel.add(passwordInput);
    panel.add(btnLogin);
    panel.add(btnReturn);

    jf.setContentPane(panel);
    jf.setVisible(true);

    // ��¼��ť������
    LoginListener loginListener = new LoginListener(userInput, passwordInput, jf);
    btnLogin.addActionListener(loginListener);

    // ����
    btnReturn.addActionListener(e -> {
      jf.setVisible(false);
      index();
    });
  }

  private void sign() {
    JFrame jf = new JFrame();
    jf.setSize(400, 300  );
    jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    jf.setLocationRelativeTo(null);

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));

    // �û�
    userText = new JLabel("�û���");
    JTextField userInput = new JTextField(20);
    userText.setPreferredSize(new Dimension(60, 40));

    // ����
    JLabel passwordText = new JLabel("����");
    JPasswordField passwordInput = new JPasswordField(20);
    passwordText.setPreferredSize(new Dimension(60, 40));

    // ȷ������
    JLabel surePasswordText = new JLabel("ȷ������");
    JPasswordField surePasswordInput = new JPasswordField(20);
    surePasswordText.setPreferredSize(new Dimension(60, 40));

    // ��¼
    JButton btnSign = new JButton("ע��");
    JButton btnReturn = new JButton("����");

    panel.add(userText);
    panel.add(userInput);
    panel.add(passwordText);
    panel.add(passwordInput);
    panel.add(surePasswordText);
    panel.add(surePasswordInput);
    panel.add(btnSign);
    panel.add(btnReturn);

    jf.setContentPane(panel);
    jf.setVisible(true);

    // ע�ᰴť������
    SignListener signListener = new SignListener(userInput, passwordInput, surePasswordInput);
    btnSign.addActionListener(signListener);

    // ���ذ�ť����
    btnReturn.addActionListener(e -> {
      jf.setVisible(false);
      index();
    });
  }

  public static void dialog(String message) {
    JFrame jf = new JFrame();
    jf.setSize(300, 200  );
    jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    jf.setLocationRelativeTo(null);

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    // �û�
    JLabel userText = new JLabel(message);
    userText.setPreferredSize(new Dimension(230, 50));
    userText.setHorizontalAlignment(SwingConstants.CENTER);

    // ȷ��
    JButton btn = new JButton("ȷ��");

    panel.add(userText);
    panel.add(btn);
    jf.setContentPane(panel);
    jf.setVisible(true);

    // ������
    btn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jf.setVisible(false);
      }
    });
  }

  public static void main(String[] args) {
    new LoginActivity();

  }
}
