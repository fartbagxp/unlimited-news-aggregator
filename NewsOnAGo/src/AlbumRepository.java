
import java.util.Vector;

/**
 * The AlbumRepository class represents a repository storing a bunch of albums. 
 * 
 * AlbumRepository object is created when a repository for storing albums is created by the user.
 * 
 * AlbumRepository object persists in the computer and is destroyed when it is deleted by the user. 
 * 
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class AlbumRepository{
	    
		// vector storing a list of albums
		private static Vector<Album> albumList;
		
		// constructor: automatically read all the disk-stored albums from the directory (disk)
		@SuppressWarnings("unchecked")
		public AlbumRepository(String directory){
            albumList = (Vector<Album>)SerializableManager.getDataFromDisk(directory);
            if (albumList == null)albumList = new Vector<Album>();
		}
		
		// store the album into the album repository
		public static void addAlbum(Album storedAlbum) {
			albumList.add(storedAlbum);
		}
		
		// add an album with no articles 
		public static void addEmptyAlbum(String albumName){
			Vector<Article> articles = new Vector<Article>();
			Album emptyAlbum = new Album(albumName, articles);
			albumList.add(emptyAlbum);
		}
		
		// add a specific article into a specific album
		// duplicate: true(false) for (not)allowing duplicate article be stored in the same album 
		// return 0: album repository is empty
		// return 1: cannot find the album in the album repository 
		// return 2: the article (not duplicate one) is successfully stored
		// return 3: the duplicate article is successfully stored
		// return 4: the stored article already exists and it is not stored because duplicate is not allowed

		public static int storeArticle(String albumName, Article storedArticle, boolean duplicate){
			int albumIndex;
			int articleIndex;
			for(albumIndex=0; albumIndex<albumList.size(); albumIndex++){
				if(albumName.equals(albumList.get(albumIndex).getAlbumName())){
					for(articleIndex=0; articleIndex<albumList.get(albumIndex).getNumOfArticles(); articleIndex++){
						if(storedArticle.getTitle().equals(albumList.get(albumIndex).getArticleTitle(articleIndex))){
							if(duplicate == true){
								albumList.get(albumIndex).getAllArticles().add(storedArticle);
							    return 3;
							} 
							else return 4;
						}
					}
					// no duplicate article is found in this album
					if(articleIndex == albumList.get(albumIndex).getNumOfArticles()){
						albumList.get(albumIndex).getAllArticles().add(storedArticle);
						return 2;
					}	    
				}
			}	
			if(albumIndex == albumList.size()) return 1;
			else return 0; // in this case albumIndex must be 0
		}
		
		// store the comment with album name and article title provided
		public static boolean storeComment(String albumName, String articleName, String comment){
			for(int albumIndex=0; albumIndex<albumList.size(); albumIndex++){
				if(albumName.equals(albumList.get(albumIndex).getAlbumName())){
					for(int articleIndex=0; articleIndex<albumList.get(albumIndex).getNumOfArticles(); articleIndex++){
						if(articleName.equals(albumList.get(albumIndex).getArticleTitle(articleIndex))){
							albumList.get(albumIndex).getArticle(articleIndex).setComment(comment);
							return true;							
						}							
					}
				}
			}
			return false;
		}
		
		// get a specific album from the album repository by index
		public static Album getAlbum(int albumIndex){
			return albumList.get(albumIndex);
		}
		
		// get a specific album name by index
		public static String getAlbumName(int albumIndex){
			return albumList.get(albumIndex).getAlbumName();
		}
		
		// get the article html by its title (use the first match)
		public static String getHtmlByTitle(String title){
			for(int albumIndex=0; albumIndex<albumList.size(); albumIndex++){
				for(int articleIndex=0; articleIndex<albumList.get(albumIndex).getNumOfArticles(); articleIndex++){
					if(title.equals(albumList.get(albumIndex).getArticleTitle(articleIndex))) return albumList.get(albumIndex).getArticleHtml(articleIndex);
				}
			}
			return "";
		}
				
		// get the article comments by its title and the name of album where it is stored 
		public static String getCommentByTitle(String title, String albumName){
			for(int albumIndex=0; albumIndex<albumList.size(); albumIndex++){
				if(albumName.equals(albumList.get(albumIndex).getAlbumName())){
					for(int articleIndex=0; articleIndex<albumList.get(albumIndex).getNumOfArticles(); articleIndex++){
						if(title.equals(albumList.get(albumIndex).getArticleTitle(articleIndex))) 
							return albumList.get(albumIndex).getArticleComment(articleIndex);
					}
				}
			}
			return "";
		}
		
		// get total list of album names in album repository 
		public static Vector<String> getAlbumNames(){
			Vector<String> albumNames = new Vector<String>();
			for(int index=0; index<albumList.size(); index++){
				albumNames.add(albumList.get(index).getAlbumName());
			}			
			return albumNames;
		}
		
		// get the number of albums stored in the album repository
		public static int getNumOfAlbums(){
			return albumList.size();
		}
		
		// remove the article from a specific album
		public static boolean removeArticle(String albumName, String articleName){
			for(int albumIndex=0; albumIndex<albumList.size(); albumIndex++){
				if(albumName.equals(albumList.get(albumIndex).getAlbumName())){
					for(int articleIndex=0; articleIndex<albumList.get(albumIndex).getNumOfArticles(); articleIndex++){
						if(articleName.equals(albumList.get(albumIndex).getArticleTitle(articleIndex))){
							albumList.get(albumIndex).removeArticle(articleIndex);
							return true;
						}							
					}
				}
			}
			return false;
		}
		
		// remove the album and its stored articles
		public static void removeAlbum(String albumName){
			for(int index=0; index<albumList.size(); index++){
				if(albumName.equals(albumList.get(index).getAlbumName())){
					albumList.remove(index);
					return;
				}
			}			
		}
							
		// check if a specific album name already exists in the album repository
		public static boolean DuplicateAlbumName(String albumName){
			for(int index=0; index<albumList.size(); index++){
				if(albumName.equals(albumList.get(index).getAlbumName())) return true;
			}	
			return false;
		}
	
		//save all the albums into the directory(disk)
		public static void writeAlbumsIntoDisk(String directory){
			SerializableManager.saveDataToDisk(albumList , directory);
		}
		
	}