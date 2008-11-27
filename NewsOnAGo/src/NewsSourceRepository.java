
import java.util.Vector;

/**
 * The NewsSourceRepository class represents a repository storing a bunch of news sources. 
 * 
 * NewsSourceRepository object is created when a repository for storing news sources is created by the user.
 * 
 * NewsSourceRepository object persists in the computer and is destroyed when it is deleted by the user. 
 * 
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class NewsSourceRepository{
	    
		// vector storing a list of news sources
		private static Vector<NewsSource> newsSourceList;
		
		// constructor: automatically read all the disk-stored news sources from the directory (disk)
		@SuppressWarnings("unchecked")
		public NewsSourceRepository(String directory){
            newsSourceList = (Vector<NewsSource>)SerializableManager.getDataFromDisk(directory);
            
            // if file is not found in the disk, create a new news source repository  
            if (newsSourceList == null){
            	newsSourceList = new Vector<NewsSource>();
            	
            	// set default news sources for the user 
            	NewsSource defaultNewsSource1 = new NewsSource("Cnn Technology", "http://rss.cnn.com/rss/cnn_tech.rss");
            	NewsSource defaultNewsSource2 = new NewsSource("Cnn Space & Science", "http://rss.cnn.com/rss/cnn_space.rss");
            	NewsSource defaultNewsSource3 = new NewsSource("Yahoo Top Stories", "http://rss.news.yahoo.com/rss/topstories");
            	NewsSource defaultNewsSource4 = new NewsSource("Google News", "http://news.google.com/?output=rss");
            	NewsSource defaultNewsSource5 = new NewsSource("Reuters Top Stories", "http://feeds.reuters.com/reuters/topNews");
            	NewsSource defaultNewsSource6 = new NewsSource("Reuters Technology", "http://feeds.reuters.com/reuters/technologyNews");  
            	newsSourceList.add(defaultNewsSource1);
            	newsSourceList.add(defaultNewsSource2);
            	newsSourceList.add(defaultNewsSource3);
            	newsSourceList.add(defaultNewsSource4);
            	newsSourceList.add(defaultNewsSource5);
            	newsSourceList.add(defaultNewsSource6);
            }
		}
		// store the news source into the news source repository
		public static void addSource(NewsSource storedNewsSource) {
			newsSourceList.add(storedNewsSource);
		}
		
		// store the news source into the news source repository
		public static void addSource(String title, String link){
			NewsSource storedNewsSource = new NewsSource(title, link);
			newsSourceList.add(storedNewsSource);
		}
	    
		// remove all news source stored in the news source repository (clear command)
		public static void clearSources(){
			newsSourceList.removeAllElements();
		}
		
		// retrieve a specific news source from the news source repository by index
		public static NewsSource getSourceByIndex(int newsSourceIndex){
			return newsSourceList.get(newsSourceIndex);
		}
		
		// get the number of news sources stored in the news source repository
		public static int getNumOfSources(){
			return newsSourceList.size();
		}
		
		// erase the news source at location newsSourceIndex in news source repository
		public static void removeSourceByIndex(int newsSourceIndex){
			newsSourceList.removeElementAt(newsSourceIndex);
		}
				
		// erase the news source by the link attribute
		public static void removeSourceByLink(String link){
			// iterate through NewsSourceRepository to see if it already exists
			for(int SourceIndex = 0; SourceIndex<newsSourceList.size() ;SourceIndex++){
                
				if (link.equals(newsSourceList.elementAt(SourceIndex).getLink())){
					// if found
					newsSourceList.removeElementAt(SourceIndex);
					return;
				}
			}
			// if not found, remove nothing
		}
		
		// display all the information of all the news sources stored in the news source repository
		public static void displayNewsSourceRepository(){
			for(int index = 0; index < newsSourceList.size(); index++){
				NewsSource newsSource = newsSourceList.get(index);
				newsSource.displaySource();
			}
		}
		
		// check if the news source is already added into the news source repository
		public static boolean checkNewsSourceRepo(String storedLink){
			// iterate through NewsSourceRepository to see if it already exists
			for(int SourceIndex = 0; SourceIndex < newsSourceList.size() ;SourceIndex++){
                
				if (storedLink.equals(newsSourceList.elementAt(SourceIndex).getLink())){
					// if it is found
					return true;
				}
			}
			// if it is not found
			return false;
		}

		
		// save all the news sources into the directory(disk)
		public static void writeNewsSourcesIntoDisk(String directory){
			SerializableManager.saveDataToDisk(newsSourceList , directory);
		}
		
	}