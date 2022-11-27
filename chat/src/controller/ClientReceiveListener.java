package controller;

import entity.Client;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 * @author ���ѱ�
 * @date 2022/11/26 1:03
 */
public class ClientReceiveListener implements Runnable {
  private Client client;
  private JTextArea text;
  private JLabel online;
  private JList<String> userList;

  public ClientReceiveListener(Client client, JTextArea text, JLabel online,
      JList<String> userList) {
    this.client = client;
    this.text = text;
    this.online = online;
    this.userList = userList;
  }

  @Override
  public void run() {
    try {
      Socket socket = client.getSocket();
      BufferedReader reader = client.getReader();
      // ���նԷ����������
      while (true) {
        String msg = reader.readLine();
        String[] clients= msg.split(":");
        System.out.println(msg);
        if (clients[0].equals("online")) {
          String[] names = clients[1].split(" ");
          userList.setListData(names);
          online.setText("�����û�����:" + names.length);
          continue;
        }
        else {
          text.append(msg + "\n");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
