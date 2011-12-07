
import java.net.*;
import java.io.*;


public class Sender {
    
    private PrintWriter out;
    private PrintWriter Uout;
    private PrintWriter Passout;
    private PrintWriter Whiteout;
    

    public Sender(Socket theSocket) {
        
        try {
            out = new PrintWriter(theSocket.getOutputStream(), true);
            Uout = new PrintWriter(theSocket.getOutputStream(), true);
            
            Passout = new PrintWriter(theSocket.getOutputStream(), true);
            Whiteout = new PrintWriter(theSocket.getOutputStream(), true);
        }
        catch(IOException iE) {
        }
    }
    
    /*Closes the outgoing stream*/
    public void closeOutStream() {
        
        if(out != null) {
            out.close();
        }
    }
    
    /*Sends a message to the server*/
    public void sendAway(String s)  {
        if(out !=null) {
            out.println(s);
            //Uout.println(u);
        }
    }
    
} // End class Sender