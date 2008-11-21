                                                                     
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * The ArticleView class contains all elements and functions inside the articles tab.
 * 
 * The user deals with articles received from the headlines in this tab.
 * They can browse the article by the browser in this tab.
 * 
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class ArticleView{
	
	// article table
    private static Table table;
    
    // a browser for user to browse the article
	private static Browser browser;
	
	// article table includes the following column
	private static String[] titles = {"Title"};
	
	// a label to remind users of doing something
	private static Label articleTitleLabel;
	
	// a combo box to display all the albums 
	private static Combo albumCombo;
	
	// error message for adding an article into an album
	private static Label errorMessage;
        
	// create a tab for Article View
	public static void createTab(TabFolder tabFolder, TabItem tab){
			
		// create a tab composite
		Composite tabComposite = new Composite(tabFolder, SWT.NONE );
		
		// set control of composite to the tab
		tab.setControl(tabComposite);
			
		// create a group to group some swt widgets
		final Group group = new Group(tabComposite, SWT.SHADOW_ETCHED_IN);
		group.setBounds(0, 0, 720, 68);
		    
		// create a label
		final Label label1 = new Label(group, SWT.NONE);
		label1.setText("Article Name: ");
		label1.setBounds(15, 15, 100, 20);
		label1.pack();
            
		// create a label to show the article title that user selects in the article table
		articleTitleLabel = new Label(group, SWT.NONE);
		articleTitleLabel.setText("(Select from the table below)");
		articleTitleLabel.setBounds(100, 15, 500, 20);
		// set the color of article title to be blue
		Color blue = new Color(null, 0 , 0 , 255);
        articleTitleLabel.setForeground(blue); 
	    
		// create a label
		final Label label2 = new Label(group, SWT.NONE);
		label2.setText("Album Name: ");
		label2.setBounds(15, 40, 50, 20);
		label2.pack();
		    
		// create a combo box
		albumCombo = new Combo (group, SWT.READ_ONLY);
		albumCombo.setBounds(100, 35, 200, 20);
		
		// set the album list in the combo	
		String[] albumNames = new String[AlbumRepository.getNumOfAlbums()];
		for(int index=0; index<albumNames.length; index++){
		    albumNames[index] = AlbumRepository.getAlbumName(index);
		}
		albumCombo.setItems(albumNames);
		    
		// create a "add to album" button to add an article into an album
		final Button addToAlbumButton = new Button(group, SWT.PUSH);
		addToAlbumButton.setText("add to album");
		addToAlbumButton.setBounds(305, 34, 35, 20);
		addToAlbumButton.pack();
		    
		// create a label to show error message
		errorMessage = new Label(group, SWT.NONE);
		errorMessage.setText("Store articles in your album here.");
		errorMessage.setBounds(400, 40, 100, 20);
		errorMessage.pack();
		
		// create a table to display all articles
		table = new Table(tabComposite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	    table.setHeaderVisible(true);
		table.setBounds(0, 68, 200, 540);

		// create columns of the table header ("Title") for user to click on
		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			TableColumn column = new TableColumn(table, SWT.NULL);
		    column.setText(titles[loopIndex]);
		              
		    // add a listener to each column in case user wants to sort the column
		    column.addListener(SWT.Selection, SortListenerFactory.getListener(loopIndex));
		}				
		    
		// create a browser
		try {
			browser = new Browser(tabComposite, SWT.MOZILLA | SWT.BORDER);
			browser.setBounds(205,68,760,540);
		} 
		catch (SWTError e) {
			// if any exceptions occur, show the error message in the console
			System.out.println("Could not instantiate Browser: " + e.getMessage());
		}
			
		// if "add to album" button is pressed
		Listener addToAlbumListener = new Listener(){
			public void handleEvent(Event event){
				try{
					// store the article into the album
					storeArticle();
				}
				catch(Exception e){
					// if errors occur, show the error message in the console
				    e.printStackTrace();
				}   
				finally{
						
				}
			}
		};
			       	 
		addToAlbumButton.addListener(SWT.Selection, addToAlbumListener);
		        
		// if one item of table is clicked by user
		table.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				// display the html page of the article
				String title;
				title = ((TableItem) event.item).getText(0);
				String html = ArticleRepository.getHtmlByTitle(title);
				browser.setText(html);
					
				// display the article title in the label
				articleTitleLabel.setText(title);
				Color blue = new Color(null, 0 , 0 , 255);
			    articleTitleLabel.setForeground(blue); 
			}
		});
		
		// pack each table columns so each displays well
		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		    table.getColumn(loopIndex).pack();
		}	
	}
		
	// update the table in Article View
	// return the number of articles added
	public static int updateTable(Vector<String[]> articles){
		
		// whether an article already exists in the article table 
		boolean duplicateArticle = false;
		
		// the number of articles added in the table
		int updatedNum = 0; 
		
		String articleHTML = "";
			
		// check for duplicates
		for(int index=0; index<articles.size(); index++){
			duplicateArticle = checkArticles(articles.elementAt(index)[0]);
		        
			// if an article doesn't exist yet in the current table
		    // add it in table and display it for the user
			if (duplicateArticle == false){					
									
				// add article in the table and display it
				TableItem article = new TableItem(table, SWT.NULL);
				article.setText(0, articles.elementAt(index)[0]);
				
				// one table update occurs, so increase updatedNum by one
				updatedNum++;
				
				// get the related articleHTML
				articleHTML = UrlToHtml.getHtml(articles.elementAt(index)[1]);
					
				// store the article into article repository 
                Article articleInfo = new Article(articles.elementAt(index)[0], articleHTML, "");
				ArticleRepository.addArticle(articleInfo);	
    
				// pack each table column so each displays well
				for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
					table.getColumn(loopIndex).pack();
				}
			}
		}
		return updatedNum;
	}
		
	// check whether a specific article already exist in the table
	public static boolean checkArticles(String article){
		
		for (int articleIndex = 0; articleIndex<table.getItemCount(); articleIndex++){
				
			// if an article already exists, we don't add it into the table
			if (article.equals(table.getItem(articleIndex).getText(0))){
				return true;
			}
		}
		return false;	
	}
		
	// update the album list in the combo box
	// if action = 1, add the album name in the combo
	// if action = 2, delete the ablum name in the combo
	public static void updateCombo(String albumName, int action){
		
		if(action == 1) albumCombo.add(albumName);
		else{
			if(action == 2) albumCombo.remove(albumName);
			else System.out.println("ArticleView: Unknown updating action in combo box.");	
		}		
	}
		
	// store a specific article into an album
	public static void storeArticle(){
		
		String articleTitle = articleTitleLabel.getText();
		
		// if the user does not select an article in the table
		if(articleTitle.equals("(Select from the table below)")){
			
			// show error message
			errorMessage.setText("Select an article from table.");
			errorMessage.pack();							
			Color red = new Color(null, 255 , 0 , 0);
			errorMessage.setForeground(red);
		}
		else{
			// if album name is not selected (identified)
			if(albumCombo.getText().equals("")){
				
				// show the error message
				errorMessage.setText("Select an album from combo.");
				errorMessage.pack();							
				Color red = new Color(null, 255 , 0 , 0);
				errorMessage.setForeground(red);
			}
			else{
				// if both article name and album name are assigned by the user 
				// store the article into album (which is stored in Album Repository)
				int storeInfo;
				String html = ArticleRepository.getHtmlByTitle(articleTitle);
				Article storedArticle = new Article(articleTitle, html, "");
				storeInfo = AlbumRepository.storeArticle(albumCombo.getText(), storedArticle, false);
				
				// if the article is successfully stored in the album 
				if(storeInfo == 2){
					
					// show the article in the album tree in AlbumView
					AlbumView.updateAlbumTree(albumCombo.getText(), articleTitle);
					
					// show the message
					errorMessage.setText("The article is stored in the album " +albumCombo.getText()+".");
					errorMessage.pack();							
					Color black = new Color(null, 0 , 0 , 0);
					errorMessage.setForeground(black);
						
				}
					else{
					// if the article already exists in this album and therefore is not stored in the album
					if(storeInfo == 4){
						// show error message
						errorMessage.setText("The article had been stored in the album " +albumCombo.getText()+".");
						errorMessage.pack();							
						Color red = new Color(null, 255 , 0 , 0);
						errorMessage.setForeground(red);
					}
					else{
						// if something else happens
						// show error message
						errorMessage.setText("The article is not stored due to some reasons.");
						errorMessage.pack();							
						Color red = new Color(null, 255 , 0 , 0);
						errorMessage.setForeground(red);
					}
				}
			}				
		}			
	}	
}