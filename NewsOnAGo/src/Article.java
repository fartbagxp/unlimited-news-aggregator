
import java.io.Serializable;

/**
 * The Article class represents an article within the NewsOnAGo application.
 * 
 * Article object is created when an article is received from the Internet.
 * 
 * Article object persists in the computer and is destroyed when it is removed by the user 
 * 
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class Article implements Serializable{
	
	// an identifier for serialization
    private static final long serialVersionUID = 9064570611315798202L;

	// the headline of the article
	private String title;
	
	// the HTML string of the article
	private String html;
	
	// comments for the article
	private String commentText;
	
	// constructor_1 for serialization
	public Article(){
		
	}
	
	// constructor_2
	public Article(String headline, String html, String commentText){
		this.title = headline;
		this.html = html;
		this.commentText = commentText;
	}
		
	// get article title
	public String getTitle(){
		return title;
	}
	
	// get html string of the article 
	public String getHTML(){
		return html;
	}
	
	// get comments for the article
	public String getComment(){
		return commentText;
	}
	
	// set comments for the article
	public void setComment(String comment){
		this.commentText = comment;
	}
	
	// set information of the article
	public void setArticle(String headline, String html, String commentText){
		this.title = headline;
		this.html = html;
		this.commentText = commentText;
	}
	
	// display all the information of the article
	public void displayArticle(){
		System.out.println("Article Headline: " + title);
		System.out.println("Article HTML String: " + html);
		System.out.println("Article Comment: " + commentText);
	}
	
}
