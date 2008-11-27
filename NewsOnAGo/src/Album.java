
import java.io.Serializable;
import java.util.Vector;

/**
 * The Album class represents an album storing a bunch of articles.
 * It is used to categorize the articles according to their types. 
 * 
 * Album object is created when an album is created by the user.
 * 
 * Album object persists in the computer and is destroyed when it is deleted by the user 
 * 
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class Album implements Serializable{
	
	// an identifier for serialization
	private static final long serialVersionUID = 3737448601251430136L;
	
	// album name
	private String name;
	
	// articles held by the created album
	private Vector<Article> articleList;
	
	// constructor_1 for serialization
	public Album(){

	}
	
	// constructor_2
	public Album(String albumName, Vector<Article> articles){
		this.name = albumName;
		this.articleList = articles;
	}
		
	// add a specific article into album
	public void addArticle(Article article){		
		this.articleList.add(article);
	}
	
	// get the album name
	public String getAlbumName(){
		return name;
	}
	
	// get all articles stored in the album
	public Vector<Article> getAllArticles(){
		return articleList;
	}
	
	// get a specific article title by index
	public String getArticleTitle(int index){
		return articleList.get(index).getTitle();
	}
	
	// get a specific article html by index
	public String getArticleHtml(int index){
		return articleList.get(index).getHTML();
	}
	
	// get a specific article comment by index
	public String getArticleComment(int index){
		return articleList.get(index).getComment();
	}
	
	// get a specific article by index
	public Article getArticle(int index){
		return articleList.get(index);
	}
		
	// get the number of articles stored in the album
	public int getNumOfArticles(){
		return articleList.size();
	}
	
	// set the album name
	public void setAlbumName(String albumName){
		this.name = albumName;
	}
	
	// remove an article from an album
	public void removeArticle(int articleIndex){
		articleList.remove(articleIndex);
	}
	
}
