import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ipconfig
{
    static JTextField port = new JTextField("2022");
    public static String popup(){ 
        JTextField host = new JTextField("ozymandias.servequake.com");
        
        final JComponent[] inputs = new JComponent[] {
                new JLabel("host :"),
                host,
                new JLabel("port :"),
                port
        };
            JOptionPane.showMessageDialog(null, inputs, "enter a hostname or an IP address", JOptionPane.PLAIN_MESSAGE);
            
            String name = host.getText();
        
        return name;
    }
    public static int port(){
        String ports = port.getText();
        
        int portz = Integer.parseInt(ports);
        
        return portz;
        
    }
}
