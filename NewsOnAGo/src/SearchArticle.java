
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * The SearchArticle class searches for articles through keyword(s)
 *  
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class SearchArticle {
 	
	// search the table for articles
	public static void search(Table table, Text searchText){
		
		// create a vector of search results
	 	Vector<SearchPriority> searchResult = new Vector<SearchPriority>(); 
		
		// get the length (how many) articles there are currently in table
		TableItem[] headlines = table.getItems();
		int headlinesNum = headlines.length;
	 	
	 	String TextToString = searchText.getText();
	 	TextToString = TextToString.toLowerCase();
	 	
	 	// set the delimiter to a single space
	 	String delim = "[ ]+";
	 	
	 	// separate keyword into tokens 
	 	String[] keywordTokens = TextToString.split(delim);
	 	int numKeywords = keywordTokens.length;
	 	
		// iterate through all headlines in table and parse headlines, descriptions into single word tokens
		for (int headlineIndex = 0; headlineIndex < headlinesNum; headlineIndex++){
			
			// get the headline String, replace all additional symbols with space
			String headlineString = headlines[headlineIndex].getText(0);
			headlineString = headlineString.toLowerCase();
		
			// get the description String, replace all additional symbols with space
			String descriptionString = headlines[headlineIndex].getText(4);
			descriptionString = descriptionString.toLowerCase();
			
			// loop through each keyword and attempt to find the keywords in headlines
			for (int keywordIndex = 0; keywordIndex < numKeywords; keywordIndex++){
				
				// check for number of occurrence in headline text
				int numOccurrence = findOccurrence(keywordTokens[keywordIndex], headlineString);
				
				int numOccurrence2 = findOccurrence(keywordTokens[keywordIndex], descriptionString);
				
				int totOccurance = numOccurrence + numOccurrence2;
				
				// if keyword appears in any headline at least once, add it into vector
				if (totOccurance > 0){
					
					TableItem item = headlines[headlineIndex];
				
					
					SearchPriority addedHeadline = new SearchPriority(item, totOccurance);
					searchResult.add(addedHeadline);
				}
			}
		}
		
		redraw(table, searchResult, headlines);
	}
	
	// find which headline is related to keywords and put them in a vector
	public static int findOccurrence(String keywordToken, String matchString){
		
		int stringIndex = 0;
		int numOccurance = 0;
		int matchLength = matchString.length();
		matchLength--;
		
		stringIndex = matchString.indexOf(keywordToken, stringIndex);
		
		// if there is no match, exit
		while (stringIndex != -1){
			
			// keywordToken was found!
			numOccurance = numOccurance + 1;
			
			stringIndex = stringIndex + keywordToken.length();
			
			// if stringIndex is passed the match string length, stop
			if (stringIndex >= matchLength){
				return numOccurance;
			}
			
			stringIndex = matchString.indexOf(keywordToken, stringIndex);
		}

		return numOccurance;
	}
	
	// comparison by number of times a keyword appears in a tableitem's headline and description
    private static Comparator<SearchPriority> priorityComparator = new Comparator<SearchPriority>(){
        public int compare(SearchPriority arg0, SearchPriority arg1){
        	
        	// If it's true, set it to 1.  Else, set it to -1.
        	int retVal = (arg0.numAppearance < arg1.numAppearance) ? 1 : -1;
        	return retVal;
        }
    };
	
	// redraw the table according to search
	public static void redraw(Table table, Vector<SearchPriority> searchResult, TableItem[] headlines){
		
		 // number of headlines in the table currently
		int tableLength = headlines.length;   
		
        table.setRedraw(false);
        
        // sort the column based number of appearance
        Collections.sort(searchResult, priorityComparator);
       
        // find each tableItem and depose the ones we don't need
        for (int headlineIndex = 0; headlineIndex < tableLength; headlineIndex++){   
        
        	// dispose of the article if it wasn't found
        	if (findArticle(table, searchResult, headlines[headlineIndex]) == false){
        		headlines[headlineIndex].dispose();
        	}
        } 
        
        // redraw the table
        table.setRedraw(true);  
		
	}
	
	// find an article within the vector
	// if it exists, don't bother with it
	// if it doesn't exist, we have to take it out of the table (delete it)
	public static boolean findArticle(Table table, Vector<SearchPriority> searchResult, TableItem headline){
		
		int newTableSize = searchResult.size();
		
		// iterate through all the headlines in a vector
		for (int headlineIndex = 0; headlineIndex < newTableSize; headlineIndex++){
			
			// headline text inside searchResult vector
			String headlineText = searchResult.elementAt(headlineIndex).headline.getText(0);
			
			// headline text currently in the table
			String tableHeadline = headline.getText(0);
			
			// if the two headlines equal, we don't have to delete it
			if (headlineText.equalsIgnoreCase(tableHeadline)){
				
				return true;
			}
		}
		return false;
	}
		
}
