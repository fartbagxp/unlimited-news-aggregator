
import java.util.Vector;

import org.eclipse.swt.SWT;
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
 * The SourceView class contains all elements and functions inside the sources tab.
 * 
 * The user deals with news sources in this tab.  
 * 
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class SourceView {
	
	// news source table
	private static Table table;
	
	// error message for removing news source and getting headlines 
	private static Label errorMessage1;
	
	// error message for adding news source
	private static Label errorMessage2;
	
	// textbox for inputting the title of the added news source
	private static Text textboxForTitle;
	
	// textbox for inputting the link of the added news source
	private static Text textboxForLink;
	
	// news source table includes the following columns
	private static String[] titles = { "Title", "Link" };
		
	// create a tab for Source View
	public static void createTab(TabFolder tabFolder, TabItem tab){
		
		// create a tab composite
		Composite tabComposite = new Composite(tabFolder, SWT.NONE);
		
		// set control of composite to the tab
		tab.setControl(tabComposite);
		
		// create a group to group some swt widgets
		final Group group1 = new Group(tabComposite, SWT.SHADOW_ETCHED_IN);
		group1.setBounds(0, 10, 290, 100);
	    group1.setText("Table Operation");
        	    
	    // create a "Get Headlines" button to get headline
	    final Button getheadlines = new Button(group1, SWT.PUSH);
	    getheadlines.setBounds(85, 22, 100, 45);
	    getheadlines.setText("Get Headlines");
	    
	    // create a label to show error message
	    errorMessage1 = new Label(group1, SWT.NONE);
	    errorMessage1.setText("Please check a news source.");
	    errorMessage1.setBounds(35, 75, 100, 20);
	    errorMessage1.pack();
	    		
		// create a group to group some swt widgets
		final Group group2 = new Group(tabComposite, SWT.SHADOW_ETCHED_IN);
		group2.setBounds(0, 120, 290, 190);
	    group2.setText("Add and Remove News Source");
	    
	    // create a label
	    final Label label1 = new Label(group2, SWT.NONE);
	    label1.setText("Source Title: ");
	    label1.setBounds(20, 20, 100, 20);
	    label1.pack();
        
	    // creates a textbox for user to type in source title to add a news source 
	    textboxForTitle = new Text(group2, SWT.BORDER);
	    textboxForTitle.setText("");
	    textboxForTitle.setBounds(20, 40, 250, 20);
	    textboxForTitle.setTextLimit(50);
	    
	    // create a label
	    final Label label2 = new Label(group2, SWT.NONE);
	    label2.setText("Source Link: ");
	    label2.setBounds(20, 70, 100, 20);
	    label2.pack();
	    
	    // creates a textbox for user to type in source link to add a news source
	    textboxForLink = new Text(group2, SWT.BORDER);
	    textboxForLink.setText("http://");
	    textboxForLink.setBounds(20, 90, 250, 20);
	    textboxForLink.setTextLimit(50);
	    
	    // create a "Add" button to add a news source
	    final Button addSource = new Button(group2, SWT.PUSH);
	    addSource.setText("Add");
	    addSource.setBounds(110, 120, 80, 30);
	    
	    // create a "remove" button to remove the news source
        final Button removeSource = new Button(group2, SWT.PUSH);
        removeSource.setBounds(190, 120, 80, 30);
        removeSource.setText("Remove");
	    
	    // create a label to show error message
	    errorMessage2 = new Label(group2, SWT.NONE);
		errorMessage2.setText("Both fields are required for adding source.");
		errorMessage2.setBounds(35, 160, 200, 20);
		errorMessage2.pack();
	               
		// create a table for displaying all the news sources
		table = new Table(tabComposite, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		table.setHeaderVisible(true);
		table.setBounds(295, 10, 670, 600);
		
		// create columns of the table header ("Title", "Link") for user to click on
        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
            TableColumn column = new TableColumn(table, SWT.NULL);
            column.setText(titles[loopIndex]);
            
            // adding a listener to sort columns
            String columnString = column.getText();
            if(columnString.equals("Date"))
                column.addListener(SWT.Selection, SortListenerFactory.getListener(1));
            else
                column.addListener(SWT.Selection, SortListenerFactory.getListener(0));
        }

		// show all the news sources in the table
		for(int loopIndex=0; loopIndex < NewsSourceRepository.getNumOfSources(); loopIndex++){
			TableItem source = new TableItem(table, SWT.NULL);
			source.setText(0, NewsSourceRepository.getSourceByIndex(loopIndex).getTitle());
			source.setText(1, NewsSourceRepository.getSourceByIndex(loopIndex).getLink()); 
		}		
	    
		// if "Add" button is pressed
		Listener addSourceListener = new Listener(){
			public void handleEvent(Event event){
				try{
					// add a news source specified by the user
					addSource();
				}
				catch(Exception e){
					// if something wrong, show error message in the console
			        e.printStackTrace();
				}   
				finally{
					
				}
			}
		};
		       	 
		addSource.addListener(SWT.Selection, addSourceListener);
		
		// if "Remove Source" button is pressed
		Listener removeSourceListener = new Listener(){
			public void handleEvent(Event event){
				try{
					// remove the news sources checked in the source table
					removeSource();
				}
				catch(Exception e){
					// if something wrong, show error message in the console
			        e.printStackTrace();
				}   
				finally{
					
				}
			}
		};
		       	 
		removeSource.addListener(SWT.Selection, removeSourceListener);
		
        // if "Get Headlines" button is pressed
		Listener getHeadlineListener = new Listener(){
            public void handleEvent(Event event) {
            	try{
            		// get the headlines from the news sources checked by the user
            		getHeadlines();
            	}
				catch(Exception e){
					// if something wrong, show error message in the console
			        e.printStackTrace();
				}   
				finally{
					
				}                
            }
		};
		
		getheadlines.addListener(SWT.Selection, getHeadlineListener);
				
	    // pack each table column so each displays well
	    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	        table.getColumn(loopIndex).pack();
	    }        
	}
	
	// add the news source into the news source table
	public static void addSource(){
		
		// the title of the added news source typed in by the user
		String sourceTitle = textboxForTitle.getText();
		
		// the link of the added news source typed in by the user
		String sourceLink = textboxForLink.getText();
		
		// if Source Title textbox is empty
		if(sourceTitle.equals("")){
			
			// show error message
			errorMessage2.setText("Source Title is required.");
			errorMessage2.pack();							
			Color red = new Color(null, 255 , 0 , 0);
			errorMessage2.setForeground(red);
		}
		else
		{
            // if Source Link textbox is empty
			if(sourceLink.equals("")){
				
				// show error message
				errorMessage2.setText("Source Link is required.");
				errorMessage2.pack();							
				Color red = new Color(null, 255 , 0 , 0);
				errorMessage2.setForeground(red);
			}	
			else{
				// if added news source does not exist in the news source repository
				if(!NewsSourceRepository.checkNewsSourceRepo(sourceLink)){
					
					// store that news source in the news source repository
					NewsSource addedSource = new NewsSource(sourceTitle, sourceLink);
					NewsSourceRepository.addSource(addedSource);
					
					// show that news source in the source table
					TableItem source_1 = new TableItem(table, SWT.NULL); 
					source_1.setText(0, sourceTitle);
					source_1.setText(1, sourceLink);
					
				    // pack each table column so each displays well
				    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
				        table.getColumn(loopIndex).pack();
				    }
				    
				    // show the message
				    errorMessage2.setText("News source is added.");
					errorMessage2.pack();							
					Color black = new Color(null, 0 , 0 , 0);
					errorMessage2.setForeground(black);
				}
				
				// if added news source already exists in the news source repository
				else{					
				    // show error message
					errorMessage2.setText("News source already exists.");
					errorMessage2.pack();							
					Color red = new Color(null, 255 , 0 , 0);
					errorMessage2.setForeground(red);
				}
			}
		}
	}
	
	// remove the checked news sources from the table
	public static void removeSource(){
        
		// whether each checkbox (news source) is checked
		boolean isChecked = false;
  		
  	    // hold indexes of news sources checked in the table
  		Vector<Integer> checkedIndexes = new Vector<Integer>();
  		
  	    // the number of checked checkboxes   
  		int checkedNum=0; 
		
		// iterate through news sources to see which news source has been checked
	  	for (int index = 0; index < table.getItemCount(); index++) {
	  		
	  		// check if each checkbox (news source) is checked
	  		isChecked = table.getItem(index).getChecked();
	  			  		
	  		// if a news source has been checked 
	  		if(isChecked){
                String checkedLink = table.getItem(index).getText(1);
                
                // remove the news source from news source repository
				NewsSourceRepository.removeSourceByLink(checkedLink);
				
				// add the table index of the checked news source into vector
				checkedIndexes.add(index);
				
				// pack each table column so each displays well
			    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			        table.getColumn(loopIndex).pack();			        
			    }
                checkedNum++;
		  	}
		}
	  	
	  	// if none of news sources are checked
        if(checkedNum == 0){
        	
	        //show error message
            errorMessage2.setText("Please check a news source to remove.");
			errorMessage2.pack();							
			Color red = new Color(null, 255 , 0 , 0);
			errorMessage2.setForeground(red);
	  	}
        else{
        	// if at least one news source is checked
        	// convert vector into array for removing checked news sources 
	  		int[] checkedSources = new int[checkedIndexes.size()];
	  		for(int index=0; index<checkedIndexes.size(); index++){
	  			checkedSources[index] = checkedIndexes.get(index);
	  		}
	  		
	  		// remove checked news sources from the table
	  		table.remove(checkedSources);
	  		
	  		// show the message
	        errorMessage2.setText(checkedNum+" news sources are removed.");
			errorMessage2.pack();							
			Color black = new Color(null, 0 , 0 , 0);
			errorMessage2.setForeground(black);
        }
	}
	
	// get the headlines from the checked news sources in the source table 
	public static void getHeadlines(){
		
		// whether each checkbox is checked
		boolean isChecked = false; 	
		
		// the number of checked checkboxes
  		int checkedNum = 0; 
  		
  	    // the number of headlines received from each checked news source
  		// in the other words, the number of headlines (received from each news source) added in the headline table
  		int updatedNum = 0; 
  		
  	    // the total number of headlines added into the headline table
  		int totalAddedHeadline = 0;
  		
  		// temporary storage for headlines received from each news source checked in the table
  		Vector<String[]> headlines = new Vector<String[]>();
		
		// iterate through news sources to see which news source has been checked
	  	for (int index = 0; index < table.getItemCount(); index++) {
	  		
	  		// temporarily hold for a list of headlines received from each news source
	  		headlines = new Vector<String[]>();
	  		
	  		// check if each checkbox is checked
	  		isChecked = table.getItem(index).getChecked();
	  		
	  		// if a news source has been checked, get the headline from this news source
	  		if(isChecked){
	  			
	  			// get the title of the checked news source
                String checkedTitle = table.getItem(index).getText(0);
                
                // get the link of the checked news source
                String checkedLink = table.getItem(index).getText(1);
                
                // get the headlines from each news source 
                headlines = RSSFeed.iterateRSSFeed(checkedTitle, checkedLink); 
                
                // show the received headlines in the headline table 
                updatedNum = HeadlineView.updateTable(headlines);
                totalAddedHeadline = totalAddedHeadline + updatedNum;
                
                // clear the vector which is temporary storage for a list of headlines received from each news source
                headlines.clear();
                checkedNum++;
		  	}
		}
	  	
	  	// if no news sources are checked in the table
	  	if(checkedNum == 0){
	  		
        	//show error message
            errorMessage1.setText("Please check a news source.");
			errorMessage1.pack();							
			Color red = new Color(null, 255 , 0 , 0);
			errorMessage1.setForeground(red);
	  	}
	  	
	  	// if at least one news source is checked in the table
	  	else{
	  		
	  		// if at least one headline is added into the headline table
	  		if(updatedNum != 0){
	  			
		  	    // show the message
	            errorMessage1.setText(totalAddedHeadline+ " headlines are received in HeadlineView.");
				errorMessage1.pack();							
				Color black = new Color(null, 0 , 0 , 0);
				errorMessage1.setForeground(black); 
		  	}
		  	else{
		  		// if no table updating actions happen in HeadlineView 
		  		// (no headlines are added in the headline table)
		  		// show the message
		  		errorMessage1.setText("All headlines had been received.");
				errorMessage1.pack();							
				Color black = new Color(null, 0 , 0 , 0);
				errorMessage1.setForeground(black); 
		  	}
	  	}
	}
	
}
