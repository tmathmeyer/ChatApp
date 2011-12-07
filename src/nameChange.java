import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class nameChange
{
    public static void popup(Sender send, int i){ 
        JTextField firstName = new JTextField();
        JPasswordField password = new JPasswordField();
        
        final JComponent[] inputs = new JComponent[] {
        new JLabel("Enter your username and password. "),
        new JLabel("username: "),
        firstName,
        new JLabel("password: "),
        password
        };
        
        JOptionPane.showMessageDialog(null, inputs, "Enter your preferred username", JOptionPane.PLAIN_MESSAGE);
            
        String name = firstName.getText();
        String pword = password.getText();
        
        String command = "";
        if (i == 1){
            command = "/login ";
        }
        if (i == 2 ){
            command = "/register ";
        }
        
        send.sendAway(command + name + ":" + pword);

    }
}
