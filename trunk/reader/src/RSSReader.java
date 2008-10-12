import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndImageImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import com.sun.syndication.feed.synd.SyndContentImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class RSSReader
{

 public static void iterateRSSFeed(String rssFeedUrl)
 {
  try
  {
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
    String data[] = { String.valueOf(i+1), entry.getTitle(), temp,
            String.valueOf(entry.getPublishedDate()), rssFeedUrl,
            entry.getLink() };
    //model.addRow(data);
    if (entry.getContents().size()==1)
    {
     SyndContentImpl imp = (SyndContentImpl)entry.getContents().get(0);
     display += "\ncontent value:"+imp.getValue();   
    }
    display += "\n";
    System.out.println(display);

   }

  }
  catch(Exception e)
  {
   e.printStackTrace();
  }

 }
 
 
 public static void main(String[] args) {
     String columnNames[] = { " ", "Title", "Description", "Date", "Source", "Link" };

    
     Display window = new Display();
     Shell shell = new Shell(window);
     final Button getheadlines = new Button(shell, SWT.PUSH);
     getheadlines.setBounds(50, 40, 65, 50);
     getheadlines.setText("Get Headlines");
     shell.open();
     
     final Text text = new Text(shell, SWT.SHADOW_IN);
    
     Listener headlinelistener = new Listener(){
    	 public void handleEvent(Event event) {
    		 iterateRSSFeed("http://rss.cnn.com/rss/cnn_topstories.rss");
    	 }
     };
     
     getheadlines.addListener(SWT.Selection, headlinelistener);
 
     while (!shell.isDisposed()){
    	 if(!window.readAndDispatch())
    		 window.sleep();
    	 }
     window.dispose();
 }
}