
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
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
import org.eclipse.swt.widgets.Text;

/**
 * The HeadlineView class contains all elements and functions inside the headlines tab.
 * 
 * The user deals with headlines received from the news sources in this tab.
 * 
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class HeadlineView {
	
	// headline table 
	private static Table table;
	
	// error message for removing headlines and getting articles
	private static Label errorMessage1;
	
	// error message for searching headlines
	private static Label errorMessage2;
	
	// headline table includes the following columns
	private static String[] titles = { "Title", "Date", "Source", "Link", "Description" };
		
	// create a tab for Headline View
	public static void createTab(TabFolder tabFolder, TabItem tab){
		
		// create a tab composite
		Composite tabComposite = new Composite(tabFolder, SWT.NONE );
		
		// set control of composite to the tab
	    tab.setControl(tabComposite);
		
		// create a group to group some swt widgets
		final Group group1 = new Group(tabComposite, SWT.SHADOW_ETCHED_IN);
		group1.setBounds(0, 0, 470, 58);
		
		// create a label to show error message
		errorMessage1 = new Label(group1, SWT.NONE);
	    errorMessage1.setText("Please check a headline.");
	    errorMessage1.setBounds(10, 15, 100, 20);
	    errorMessage1.pack();
		
		// user has option to select all headlines using the select all button
	    // or deselect all simply by clicking on it again
	    final Button selectAllButton = new Button(group1, SWT.CHECK);
	    selectAllButton.setText("Check all/Uncheck all");
	    selectAllButton.setBounds(10,38,100,100);
	    selectAllButton.pack();
	    
	    // create a "Get Articles" button to get articles
	    final Button getArticles = new Button(group1, SWT.PUSH);
	    getArticles.setBounds(240, 13, 110, 40);
	    getArticles.setText("Get Articles");
	    
	    // create a "Remove Headline" button to remove headlines
	    final Button removeCheckedHeadlines = new Button(group1, SWT.PUSH);
	    removeCheckedHeadlines.setBounds(350, 13, 110, 40);
	    removeCheckedHeadlines.setText("Remove Headline");
	    
	    // create a group to group some swt widgets
		final Group group2 = new Group(tabComposite, SWT.SHADOW_ETCHED_IN);
		group2.setBounds(550, 0, 375, 58);
		
		// create a label to show error message
		errorMessage2 = new Label(group2, SWT.NONE);
	    errorMessage2.setText("Please enter a search string:");
	    errorMessage2.setBounds(10, 15, 100, 20);
	    errorMessage2.pack();
	    	    
	    // create a textbox for user to type in text to search headlines
	    final Text searchText = new Text(group2, SWT.BORDER);
	    searchText.setText("");
	    searchText.setBounds(10, 33, 300, 20);
	    searchText.setTextLimit(50);
	        
	    // create a "search" button for user to search headlines
	    final Button searchButton = new Button(group2, SWT.PUSH);
	    searchButton.setBounds(315, 29, 50, 25);
	    searchButton.setText("search");
	
	    // create a table for displaying all the headlines
	    table = new Table(tabComposite, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        table.setHeaderVisible(true);
        table.setBounds(3, 58, 960, 550);

        // create columns of the table header ("Title", "Date", "Source", "Link", "Description") for user to click on
	    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	    	TableColumn column = new TableColumn(table, SWT.NULL);
	        column.setText(titles[loopIndex]);
	              
	        // add a listener to each column in case user wants to sort the column
	        column.addListener(SWT.Selection, SortListenerFactory.getListener(loopIndex));
	    }
	                            
	    // listen for when the user clicks on "Check all/Uncheck all" button
	    selectAllButton.addSelectionListener(new SelectionAdapter(){ 
	    	public void widgetSelected(SelectionEvent e){
	        		
	        checkButton(table, selectAllButton);
	    	}
	    }); 
	    
	    // if "Get Articles" button is pressed	       	 
	    getArticles.addListener(SWT.Selection, new Listener(){
		       public void handleEvent(Event event){
		    	   // get the articles from the headlines checked in the table 
		    	   getHtml();
		       }
	    });
	    
	    // if "Remove Headline" button is pressed
	    removeCheckedHeadlines.addListener(SWT.Selection, new Listener(){
		       public void handleEvent(Event event){
		    	   // remove the headlines checked in the table
		    	   removeHeadline();
		       }
	    });
	    
	    // add a listener for the search button
	    // if "search" button is pressed, search for the word(s) 
	    // that the user wants to search for in headlines titles and descriptions
	    searchButton.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event){
	    		
	    		// if the user does type in a search string
	    		if (!searchText.getText().equals("")){
	    			
	    			// search by that search string
	    			SearchArticle.search(table, searchText);			
	    		}
	    		
	    		// if the user does not type in any search strings
	    		else {	    			
	    			// show error message
	    			errorMessage2.setText("Please enter a search string:");							
	    			Color red = new Color(null, 255 , 0 , 0);
	    			errorMessage2.setForeground(red); 
	    			errorMessage2.pack();
	    		}
	    	}
	    });
	    	    
	    // pack each table column so each displays well
	    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	        table.getColumn(loopIndex).pack();
	    }	    
	}
	
	// get the html of article and add article into table if not previously added
	public static void getHtml(){
		
		// whether each checkbox is checked
		boolean isChecked = false;
		
		// the number of articles added in the article table
		int updatedNum = 0;
		
		// the number of checked checkboxes (headlines)
  		int checkedNum = 0;
  		
  		// temporarily hold a list of checked headline infos 
  		// (each headline info contains the article title 
  		// and the link for getting that article)
  		Vector<String[]> headlines = new Vector<String[]>();
		
		// iterate through headlines to see which headline has been checked
	  	for (int index = 0; index < table.getItemCount(); index++) {	  		
	  		
	  		// check if each headline is checked
	  		isChecked = table.getItem(index).getChecked();
		  		
	  		// if a headline is checked
	  		if(isChecked){
	  			
	  			// add the headline info into the vector holding a list of infos of checked headlines
	  			String[] strings = {"", ""};
	  			strings[0] = table.getItem(index).getText(0);
	  			strings[1] = table.getItem(index).getText(3);
	  			headlines.add(strings);
	  			checkedNum++;
	  		}     
	  	}
	  	
	  	// if none of the headlines are checked in the table
	  	if(checkedNum == 0){
	  		
	  	    //show error message
	        errorMessage1.setText("Please check a headline.");
			errorMessage1.pack();							
			Color red = new Color(null, 255 , 0 , 0);
			errorMessage1.setForeground(red);
	  	}
	  	
	  	// if at least one headline is checked in the table
	  	else{
	  		
	  		// throw the list of info of checked headlines to ArticleView 
	  		// to see if each headline's article is already in the article table 
		  	updatedNum = ArticleView.updateTable(headlines);
		  	
		  	// clear the temporary storage for headlines info
	        headlines.clear();
	        
	        // if no articles are added into the article table
	        // in the other words, if no article table updates occur
	        if(updatedNum == 0){

	        	// show the message
	        	errorMessage1.setText("All articles had been received before.");
				errorMessage1.pack();							
				Color black = new Color(null, 0 , 0 , 0);
				errorMessage1.setForeground(black);
	        }
	        else{
	        	// if at least one article is added into article table
	        	// show the message
	        	errorMessage1.setText(updatedNum+" articles are received in ArticleView.");
				errorMessage1.pack();							
				Color black = new Color(null, 0 , 0 , 0);
				errorMessage1.setForeground(black);	        	
	        }
	        
	  	}		
	}
	
	// remove the checked headlines in the table
	public static void removeHeadline(){
		
		// whether a checkbox (headline) is checked
        boolean isChecked = false;
		
  		// hold indexes of headlines checked in the table
  		Vector<Integer> checkedIndexes = new Vector<Integer>();
  		
		// the number of checked checkboxes
  		int checkedNum = 0;
		
		// iterate through headlines to see which headline has been checked
	  	for (int index = 0; index < table.getItemCount(); index++) {	  		
	  		isChecked = table.getItem(index).getChecked();
	  		
	  		// if a headline is checked
	  		if(isChecked){
	  			
	  			// add the table index of the checked headline into vector
	  			checkedIndexes.add(index);
	  			checkedNum++;
	  		}     
	  	}
	  	
	  	// if none of the headlines are checked in the table
	  	if(checkedNum == 0){
	  		
	  	    //show error message
	        errorMessage1.setText("Please check a headline.");
			errorMessage1.pack();							
			Color red = new Color(null, 255 , 0 , 0);
			errorMessage1.setForeground(red);
	  	}
	  	
	  	// if at least one headline is checked in the table
	  	else{
	  		// convert vector into array for removing checked headlines 
	  		int[] checkedHeadlines = new int[checkedIndexes.size()];
	  		for(int index=0; index<checkedIndexes.size(); index++){
	  			checkedHeadlines[index] = checkedIndexes.get(index);
	  		}
	  		
	  		// checked headlines are removed from the table
	  		table.remove(checkedHeadlines);
	  		
	  		// show the message
	        errorMessage1.setText(checkedNum+" headlines are removed from the table.");
			errorMessage1.pack();							
			Color black = new Color(null, 0 , 0 , 0);
			errorMessage1.setForeground(black);	        	
	    }	
	}
	
	// check whether a user wants all headlines checked or unchecked
	public static void checkButton(Table table, Button selectAllButton){
		
		// if user checks the "check all" checkbox, all headlines checkboxes will be checked
		if (selectAllButton.getSelection()){
			setAllChecked(table, true);
		}
		
		// if user unchecks the "check all" checkbox, all headlines will be unchecked
		else          
			setAllChecked(table, false); 
	}
	
	// set each headline checkbox to check
	public static void setAllChecked(Table table, boolean selectCheck) {
		TableItem[] headlines = table.getItems();
		int numHeadlines = headlines.length;
		
		for (int headlineIndex = 0; headlineIndex < numHeadlines; headlineIndex++){

			headlines[headlineIndex].setChecked(selectCheck);
		}
	}
	
	// for updating the headline table
	// return the number of headlines added
	public static int updateTable(Vector<String[]> headlines){
		
		// whether a headline already exist in the headline table
		boolean duplicateHeadline = false;
		
		// the number of headlines added in the table
		int updatedNum = 0;
		
		// check for duplicates
		for(int index=0; index<headlines.size(); index++){
			duplicateHeadline = checkHeadlines(headlines.elementAt(index)[3]);
	        
			// if headline doesn't exist yet in the current table
	        // add it in table and display it for the user
			if (duplicateHeadline == false){
				TableItem headline = new TableItem(table, SWT.NULL);
				headline.setText(0, headlines.elementAt(index)[0]);
				headline.setText(1, headlines.elementAt(index)[1]);
				headline.setText(2, headlines.elementAt(index)[2]);
				headline.setText(3, headlines.elementAt(index)[3]);
				headline.setText(4, headlines.elementAt(index)[4]);
				
			    // pack each table column so each displays well
			    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			        table.getColumn(loopIndex).pack();
			    }
			    updatedNum++;
			}
		}
		return updatedNum;
	}
	
	// check whether a specific headline already exists in the table
	public static boolean checkHeadlines(String headline){
		
        for (int headlineIndex = 0; headlineIndex<table.getItemCount(); headlineIndex++){
			
			// if a headline already exists, we don't add it into the table
			if (headline.equals(table.getItem(headlineIndex).getText(3))){
				return true;
			}
		}
		return false;	
	}
	
}
