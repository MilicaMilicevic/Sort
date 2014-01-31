package src.server;

import java.net.Socket;
import java.net.ServerSocket;

public class Server {
  private static final int SERVER_PORT=444;
  
  public static void main(String[] args){
    try{
      System.out.println("Server is started.");
      ServerSocket serverSocket=new ServerSocket(SERVER_PORT);
      while(true){
        Socket socket=serverSocket.accept();
        (new ServerThread(socket)).start();
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}