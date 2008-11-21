
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Vector;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImageImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * The RSSFeed class establishes a connection to an RSS feed and returns all the headlines
 * from that RSS feed to the table in the headline tab
 *  
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class RSSFeed {
	
	// hold onto all the headlines needed
	private static Vector<String[]> headlines = new Vector<String[]>();
	
	// hold onto all the RSS feed streams that user has checked off
	public static Vector<String[]> iterateRSSFeed(String src, String rssFeedUrl){
		
		try{
			
	    	// establish a connection to the rss feed
	        URLConnection feedUrl = new URL(rssFeedUrl).openConnection();

	        // create Feed Object
	        SyndFeedInput input = new SyndFeedInput();
	        SyndFeed feed = input.build(new XmlReader(feedUrl));

	        System.out.println("Examining rss feed:"+rssFeedUrl+"\n");
	        System.out.println("Feed type="+feed.getFeedType());

	        // iterate through object to get details
	        List list = feed.getEntries();

	        System.out.println("Feed image="+feed.getImage());
	        
	        if (feed.getImage()!=null){
	        	SyndImageImpl image = (SyndImageImpl)feed.getImage();
	            String imageInfo = "Image url:"+image.getUrl()+"\n";
	            System.out.println(imageInfo);
	        }   
	            
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
		        System.out.println("DescriptionString = " + descriptionString + "\n\n\n");
		        
		        // model.addRow(data);
		        if (entry.getContents().size()==1){
		            SyndContentImpl imp = (SyndContentImpl)entry.getContents().get(0);
		            display += "\ncontent value:"+imp.getValue();
		        }
		        display += "\n";
		        System.out.println(display);
		                
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
	        
	        // packing 5 columns together
	        //for (int loopIndex = 0; loopIndex < 5; loopIndex++){
	        //	headlineTable.getColumn(loopIndex).pack();
	        //}
		}    
	    // if all else fails, blah out an error
	    catch(Exception e){
	        e.printStackTrace();
	        System.out.println("Something wrong in IterateRSSFeed");
	    }   
	    finally{}
		return headlines;
	}
}



