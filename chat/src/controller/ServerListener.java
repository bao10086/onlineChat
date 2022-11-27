package controller;

import entity.Client;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author ���ѱ�
 * @date 2022/11/25 21:27
 */
public class ServerListener implements Runnable {

  private final BufferedReader reader;
  private final Socket socket;

  public ServerListener(BufferedReader reader, Socket socket) {
    this.reader = reader;
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      // ��ȡ��������Ϣ
      String time = reader.readLine();
      String message = reader.readLine();
      String name = message.split(":")[0];
      String head = message.split(":")[1];
      String content = message + "\n";
      // ��ӡ��������Ϣ
      System.out.println(time);
      System.out.println(message);
      // ���յ��û���¼����Ϣ���������û��б���Ӹ��û�
      if (head.equals("is online!")) {
        Client client = new Client(name, socket);
        Server.addClient(client);
        String clients = "online:" + Server.getClientsName();
        // �����������û��㲥����֪ͨ
        for (Client tmp : Server.getClientList()) {
          Socket send = tmp.getSocket();
          DataOutputStream writer = new DataOutputStream(send.getOutputStream());
          writer.writeBytes(clients + "\n");
          writer.flush();
        }
      }
      // ���յ��û����ߵ���Ϣ���������û��б�ɾ�����û�
      else if (head.toUpperCase().contains("BYE")) {
        System.out.print(name + " leaves��");
        Server.removeClient(name);
        String clients = "online:" + Server.getClientsName();
        // �����������û��㲥����֪ͨ
        for (Client tmp : Server.getClientList()) {
          Socket send = tmp.getSocket();
          DataOutputStream writer = new DataOutputStream(send.getOutputStream());
          writer.writeBytes(clients + "\n");
          writer.flush();
        }
      }
      // ˽����Ϣ
      if (head.startsWith("(")) {
        // ���˽�Ķ�������ֺ�˽������
        String certainName = "";
        for (int i = 1; i < head.length(); i++) {
          if (head.charAt(i) == ')') {
            certainName = head.substring(1, i);
            head = head.substring(i + 1);
            break;
          }
        }
        // ��˽�Ķ������������Ϣ
        for (Client client : Server.getClientList()) {
          if (client.getName().equals(certainName) || client.getName().equals(name)) {
            Socket send = client.getSocket();
            DataOutputStream writer = new DataOutputStream(send.getOutputStream());
            writer.writeBytes(time + "\n");
            writer.flush();
            content = name + ":" + head + "\n";
            writer.writeBytes(content);
            writer.flush();
          }
        }
        return;
      }
      // �����������û��㲥��Ϣ
      for (Client client : Server.getClientList()) {
        Socket send = client.getSocket();
        DataOutputStream writer = new DataOutputStream(send.getOutputStream());
        writer.writeBytes(time + "\n");
        writer.flush();
        writer.writeBytes(content);
        writer.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
