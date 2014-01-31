package src.client;

import java.net.Socket;
import java.net.InetAddress;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

import src.protocol.Protocol;

public class Client extends Thread {
  private String userName;
  
  private static final int SERVER_PORT=444;
  
  private Socket clientSocket;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  
  public Client() throws IOException {
    userName="Client-"+new Random().nextInt(30);
    clientSocket=new Socket(InetAddress.getLocalHost(),SERVER_PORT);
    out=new ObjectOutputStream(clientSocket.getOutputStream());
    in=new ObjectInputStream(clientSocket.getInputStream());
  }
  
  @Override
  public void run(){
    try{
      System.out.println(userName+" is started.");
      System.out.println("Server -> "+userName+": "+(String)in.readObject());
      out.writeObject(userName);                      //prijavljivanje!
      System.out.println("Server -> "+userName+": "+(String)in.readObject());
     
      String action=Protocol.getRandom();   //slanje komande
      out.writeObject(action);   //klijent tokom rada preduzima samo jednu akciju!
      
      if(action.equals("101IR") || action.equals("101IO")){
        out.writeObject(new int[]{1,3,9,7});
        int[] sortedArray=(int[])in.readObject();
        for(int i=0;i<sortedArray.length;i++)
          System.out.println(sortedArray[i]);
      }
      else if(action.equals("102ZR") || action.equals("102ZO")){
        out.writeObject(new String[]{"Milica","Dajana","Nena","Ana"});
        String[] sortedArray=(String[])in.readObject();
        for(int i=0;i<sortedArray.length;i++)
          System.out.println(sortedArray[i]);
      }
      else if(action.equals("103AK")) {
        Map<String,String> pairs=(HashMap<String,String>)in.readObject();
        Set userNames=pairs.keySet();
        Iterator iteratorUN=userNames.iterator();
        while(iteratorUN.hasNext()){
          String userName=(String)iteratorUN.next();
          System.out.println(userName+" - "+pairs.get(userName));
        }
      }
      else 
        System.out.println(userName+" will disconnect.");       
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args){
    try{
      new Client().start();
      new Client().start();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}