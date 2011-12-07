import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class linkpop
{
    public linkpop(String s){
        final JComponent[] inputs = new JComponent[] {
        new JLabel("<html>" + s),
        };
        
        JOptionPane.showMessageDialog(null, inputs, "someone sent it ^^", JOptionPane.PLAIN_MESSAGE);
    }
}
