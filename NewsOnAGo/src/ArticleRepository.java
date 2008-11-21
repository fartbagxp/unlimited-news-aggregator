
import java.util.Vector;

/**
 * The ArticleRepository class represents a repository storing a bunch of articles. 
 * 
 * ArticleRepository object is created when a repository for storing articles is created by the user.
 * 
 * ArticleRepository object persists in the computer and is destroyed when it is deleted by the user. 
 * 
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class ArticleRepository{
	    
		// vector storing a list of articles
		private static Vector<Article> vect;
		
		// constructor: automatically read all the disk-stored articles from the directory (disk)
		@SuppressWarnings("unchecked")
		public ArticleRepository(String directory){
            vect = (Vector<Article>)SerializableManager.getDataFromDisk(directory);
            if (vect == null)vect = new Vector<Article>();
		}
		
		// store the article into the article repository
		public static void addArticle(Article storedArticle) {
			vect.add(storedArticle);
		}		
		
		// retrieve a specific article from the article repository by index
		public static Article getArticleByIndex(int articleIndex){
			return vect.get(articleIndex);
		}
		
		// get the article html string by its title
		public static String getHtmlByTitle(String title){
			String html;
			for(int index = 0; index < vect.size(); index++){
				if(title.equals(vect.elementAt(index).getTitle())){
					html = vect.elementAt(index).getHTML();
					return html;
				}
			}
			return "";
		}
		
		// get the number of articles stored in the article repository
		public static int getNumOfArticles(){
			return vect.size();
		}
		
		// erase the article at location articleIndex in article repository
		public static void removeArticleByIndex(int articleIndex){
			vect.removeElementAt(articleIndex);
		}
		
		// remove all articles stored in the article repository (clear command)
		public static void clearArticles(){
			vect.removeAllElements();
		}
		
		// display all the information of all the articles stored in the article repository
		public static void displayArticleRepository(){
			for(int index = 0; index < vect.size(); index++){
				Article article = vect.get(index);
				article.displayArticle();
			}
		}
		
		// check if the article is already added into the article repository
		public static boolean checkArticleRepo(Article articleInfo){
			// iterate through ArticleRepository to see if it already exists
			for(int articleIndex = 0; articleIndex<vect.size() ;articleIndex++){
				// access headline text of user checked article
				String headlineText = articleInfo.getTitle(); 	
                
				if (headlineText.equals(vect.elementAt(articleIndex).getTitle())){
					// if it is found
					return true;
				}
			}
			// if it is not found
			return false;
		}
			
		// save all the articles into the directory(disk)
		public static void writeArticlesIntoDisk(String directory){
			SerializableManager.saveDataToDisk(vect , directory);
		}
		
	}