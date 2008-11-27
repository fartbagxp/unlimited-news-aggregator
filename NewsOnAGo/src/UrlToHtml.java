import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The UrlToHtml class creates an HTML string from an URL link
 *  
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class UrlToHtml {
	
	public static String getHtml(String urlToRead) {
        URL url; 				// The URL to read
        HttpURLConnection conn; // The actual connection to the web page
        BufferedReader rd; 		// Used to read results from the web page
        String line; 			// An individual line of the web page HTML
        String result = ""; 	// A long string containing all the HTML
        
        // returns a string containing the html of a web page
        try {
           url = new URL(urlToRead);
           conn = (HttpURLConnection) url.openConnection();
           conn.setRequestMethod("GET");
           rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           while ((line = rd.readLine()) != null) {
              result += line;
           }
           rd.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return result;
	}
};
