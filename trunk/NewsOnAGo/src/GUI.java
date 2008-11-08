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

//creates a graphical user interface (GUI) for the NewsOnAGo feed aggregator
public class GUI
{
	/* Note: Displays are responsible from managing event loops and controlling threads while
	 *       Shell is the window of the application.
	 */

	// other objects have to access display 
	public static Display display;
	
	// other objects have to access shell 
    public static Shell shell;
    
    // For classes who needs to access the MainController 
    public static GUI newsOnAGo;

    // a GUI class to initial the shell and creates a menu for the shell
    public GUI(Display display, Shell shell){
    	GUI.display = display;
    	GUI.shell = shell;
    	GUI.newsOnAGo = this;
    	menuMaking();
    }
  
    // make beautiful menu for user to use, lalalala!
    public static void menuMaking(){
    	
        //create a "Get Headlines" button to get headline!
        final Button getheadlines = new Button(shell, SWT.PUSH);
        getheadlines.setBounds(5, 5, 80, 50);
        getheadlines.setText("Get Headlines");
        
        final Button getArticles = new Button(shell, SWT.PUSH);
        getArticles.setBounds(5, 55, 80, 50);
        getArticles.setText("Get Articles");        shell.open();
        
        //create a window
        shell.setSize(1000, 900);
        shell.setText("A Shell Menu Example");

        Menu menu = new Menu(shell, SWT.BAR);

        //creating a menu bar
        MenuItem file = new MenuItem(menu, SWT.CASCADE);
        file.setText("File");
        Menu filemenu = new Menu(shell, SWT.DROP_DOWN);
        file.setMenu(filemenu);
        MenuItem openItem = new MenuItem(filemenu, SWT.PUSH);
        openItem.setText("Open");
        MenuItem exitItem = new MenuItem(filemenu, SWT.PUSH);
        exitItem.setText("Exit");
  
        // create columns of news attributes
        final Table table = new Table(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL
                | SWT.H_SCROLL);
        table.setHeaderVisible(true);
        String[] titles = { "Title", "Author", "Date", "Source", "Link" };

        // create indexes of boxes for user to click on!
        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
              TableColumn column = new TableColumn(table, SWT.NULL);
              column.setText(titles[loopIndex]);
        }
        
        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
            table.getColumn(loopIndex).pack();
        }

        // Bound of table
        table.setBounds(85, 5, 900, 600);
                    
        /* simply provide a link to generate xml feeds to client
         * if user attempts to get headlinesW
         * generate a feed back to the client to download headlines
         */
        getheadlines.addListener(SWT.Selection, new Listener(){
            public void handleEvent(Event event) {
                getHeadlines.iterateRSSFeed("http://rss.cnn.com/rss/cnn_topstories.rss", table);
            }
        });
        
        // for every headline check box that is checked, get the article HTML
        Listener articleListener = new Listener(){
        	public void handleEvent(Event event){
        		String articleHTML = "";
       		 	TableItem[] headlines = table.getItems();
       		 	boolean isChecked = false;
       		  	for (int i = 0; i < headlines.length; i++) {
       		  		isChecked = headlines[i].getChecked();
       		  		if(isChecked){
       		  			articleHTML = Article.getArticle(headlines[i].getText(4));
       		  			System.out.println(articleHTML);
       		  		}
       		  	}		 
       		  	
       		 }
       	 };
  
 
        getArticles.addListener(SWT.Selection, articleListener);
        
        // close the shell if the menu item "Exit" is pushed
        exitItem.addListener(SWT.Selection, new Listener(){
            public void handleEvent(Event event) {
                GUI.shell.close();
            }
        }); 

        shell.setMenuBar(menu);
        shell.open();
        
        // close the shell
        while (!shell.isDisposed()) {
          if (!display.readAndDispatch())
            display.sleep();
        }
        display.dispose();
    }
}
