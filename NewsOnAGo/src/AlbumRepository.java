
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
		private static Vector<Album> vect;
		
		// constructor: automatically read all the disk-stored albums from the directory (disk)
		@SuppressWarnings("unchecked")
		public AlbumRepository(String directory){
            vect = (Vector<Album>)SerializableManager.getDataFromDisk(directory);
            if (vect == null)vect = new Vector<Album>();
		}
		
		// store the album into the album repository
		public static void addAlbum(Album storedAlbum) {
			vect.add(storedAlbum);
		}
		
		// add an album with no articles 
		public static void addEmptyAlbum(String albumName){
			Vector<Article> articles = new Vector<Article>();
			Album emptyAlbum = new Album(albumName, articles);
			vect.add(emptyAlbum);
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
			for(albumIndex=0; albumIndex<vect.size(); albumIndex++){
				if(albumName.equals(vect.get(albumIndex).getAlbumName())){
					for(articleIndex=0; articleIndex<vect.get(albumIndex).getNumOfArticles(); articleIndex++){
						if(storedArticle.getTitle().equals(vect.get(albumIndex).getArticleTitle(articleIndex))){
							if(duplicate == true){
								vect.get(albumIndex).getAllArticles().add(storedArticle);
							    return 3;
							} 
							else return 4;
						}
					}
					// no duplicate article is found in this album
					if(articleIndex == vect.get(albumIndex).getNumOfArticles()){
						vect.get(albumIndex).getAllArticles().add(storedArticle);
						return 2;
					}	    
				}
			}	
			if(albumIndex == vect.size()) return 1;
			else return 0; // in this case albumIndex must be 0
		}
		
		// store the comment with album name and article title provided
		public static boolean storeComment(String albumName, String articleName, String comment){
			for(int albumIndex=0; albumIndex<vect.size(); albumIndex++){
				if(albumName.equals(vect.get(albumIndex).getAlbumName())){
					for(int articleIndex=0; articleIndex<vect.get(albumIndex).getNumOfArticles(); articleIndex++){
						if(articleName.equals(vect.get(albumIndex).getArticleTitle(articleIndex))){
							vect.get(albumIndex).getArticle(articleIndex).setComment(comment);
							return true;							
						}							
					}
				}
			}
			return false;
		}
		
		// get a specific album from the album repository by index
		public static Album getAlbum(int albumIndex){
			return vect.get(albumIndex);
		}
		
		// get a specific album name by index
		public static String getAlbumName(int albumIndex){
			return vect.get(albumIndex).getAlbumName();
		}
		
		// get the article html by its title (use the first match)
		public static String getHtmlByTitle(String title){
			for(int albumIndex=0; albumIndex<vect.size(); albumIndex++){
				for(int articleIndex=0; articleIndex<vect.get(albumIndex).getNumOfArticles(); articleIndex++){
					if(title.equals(vect.get(albumIndex).getArticleTitle(articleIndex))) return vect.get(albumIndex).getArticleHtml(articleIndex);
				}
			}
			return "";
		}
				
		// get the article comments by its title and the name of album where it is stored 
		public static String getCommentByTitle(String title, String albumName){
			for(int albumIndex=0; albumIndex<vect.size(); albumIndex++){
				if(albumName.equals(vect.get(albumIndex).getAlbumName())){
					for(int articleIndex=0; articleIndex<vect.get(albumIndex).getNumOfArticles(); articleIndex++){
						if(title.equals(vect.get(albumIndex).getArticleTitle(articleIndex))) 
							return vect.get(albumIndex).getArticleComment(articleIndex);
					}
				}
			}
			return "";
		}
		
		// get total list of album names in album repository 
		public static Vector<String> getAlbumNames(){
			Vector<String> albumNames = new Vector<String>();
			for(int index=0; index<vect.size(); index++){
				albumNames.add(vect.get(index).getAlbumName());
			}			
			return albumNames;
		}
		
		// get the number of albums stored in the album repository
		public static int getNumOfAlbums(){
			return vect.size();
		}
		
		// remove the album and its stored articles
		public static void removeAlbum(String albumName){
			for(int index=0; index<vect.size(); index++){
				if(albumName.equals(vect.get(index).getAlbumName())){
					vect.remove(index);
					return;
				}
			}			
		}
							
		// check if a specific album name already exists in the album repository
		public static boolean DuplicateAlbumName(String albumName){
			for(int index=0; index<vect.size(); index++){
				if(albumName.equals(vect.get(index).getAlbumName())) return true;
			}	
			return false;
		}
	
		//save all the albums into the directory(disk)
		public static void writeAlbumsIntoDisk(String directory){
			SerializableManager.saveDataToDisk(vect , directory);
		}
		
	}
