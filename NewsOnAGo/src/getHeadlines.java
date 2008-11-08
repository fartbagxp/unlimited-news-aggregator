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


/* provides a method to get headlines */
public class getHeadlines {
	
	public static void iterateRSSFeed(String rssFeedUrl, Table table){
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
	            if (feed.getImage()!=null){
	                SyndImageImpl image = (SyndImageImpl)feed.getImage();
	                String imageInfo = "Image url:"+image.getUrl()+"\n";
	                System.out.println(imageInfo);
	            }   
	            
	            // display multiple entries from a single aggregation
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
	              
	                /* data holds all the data we need to get headlines and other various attributes
	                 * parameter:	item 0 = title
	                 * 			  	item 1 = author
	                 * 				item 2 = published date
	                 * 				item 3 = source of article
	                 * 				item 4 = link of article
	                 */
	                
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
	}
}
