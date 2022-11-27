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
 * @author 曾佳宝
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
    // 私聊消息
    if (index >= 0) {
      content += "(" + list.getSelectedValue() + ")";
    }
    content += send + "\n";
    StringBuilder total = new StringBuilder(time + content);
    try {
      // 向服务器线程发送消息
      Socket socket = new Socket("localhost", 8888);
      DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
      writer.writeBytes(total.toString());
      writer.flush();
      writer.close();
      socket.close();

      // 清空输入框
      input.setText("");
      // 终端输出
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
