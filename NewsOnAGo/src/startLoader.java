import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import org.eclipse.swt.widgets.TableItem;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImageImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;


//provides a startup method
public class startLoader {
	
	Display display;
	Shell shell; 
	
	// create a new shell and display
	private startLoader(){
		display = new Display();
		shell = new Shell(display);
		new GUI(display, shell);
	}
	
	// program starts running from here! 
    public static void main(String[] args) {
        new startLoader();
   }
}
