import java.net.URL;
import java.net.MalformedURLException;

// Replaces URLs with html hrefs codes
public class links {
    public String detect(String s) {
        String [] parts = s.split("\\s");
        for( String item : parts )
            try {
                URL url = new URL(item);
                new linkpop("<a href=\"" + url + "\">"+ url + "</a> ");
            } catch (MalformedURLException e) {}
        return s;
    }
}