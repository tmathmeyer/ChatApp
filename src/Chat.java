/**
*Author:  Ted Meyer
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.JScrollBar;
import java.util.*;



public class Chat extends JFrame implements ActionListener {
    private String host;
    private int port;
    private Socket theSocket;
    private Thread receiveactivity; 
    private static String usage = "usage: java Chat host port\n" + "example:java Chat this.is.the.host.address 7000";
    links url = new links();
        
    private JTextField writeField = new JTextField(46);
    private JTextArea messageArea = new JTextArea();
    private JScrollPane jsp = new JScrollPane(messageArea);
    private JPanel up = new JPanel(),down=new JPanel();
    private JPanel middle = new JPanel();
    private JButton sendButton = new JButton();
    
    JMenuItem quit = new JMenuItem("quit");
    JMenuItem username = new JMenuItem("login");
    JMenuItem reg = new JMenuItem("register");
    JMenuItem clear = new JMenuItem("Clear");
    JMenuItem save = new JMenuItem("save conversation");
    JMenuItem filter = new JMenuItem("Create Filter");
    JMenuItem group = new JMenuItem("Create Group");
    
    private Receiver receiver; 
    private Sender sender;
    private JScrollBar bar = new JScrollBar();
    private JScrollBar bar2 = new JScrollBar();
    private JTextArea online = new JTextArea();
    private JScrollPane jsp2 = new JScrollPane(online);
    JMenuBar menu = new JMenuBar();
    int height = 0;
    public Chat(String host, int port)throws IOException {
        this.host=host;
        this.port=port;
        
        jsp.setVerticalScrollBar(bar);
        jsp2.setVerticalScrollBar(bar2);
        
        JMenu options = new JMenu("File");
        JMenu user = new JMenu("Account");
        JMenu chat = new JMenu("Chat");
        
        menu.add(options);
        menu.add(user);
        menu.add(chat);
        
        options.add(clear);
        options.add(quit);
        options.add(save);
        user.add(reg);
        user.add(username);
        user.add(filter);
        chat.add(group);
        
        // Layout of the south panel
        down.setLayout(new FlowLayout(FlowLayout.RIGHT));
        down.setBackground(Color.lightGray);
        down.add(writeField);
        down.add(sendButton);
        
        
        // Prevent changes in the message area
        messageArea.setEditable(false);
        messageArea.setOpaque(false);
        online.setEditable(false);
        online.setColumns(10);
        
        
        
        
        // Layout of the text field
        writeField.setText(" ");
        writeField.requestFocus();
        
        
        // Text for the buttons
        sendButton.setText("SEND");
        quit.setText("QUIT");
        
        
        /*Adds listener*/
        sendButton.addActionListener(this);
        quit.addActionListener(this);
        writeField.addActionListener(this);
        save.addActionListener(this);
        username.addActionListener(this);
        filter.addActionListener(this);
        clear.addActionListener(this);
        reg.addActionListener(this);
        group.addActionListener(this);
        
        
        // Adds panels
        super.add(down,BorderLayout.SOUTH);
        super.add(up,BorderLayout.NORTH);
    
    
        //Adds scroll area with text area
        //tabbedPane.addTab("#home", jsp);
        super.add(jsp, BorderLayout.CENTER);
        super.add(jsp2,BorderLayout.EAST);
        
        //tabbedPane.add("herro", panel);
        super.setJMenuBar(menu);
        
        
        //Sets the window size and makes it all visible
        setSize(605,400);
        show();
        
        super.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    sender.closeOutStream();
                    receiver.closeInStream();
                    if(theSocket!=null){
                        theSocket.close();
                    }
                    System.exit(0);
                }
                catch(Exception iE) {}
                System.exit(0); 
            }
        });
        
        try {
            theSocket = new Socket(host, port);
        }
        catch(Exception uHe) {
            JOptionPane.showMessageDialog(null, "alert", "sorry, no server on that port or host. try a different combo ;)", JOptionPane.ERROR_MESSAGE);
            setTitle("Java Chat Client");
            try {
                Thread.sleep(3000);
                System.exit(0);
            }
            catch(InterruptedException e) {}
        }
        sender=new Sender(theSocket);
        receiver=new Receiver(this, theSocket, sender);
        receiveactivity= new Thread(receiver); 
        receiveactivity.start();
    }
    
    public void clearWriteField() {
        writeField.setText("");
    }
    
    public void setIncomingMessage(String s) {
        String test1 = s.substring(0,1);
        this.toFront();
        if (test1.equals("@")){
            fillUsers(s);
        }
        else {
            messageArea.append("\n" + url.detect(s));
        }
        try {
            Thread.sleep(100);
            Scroll();
        }
        catch(InterruptedException e) {}
    }
    
    public void Scroll(){
        height = messageArea.getHeight();
        bar.setValue(height + 32);
    }
    
    public void clearScreen() {
        messageArea.setText("");
    }
    
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==writeField || ae.getSource()==sendButton) {
            try {
                if (!writeField.getText().equals(""))sender.sendAway((writeField.getText().trim()));
                clearWriteField();
                height = messageArea.getHeight();
            }
            catch(Exception e) {
                messageArea.append("\n Not able to send message at this time");
            }
            bar.setValue(height + 32);
        }
        
        else if(ae.getSource()==quit) {
            try {
                sender.closeOutStream();
                receiver.closeInStream();
                if(theSocket!=null){
                    theSocket.close();
                }
            }
            catch(Exception iE) {
                messageArea.append("\n Please wait...having some trouble closing..");
            }
            System.exit(0); 
        }
        
        else if(ae.getSource() == username) {
            nameChange.popup(sender, 1);
        }
        
        else if(ae.getSource() == save) {
            new save(messageArea.getText());
        }
        
        else if (ae.getSource() == clear) {
            clearScreen();
        }
        
        else if (ae.getSource() == filter){
             
        }
        else if (ae.getSource()==reg){
            nameChange.popup(sender, 2);
        }
        else if (ae.getSource()==group){
            sender.sendAway("/create "+JOptionPane.showInputDialog("enter a group name"));
        }
    }
    
    public void sendMessage(String s){
        sender.sendAway(s.trim());
    }
    
    public static void main (String[] args) {
        
        new ipconfig();
        String host = ipconfig.popup();
        
        int port=ipconfig.port();
        

          
        
        try {
             new Chat(host, port); // starts
        }
        catch(Exception nFe) {}
 
        
    }
    
    public void fillUsers(String s){
        String users = s.substring(2, s.length()-1);
        String print = "---HOME---" + "\n";
        while(users.length() > 0){
            int bp = users.indexOf(",");
            String name = "g";
            if (bp != -1){
                name = users.substring(0, bp).trim();
                users = users.substring(bp+1);
            }
            else{
                name = users.substring(0).trim();
                users = "";
            }
            
            if (name.length() > 3 && name.substring(0, 3).equals("{u}")){
                print += name.substring(3) + "\n";
            }
            else {
                String print2 = "";
                int a = (10-name.length())/2;
                int b = 10-name.length()-a;
                for (int i = 0; i < a; i++){
                    print2 += "-";
                }
                print2 += name;
                for (int i = 0; i < b; i++){
                    print2 += "-";
                }
                print += "\n" + print2 + "\n";
            }
        }
        
        online.setText(print);
    }
    
    public void startupLogIn(String name, String pword){
        sender.sendAway("/login " + name + ":" + pword);
    }
    public void startupRegister(String name, String password){
        sender.sendAway("/register "+name+":"+password);
        startupLogIn(name, password);
    }
}