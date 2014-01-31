package src.server;

import java.net.Socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Comparator;

public class ServerThread extends Thread {
  private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;
  
  private Map<String,String> pairs; //<username,action>
  
  public ServerThread(Socket arg) throws IOException {
      socket=arg;
      in=new ObjectInputStream(socket.getInputStream());
      out=new ObjectOutputStream(socket.getOutputStream());
      pairs=new HashMap<String,String>();
    }    
  
  @Override
  public void run(){
    try{
      out.writeObject("Send username!");
      String userName=(String)in.readObject();
      System.out.println(userName); //server cita username i prijavljuje ga u kolekciju posto uradi prvu akciju!
      out.writeObject("Send command!");
      String action=(String)in.readObject(); //cita komandu/akciju!
      System.out.println(userName+" - "+action);
      
      pairs.put(userName,action); 

      if(action.equals("101IR")){ //klijent zeli da sortira niz int u rastudem redoslijedu
        int[] array=(int[])in.readObject();
        Arrays.sort(array);
        out.writeObject(array);
      }
      else if(action.equals("101IO")){
        int[] array=(int[])in.readObject();
        Arrays.sort(array,array.length-1,0);
        out.writeObject(array);
      }
      else if(action.equals("102ZR")){
        String[] array=(String[])in.readObject();
        Arrays.sort(array);
        out.writeObject(array);
      }
      else if(action.equals("102ZO")){
        String[] array=(String[])in.readObject();
        Arrays.sort(array,new Comparator<String>(){
          @Override
          public int compare(String a,String b){
            return a.charAt(0)-b.charAt(0);
          }
        });
        out.writeObject(array);
      }
      else if(action.equals("103AK"))
        out.writeObject(pairs);
      else 
        System.out.println(userName+" is disconnected.");
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}