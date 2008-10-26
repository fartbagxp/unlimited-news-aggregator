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
import org.eclipse.swt.widgets.Text;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImageImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;


public class RSSReader
{

    // generate a feed back to the client to download headlines

    public static void iterateRSSFeed(String rssFeedUrl, Table table)
    {
    	try{
    		//open up a connection to the rss feed
    		URLConnection feedUrl = new URL(rssFeedUrl).openConnection();

    		//Create Feed Object
    		SyndFeedInput input = new SyndFeedInput();
    		SyndFeed feed = input.build(new XmlReader(feedUrl));

    		System.out.println("Examining rss feed:"+rssFeedUrl+"\n");
    		
    		System.out.println("Feed type="+feed.getFeedType());

    		//Iterate through object to get details
    		List list = feed.getEntries();

    		System.out.println("Feed image="+feed.getImage());
    		if (feed.getImage()!=null)
    		{
    			SyndImageImpl image = (SyndImageImpl)feed.getImage();
    			String imageInfo = "Image url:"+image.getUrl()+"\n";
    			System.out.println(imageInfo);
    		}	
    		for (int i=0 ; i < list.size(); i++){

                //display entry attributes
                SyndEntry entry = (SyndEntry)list.get(i);
                String display = "Entry:"+i;
                display += "\ntitle:"+entry.getTitle();
                display += "\nlink:"+entry.getLink();
                display += "\nauthor:"+entry.getAuthor();
                display += "\npublished:"+entry.getPublishedDate();
                display += "\nupdated:"+entry.getUpdatedDate();
                display += "\ndescription:"+entry.getDescription();
 
                display += "\ncontent size:"+entry.getContents().size();
 
                String temp = String.valueOf(entry.getDescription());
                int j = temp.indexOf("<p>");
                temp = temp.substring(22,j);
               
                //data holds all the data we need to get headlines and good stuffies!

                TableItem item = new TableItem(table, SWT.NULL);
                item.setText(entry.getTitle());
                item.setText(0, entry.getTitle());
                item.setText(1, entry.getAuthor());
                item.setText(2, String.valueOf(entry.getPublishedDate()));
                item.setText(3, String.valueOf(entry.getSource()));
                item.setText(4, entry.getLink());
               
                //model.addRow(data);
                if (entry.getContents().size()==1){
                    SyndContentImpl imp = (SyndContentImpl)entry.getContents().get(0);
                    display += "\ncontent value:"+imp.getValue(); 
                }
                display += "\n";
                System.out.println(display);
            }
    		for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
    			table.getColumn(loopIndex).pack();
    		}
    	}
    	
        // if all else fails, blah out an error
        catch(Exception e){
            e.printStackTrace();
        }
        
        finally{}
    }
   
    // make beautiful menu for user to use, lalalala!
    public static void MenuMaking(Display window, Shell shell){
        shell.setSize(500, 500);
        shell.setText("A Shell Menu Example");

        Menu menu = new Menu(shell, SWT.BAR);

        //create a file
        MenuItem file = new MenuItem(menu, SWT.CASCADE);
        file.setText("File");
        Menu filemenu = new Menu(shell, SWT.DROP_DOWN);
        file.setMenu(filemenu);
        MenuItem openItem = new MenuItem(filemenu, SWT.PUSH);
        openItem.setText("Open");
        MenuItem exitItem = new MenuItem(filemenu, SWT.PUSH);
        exitItem.setText("Exit");

        shell.setMenuBar(menu);

        shell.open();

        while (!shell.isDisposed()) {
          if (!window.readAndDispatch())
            window.sleep();
        }
        window.dispose();
    }
 
    public static void main(String[] args) {

        // make a window for all the users to see!

        Display window = new Display();
        Shell shell = new Shell(window);
       
        //create a button to get headline!

        final Button getheadlines = new Button(shell, SWT.PUSH);
        getheadlines.setBounds(5, 5, 80, 50);

        getheadlines.setText("Get Headlines");
        shell.open();
   
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

           //Bound of table
        table.setBounds(60, 60, 1000, 1000);
                     
         // simply provide a link to generate xml feeds to client!

         Listener headlinelistener = new Listener(){
        	 public void handleEvent(Event event) {
        		 iterateRSSFeed("http://rss.cnn.com/rss/cnn_topstories.rss", table);
             }
         };
   
         getheadlines.addListener(SWT.Selection, headlinelistener);
         MenuMaking(window, shell);
    
         // close everything!

         while (!shell.isDisposed()){
        	 if(!window.readAndDispatch())
        		 window.sleep();
         }
    
         // trash it!
         window.dispose();

    }
}