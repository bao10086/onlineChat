package entity;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Ôø¼Ñ±¦
 * @date 2022/11/23 19:32
 */
public class Client {

  private String name;
  private Socket socket;
  private BufferedReader reader;

  public Client(String name, Socket socket) {
    this.name = name;
    this.socket = socket;
    try {
      this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public BufferedReader getReader() {
    return reader;
  }

  public void setReader(BufferedReader reader) {
    this.reader = reader;
  }

  public void closeSocket() {
    try {
      reader.close();
      System.out.println(11111);
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return "Client{" +
        "name='" + name + '\'' +
        ", socket=" + socket +
        '}';
  }
}
