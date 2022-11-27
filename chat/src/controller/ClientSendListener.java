package controller;

import entity.Client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JList;
import javax.swing.JTextArea;
import util.DateUtil;

/**
 * @author ���ѱ�
 * @date 2022/11/25 20:04
 */
public class ClientSendListener implements ActionListener {

  private JTextArea textArea;
  private final String userName;
  private JTextArea input;
  private Client client;
  private JList<String> list;

  public ClientSendListener(JTextArea textArea, JTextArea input, Client client,
      JList<String> list) {
    this.textArea = textArea;
    this.userName = client.getName();
    this.input = input;
    this.client = client;
    this.list = list;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String send = input.getText();
    String time = DateUtil.getDate() + "\n";
    Integer index = list.getSelectedIndex();
    String content = userName + ":";
    // ˽����Ϣ
    if (index >= 0) {
      content += "(" + list.getSelectedValue() + ")";
    }
    content += send + "\n";
    StringBuilder total = new StringBuilder(time + content);
    try {
      // ��������̷߳�����Ϣ
      Socket socket = new Socket("localhost", 8888);
      DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
      writer.writeBytes(total.toString());
      writer.flush();
      writer.close();
      socket.close();

      // ��������
      input.setText("");
      // �ն����
      System.out.print(total);

      if (send.equalsIgnoreCase("BYE")) {
        client.getSocket().close();
        System.exit(1);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
