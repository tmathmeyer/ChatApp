import java.io.*;
public class save
{
    public save(String conversation)
    {
        try
        {
            FileWriter fstream = new FileWriter("saved conversation.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(conversation);
            out.close();
        }
        catch (Exception e){}
    }
}
