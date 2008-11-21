
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
		private static Vector<NewsSource> vect;
		
		// constructor: automatically read all the disk-stored news sources from the directory (disk)
		@SuppressWarnings("unchecked")
		public NewsSourceRepository(String directory){
            vect = (Vector<NewsSource>)SerializableManager.getDataFromDisk(directory);
            
            // if file is not found in the disk, create a new news source repository  
            if (vect == null)vect = new Vector<NewsSource>();
		}
		
		// store the news source into the news source repository
		public static void addSource(NewsSource storedNewsSource) {
			vect.add(storedNewsSource);
		}
	    
		// remove all news source stored in the news source repository (clear command)
		public static void clearSources(){
			vect.removeAllElements();
		}
		
		// retrieve a specific news source from the news source repository by index
		public static NewsSource getSourceByIndex(int newsSourceIndex){
			return vect.get(newsSourceIndex);
		}
		
		// get the number of news sources stored in the news source repository
		public static int getNumOfSources(){
			return vect.size();
		}
		
		// erase the news source at location newsSourceIndex in news source repository
		public static void removeSourceByIndex(int newsSourceIndex){
			vect.removeElementAt(newsSourceIndex);
		}
				
		// erase the news source by the link attribute
		public static void removeSourceByLink(String link){
			// iterate through NewsSourceRepository to see if it already exists
			for(int SourceIndex = 0; SourceIndex<vect.size() ;SourceIndex++){
                
				if (link.equals(vect.elementAt(SourceIndex).getLink())){
					// if found
					vect.removeElementAt(SourceIndex);
					return;
				}
			}
			// if not found, remove nothing
		}
		
		// display all the information of all the news sources stored in the news source repository
		public static void displayNewsSourceRepository(){
			for(int index = 0; index < vect.size(); index++){
				NewsSource newsSource = vect.get(index);
				newsSource.displaySource();
			}
		}
		
		// check if the news source is already added into the news source repository
		public static boolean checkNewsSourceRepo(String storedLink){
			// iterate through NewsSourceRepository to see if it already exists
			for(int SourceIndex = 0; SourceIndex<vect.size() ;SourceIndex++){
                
				if (storedLink.equals(vect.elementAt(SourceIndex).getLink())){
					// if it is found
					return true;
				}
			}
			// if it is not found
			return false;
		}

		
		// save all the news sources into the directory(disk)
		public static void writeNewsSourcesIntoDisk(String directory){
			SerializableManager.saveDataToDisk(vect , directory);
		}
		
	}