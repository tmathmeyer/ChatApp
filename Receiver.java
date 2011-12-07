
import javax.swing.*;
import java.net.*;
import java.io.*;


public class Receiver implements Runnable {
    
    private BufferedReader in;
    private String host;
    private Chat chat;
    private Sender sender;
    
    public Receiver(Chat chat, Socket theSocket, Sender s)  {
        sender = s;
        this.chat = chat;
        try {
            in = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
            chat.setTitle("Java Chat Client on: "+theSocket.getInetAddress().getHostName()+
            " :port " + theSocket.getPort());
        }
        catch (IOException ioe) {
        }
    }
    
    /*Reads messages from the server and makes callbacks to the GUI class*/
    public void run() {
        
        try{
            String inStream;
            while (( inStream = in.readLine()) != null) {
                if (inStream.substring(0, 4).equals("P!NG")){
                    sender.sendAway("P0NG");
                }
                else {
                    chat.setIncomingMessage(inStream);
                } 
            } 
        }
        catch (IOException ioe) {
        } 
    } 
    
    /*Closes the stream*/
    public void closeInStream() {
        
        try {
            if(in !=null)
                in.close();
        }
        catch(IOException iE) {
        } 
    } 
} // End class Receiver	