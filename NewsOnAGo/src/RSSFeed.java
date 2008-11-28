
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Vector;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * The RSSFeed class opens a new connection to the RSS URL link given to it
 * and updates the table in the headline tab with headlines
 *  
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class RSSFeed {
	
	private static Vector<String[]> headlines = new Vector<String[]>();
	
	public static Vector<String[]> iterateRSSFeed(String src, String rssFeedUrl){
		
		try{
			
	    	// establish a connection to the RSS feed
	        URLConnection feedUrl = new URL(rssFeedUrl).openConnection();

	        // create Feed Object
	        SyndFeedInput input = new SyndFeedInput();
	        SyndFeed feed = input.build(new XmlReader(feedUrl));
	        
	        // Coding test: tracing statement
//	        System.out.println("Examining rss feed:"+rssFeedUrl+"\n");
//	        System.out.println("Feed type="+feed.getFeedType());

	        // iterate through object to get details
	        List<?> list = feed.getEntries();
	            
	        // display multiple entries from a single aggregation
	        for (int i=0 ; i < list.size(); i++){
		            	
	        	// display entry attributes
		        SyndEntry entry = (SyndEntry)list.get(i);
		        	
		        String display = "Entry:"+i;
		        display += "\ntitle:"+entry.getTitle();
		        display += "\nlink:"+entry.getLink();
		        display += "\nauthor:"+entry.getAuthor();
		        display += "\npublished:"+entry.getPublishedDate();
		        display += "\nupdated:"+entry.getUpdatedDate();
		        display += "\ndescription:"+entry.getDescription();
		 
		        display += "\ncontent size:"+entry.getContents().size();
		 
		        String descriptionString = String.valueOf(entry.getDescription());
		        int infoEnd = descriptionString.indexOf("<");
		        
		        descriptionString = descriptionString.substring(22, infoEnd); 
		        
		        // model.addRow(data);
		        if (entry.getContents().size()==1){
		            SyndContentImpl imp = (SyndContentImpl)entry.getContents().get(0);
		            display += "\ncontent value:"+imp.getValue();
		        }
		        display += "\n";
		                
		        /* data holds all the data we need to get headlines and other various attributes
		         * parameter:	item 0 = title
		         * 			  	item 1 = published date
		         * 				item 2 = source of article
		         * 				item 3 = link of article
		         * 				item 4 = description of article
		         */
		         
		        
		         String[] stringArray = {"", "", "", "", ""};
		         stringArray[0] = entry.getTitle();
		         stringArray[1] = String.valueOf(entry.getPublishedDate());
		         stringArray[2] = src;
		         stringArray[3] = entry.getLink();
		         stringArray[4] = descriptionString;
		         headlines.add(i, stringArray);
		    }
	        
		}    
	    // if all else fails, blah out an error
	    catch(Exception e){
	        e.printStackTrace();
	        
	        //Coding Test: exception catching
//	        System.out.println("Something wrong in IterateRSSFeed");
	    }   
	    finally{}
		return headlines;
	}
}



