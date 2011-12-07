import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.BorderFactory; 
import javax.swing.border.Border;

public class Menu extends JPanel implements ActionListener
{
    Image img;
    JTextField username = new JTextField(13);
    JPasswordField password = new JPasswordField(13);
    
    JButton l = new JButton("login");
    JButton r = new JButton("register");
    JButton o = new JButton("options");
    
    JPanel u = new JPanel();
    JPanel p = new JPanel();
    JPanel b = new JPanel();
    
    JPanel con = new JPanel();
    
    String host = "ozymandias.servequake.com";
    int port = 2022;
    
    JFrame frame = new JFrame();
    Chat c;
    public Menu()
    {
        try{
            img = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("back1.jpg"), "back1.jpg"));
        }catch(Exception skduf){}
        l.addActionListener(this);
        r.addActionListener(this);
        o.addActionListener(this);
        frame.setResizable(false);
        
        GridBagConstraints constr = new GridBagConstraints();
        setLayout(new GridBagLayout());
        
        frame.add(this);
        
        con.setLayout(new GridLayout(3,1));
        
        u.add(new JLabel("username")); u.add(username);
        p.add(new JLabel("password")); p.add(password);
        
        b.add(l); b.add(r); b.add(o);
        
        con.add(u); con.add(p); con.add(b);
        
        add(con);
        con.setOpaque(false);
        
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        con.setBorder(compound);
        
        frame.setSize(img.getWidth(null), img.getHeight(null));
        frame.show();
    }
    public void paintComponent(Graphics g){
        g.drawImage(img, 0, 0, null);
        
    }
    public void actionPerformed(ActionEvent ee){
        JButton e = (JButton)ee.getSource();
        if (e.equals(l) && !username.getText().equals("")){
            try{
                c = new Chat(host, port);
                c.startupLogIn(username.getText(), password.getText());
                frame.setVisible(false);
            }catch(Exception ex){}
        }
        else if (e.equals(r) && !username.getText().equals("")){
            try{
                String pass = JOptionPane.showInputDialog("please re-enter your password");
                if (pass.equals(password.getText())){
                    c = new Chat(host, port);
                    c.startupRegister(username.getText(), password.getText());
                    frame.setVisible(false);
                }
            }catch(Exception ex){}
        }
        else if (e.equals(o)){
            new ipconfig();
            host = ipconfig.popup();
            port=ipconfig.port();
        }
    }
    public static void main(String[] args){
        new Menu();
    }
}
