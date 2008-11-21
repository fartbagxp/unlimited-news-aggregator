
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * The AlbumView class contains all elements and functions inside the albums tab.
 * 
 * The user deals with albums and writes comments for articles in this tab.
 * 
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class AlbumView{
	
	// a bowser for browsing the articles
	private static Browser browser;
	
	// error message for creating and deleting the albums
	private static Label errorMessage;
	
	// an album name typed in by the user
	private static Text albumNameText;
	
	// a tree to show a structure of albums and their stored articles
	private static Tree albumTree;
	
	// comments written by user for each article
	private static Text commentText;
	
	// selected tree items
	private static TreeItem[] selection;
	
	// selected article name in the tree
	private static String selectedArticle;
	
	// selected album name in the tree
	private static String selectedAlbum;
        
	// create a tab for Album View
	public static void createTab(TabFolder tabFolder, TabItem tab){
			
		// create a tab composite
		Composite tabComposite = new Composite(tabFolder, SWT.NONE );
		
		// set control of composite to the tab
		tab.setControl(tabComposite);
			
		// create a group to group some swt widgets
		final Group group = new Group(tabComposite, SWT.SHADOW_ETCHED_IN);
		group.setBounds(0, 0, 600, 50);
		    
		// create a label
		final Label label = new Label(group, SWT.NONE);
		label.setText("Album Name: ");
		label.setBounds(15, 20, 100, 20);
		label.pack();
            
		// creates a textbox for user to type in album name to add or remove an album 
		albumNameText = new Text(group, SWT.BORDER);
		albumNameText.setText("");
		albumNameText.setBounds(100, 20, 200, 20);
		albumNameText.setTextLimit(50);
		    
		// create a "Create" button to generate a new album for user to store the articles in
		Button createAlbumButton = new Button(group, SWT.PUSH);
		createAlbumButton.setText("Create");
		createAlbumButton.setBounds(305, 15, 40, 20);
		createAlbumButton.pack();
		    
		// create a "Delete" button to remove a specific album
		Button deleteAlbumButton = new Button(group, SWT.PUSH);
		deleteAlbumButton.setText("Delete");
		deleteAlbumButton.setBounds(355, 15, 40, 20);
		deleteAlbumButton.pack();
		    
		// create a label to show error message
		errorMessage = new Label(group, SWT.NONE);
		errorMessage.setText("");
		errorMessage.setBounds(415, 20, 100, 20);

		// create a tree to display all albums and all the headlines of the stored articles
		albumTree = new Tree(tabComposite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		albumTree.setBounds(0, 50, 200, 700);
		albumTree.setHeaderVisible(false);
		    
		// show all the album names and their stored article names in the tree
		for (int level0Index = 0; level0Index < AlbumRepository.getNumOfAlbums(); level0Index++) {
			TreeItem treeItem0 = new TreeItem(albumTree, SWT.NONE);
		    treeItem0.setText(AlbumRepository.getAlbum(level0Index).getAlbumName());
		    for (int level1Index = 0; level1Index < AlbumRepository.getAlbum(level0Index).getNumOfArticles(); level1Index++){
		    	TreeItem treeItem1 = new TreeItem(treeItem0, SWT.NONE);
			    treeItem1.setText(AlbumRepository.getAlbum(level0Index).getArticleTitle(level1Index));   
		    }
		}
		    
		// create a browser
		try {
			browser = new Browser(tabComposite, SWT.MOZILLA | SWT.BORDER);
			browser.setBounds(205,50,760,500);
		} 
		catch(SWTError e) {
			// if any exceptions occur, show the error message in the console
			System.out.println("Could not instantiate Browser: " + e.getMessage());
		}
			
		// create a textbox for comments
		commentText = new Text(tabComposite ,SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		commentText.setText("comment article here...");
		commentText.setBounds(210, 553, 685, 58);
		    
		// create a button to clear the comments in the comment textbox
		final Button clearCommentButton = new Button(tabComposite, SWT.PUSH);
		clearCommentButton.setText("Clear");
		clearCommentButton.setBounds(900, 555, 60, 25);
		clearCommentButton.setEnabled(false);
		    
		// create a button to store the comments for the article
		final Button storeCommentButton = new Button(tabComposite, SWT.PUSH);
		storeCommentButton.setText("Save");
		storeCommentButton.setBounds(900, 580, 60, 25);
		storeCommentButton.setEnabled(false);
			
		// if one item of tree is clicked by user
		albumTree.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				
				// display the html page of the selected article
  				selection = albumTree.getSelection();                 				
				selectedArticle = selection[0].getText();
					
				// if the user clicks on the article item in the tree
				if(!AlbumRepository.DuplicateAlbumName(selectedArticle)){
					String html = AlbumRepository.getHtmlByTitle(selectedArticle);
					
					// show the article in the browser
					browser.setText(html);
					
					// get the comment for that article from the album repository					
					selectedAlbum = selection[0].getParentItem().getText();
					String comment = AlbumRepository.getCommentByTitle(selectedArticle, selectedAlbum);
					
					// show the comment in the comment textbox
					commentText.setText(comment);
					
					// enable two buttons for dealing with comment text
					clearCommentButton.setEnabled(true);
					storeCommentButton.setEnabled(true);
				}
				else{
					// if the user clicks on the album item in the tree
					// show the album name in the textbox
					albumNameText.setText(selectedArticle);	
				}
			}
		});	
		        			
		// if "Create" button is pressed
		Listener createAlbumListener = new Listener(){
			public void handleEvent(Event event){
				try{
					// create that album
					createAlbum();
				}
				catch(Exception e){
					// if any exceptions occur, show error message in the console
				    e.printStackTrace();
				}   
				finally{
						
				}
			}
		};
			       	 
		createAlbumButton.addListener(SWT.Selection, createAlbumListener);
			
		// if "Delete" button is pressed
		Listener deleteAlbumListener = new Listener(){
			public void handleEvent(Event event){
				try{
					// delete that album
					deleteAlbum();
				}
				catch(Exception e){
					// if any exceptions occur, show error message in the console
				    e.printStackTrace();
				}   
				finally{
						
				}
			}
		};
			       	 
		deleteAlbumButton.addListener(SWT.Selection, deleteAlbumListener);
			
		// if "Save" button is pressed
		Listener saveCommentListener = new Listener(){
			public void handleEvent(Event event){
				try{
					// store the comments for the article
					saveComment();
				}
				catch(Exception e){
					// if any exceptions occur, show error message in the console
				    e.printStackTrace();
				}   
				finally{
						
				}
			}
		};
			
		storeCommentButton.addListener(SWT.Selection, saveCommentListener);
			
		// if "Clear" button is pressed
		Listener clearCommentListener = new Listener(){
			public void handleEvent(Event event){
				try{
					// clear the comment text in the comment textbox
					commentText.setText("");
				}
				catch(Exception e){
					// if any exceptions occur, show error message in the console
				    e.printStackTrace();
				}   
				finally{
						
				}
			}
		};
			
		clearCommentButton.addListener(SWT.Selection, clearCommentListener);
			
	}
		
	// create a new album	
	public static void createAlbum(){
		
		// get album name that user wants to create
		String albumName = albumNameText.getText();
			
		// if textbox is empty
		if(albumName.equals("")){
			
			// show error message
			errorMessage.setText("Album name is required.");
			errorMessage.pack();							
			Color red = new Color(null, 255 , 0 , 0);
			errorMessage.setForeground(red);
		}
		else{
			// if textbox is not empty
			// if the album name does not exist
			if(!AlbumRepository.DuplicateAlbumName(albumName)){
				
				// create an empty album with the name that user wants and store it into album repository
				AlbumRepository.addEmptyAlbum(albumName);
				    
				// add the album name into combo box in ArticleView
				ArticleView.updateCombo(albumName, 1);
					
				// show the message
				errorMessage.setText("Album is created.");
				errorMessage.pack();							
				Color black = new Color(null, 0 , 0 , 0);
				errorMessage.setForeground(black);
					
				// show the album name in the album tree
				TreeItem treeItem0 = new TreeItem(albumTree, SWT.NONE);
				treeItem0.setText(albumName);
			}
			else{ 
				// if the album name already exists, show error message 
				errorMessage.setText("Album name already exists.");
				errorMessage.pack();
				Color red = new Color(null, 255 , 0 , 0);
				errorMessage.setForeground(red);
			}
		}
	}
	
	// delete a specific album
	public static void deleteAlbum(){
		
		// get album name that user wants to delete
		String albumName = albumNameText.getText();
			
		// if textbox is empty
		if(albumName.equals("")){
			
			// show error message
			errorMessage.setText("Album name is required.");
			errorMessage.pack();							
			Color red = new Color(null, 255 , 0 , 0);
			errorMessage.setForeground(red);
		}
		else{
			// if textbox is not empty
			// if the album name already exists
			if(AlbumRepository.DuplicateAlbumName(albumName)){
				
				// delete the album from album repository
				AlbumRepository.removeAlbum(albumName);
					
				// delete the album from combo box in ArticleView
				ArticleView.updateCombo(albumName, 2);
					
				// show the message
				errorMessage.setText("Album is deleted.");
				errorMessage.pack();							
				Color black = new Color(null, 0 , 0 , 0);
				errorMessage.setForeground(black);
				
				// delete the album name in the tree and its stored articles
				for(int level0Index=0; level0Index<albumTree.getItemCount(); level0Index++){
					if(albumName.equals(albumTree.getItem(level0Index).getText())){
						albumTree.getItem(level0Index).dispose();
					}			
				}
			}
			else{ 
				// if the album name does not exist, show error message
				errorMessage.setText("Album name does not exist.");
				errorMessage.pack();
				Color red = new Color(null, 255 , 0 , 0);
				errorMessage.setForeground(red);
			}
		}
	}
		
	// update the album tree in AlbumView
	public static void updateAlbumTree(String albumName, String articleTitle){
		for (int level0Index = 0; level0Index < albumTree.getItemCount(); level0Index++) {
		    if(albumName.equals(albumTree.getItem(level0Index).getText())){
		    	TreeItem newTreeItem1 = new TreeItem(albumTree.getItem(level0Index), SWT.NONE);
			    newTreeItem1.setText(articleTitle);
			    return;
		    }
		}
	}
		
	// store the comment for the article 
	public static void saveComment(){
		AlbumRepository.storeComment(selectedAlbum, selectedArticle, commentText.getText());
	}		
		
}
