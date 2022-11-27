package view;

import controller.ClientReceiveListener;
import controller.ClientSendListener;
import entity.Client;
import java.awt.Color;
import java.awt.Dimension;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import util.DateUtil;

/**
 * @author ���ѱ�
 * @date 2022/11/23 19:03
 */
public class ChatActivity {
  private JTextArea text;
  private JLabel online;
  private Integer onlineNum = 0;
  private JList<String> userList;

  public ChatActivity(String userName) {
    JFrame jf = new JFrame();
    jf.setSize(720, 600);
    jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    jf.setLocationRelativeTo(null);
    JPanel panel = new JPanel(null);


    // ��ǰ�û�
    JLabel title = new JLabel(userName, SwingConstants.CENTER);
    title.setBorder(BorderFactory.createTitledBorder("��ǰ�û�"));
    title.setSize(new Dimension(700, 50));
    title.setLocation(0, 0);
    title.setBackground(Color.white);
    title.setOpaque(true);

    // �����û���Ŀ
    online = new JLabel("�����û�����:" + onlineNum.toString(), SwingConstants.CENTER);
    online.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    online.setBackground(Color.white);
    online.setOpaque(true);
    online.setLocation(2, 50);
    online.setSize(new Dimension(120, 50));

    // �����û��б�
    userList = new JList<>();
    userList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    userList.setSize(new Dimension(120, 250));
    userList.setLocation(2, 99);

    // �����
    text = new JTextArea();
    text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    text.setEditable(false);
    text.setLocation(121, 50);
    text.setSize(new Dimension(578, 299));

    // �����ı�
    JTextArea input = new JTextArea();
    input.setBorder(BorderFactory.createTitledBorder("�����뷢������"));
    input.setLocation(0, 350);
    input.setSize(new Dimension(700, 100));

    // ���Ͱ�ť
    JButton send = new JButton("����");
    send.setLocation(200, 480);
    send.setSize(new Dimension(100, 30));

    // ����ı���ť
    JButton clear = new JButton("���");
    clear.setLocation(400, 480);
    clear.setSize(new Dimension(100, 30));

    panel.add(title);
    panel.add(online);
    panel.add(userList);
    panel.add(text);
    panel.add(input);
    panel.add(send);
    panel.add(clear);
    jf.setContentPane(panel);
    jf.setVisible(true);

    try {
      Socket socket = new Socket("localhost", 8888);
      Client client = new Client(userName, socket);
      String time = DateUtil.getDate();
      // ����������͵�¼��Ϣ
      DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
      writer.writeBytes(time + "\n");
      writer.flush();
      writer.writeBytes(userName + ":is online!\n");
      writer.flush();

      // ������Ϣ������
      ClientSendListener clientSendListener = new ClientSendListener(text, input, client, userList);
      send.addActionListener(clientSendListener);

      // ������Ϣ�߳�
      ClientReceiveListener clientReceiveListener = new ClientReceiveListener(client, text, online, userList);
      new Thread((Runnable) clientReceiveListener).start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // �����Ϣ
    clear.addActionListener(e -> input.setText(""));
  }

}
