package controller;

import entity.Client;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 曾佳宝
 * @date 2022/11/25 20:32
 */
public class Server {
  private static List<Client> clientList = new ArrayList<>();
  private ServerSocket serverSocket = null;

  public Server() {
    try {
      serverSocket = new ServerSocket(8888);
      while (true) {
        Socket socket = serverSocket.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // 创建线程对象
        ServerListener serverListener = new ServerListener(reader, socket);
        Thread t = new Thread(serverListener);
        // 启动线程
        t.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static List<Client> getClientList() {
    return clientList;
  }

  public void setClientList(List<Client> clientList) {
    clientList = clientList;
  }

  public ServerSocket getServerSocket() {
    return serverSocket;
  }

  public void setServerSocket(ServerSocket serverSocket) {
    serverSocket = serverSocket;
  }

  public static void addClient(Client client) {
    clientList.add(client);
  }

  public static void removeClient(String name) {
    Iterator<Client> iterator = clientList.iterator();
    while (iterator.hasNext()){
      Client next = iterator.next();
      if(next.getName().equals(name)){
        iterator.remove();
      }
    }
  }

  public static String getClientsName() {
    String name = "";
    for (Client client: clientList) {
      name += client.getName() + " ";
    }
    return name;
  }
}
