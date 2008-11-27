
import java.io.Serializable;

/**
 * The NewsSource class represents a news source.
 * It is used to get a list of headlines from the Internet. 
 * 
 * NewsSource object is created when a news source is added by the user.
 * 
 * NewsSource object persists in the computer and is destroyed when it is removed by the user 
 * 
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class NewsSource implements Serializable{
	
	// an identifier for serialization
	private static final long serialVersionUID = -4525273861501634332L;

	// the title of the news source
	private String title;
	
	// the URL link of the news source
	private String link;
	
	// constructor_1 for serialization
	public NewsSource(){
		
	}
	
	// constructor_2
	public NewsSource(String title, String link){
		this.title = title;
		this.link = link;
	}
	
	// get the title of the news source
	public String getTitle(){
		return title;
	}
	
	// get the URL link of the news source
	public String getLink(){
		return link;
	}
	
	// set information of the news source
	public void setSource(String title, String link){
		this.title = title;
		this.link = link;
	}
	
	// display all the information of the news source
	public void displaySource(){
		// Coding test: tracing what a news source is
//		System.out.println("News Source Title: " + title);
//		System.out.println("News Source Link: " + link);
	}
	
}