package controller;

import entity.Client;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author 曾佳宝
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
      // 获取传来的信息
      String time = reader.readLine();
      String message = reader.readLine();
      String name = message.split(":")[0];
      String head = message.split(":")[1];
      String content = message + "\n";
      // 打印传来的信息
      System.out.println(time);
      System.out.println(message);
      // 接收到用户登录的消息，向在线用户列表添加该用户
      if (head.equals("is online!")) {
        Client client = new Client(name, socket);
        Server.addClient(client);
        String clients = "online:" + Server.getClientsName();
        // 向所有在线用户广播上线通知
        for (Client tmp : Server.getClientList()) {
          Socket send = tmp.getSocket();
          DataOutputStream writer = new DataOutputStream(send.getOutputStream());
          writer.writeBytes(clients + "\n");
          writer.flush();
        }
      }
      // 接收到用户下线的消息，在在线用户列表删除该用户
      else if (head.toUpperCase().contains("BYE")) {
        System.out.print(name + " leaves！");
        Server.removeClient(name);
        String clients = "online:" + Server.getClientsName();
        // 向所有在线用户广播下线通知
        for (Client tmp : Server.getClientList()) {
          Socket send = tmp.getSocket();
          DataOutputStream writer = new DataOutputStream(send.getOutputStream());
          writer.writeBytes(clients + "\n");
          writer.flush();
        }
      }
      // 私聊消息
      if (head.startsWith("(")) {
        // 获得私聊对象的名字和私聊内容
        String certainName = "";
        for (int i = 1; i < head.length(); i++) {
          if (head.charAt(i) == ')') {
            certainName = head.substring(1, i);
            head = head.substring(i + 1);
            break;
          }
        }
        // 向私聊对象和自身发送消息
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
      // 向所有在线用户广播消息
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
