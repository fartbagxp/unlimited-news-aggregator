import java.text.Collator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * The SortListenerfactory class sorts columns in any table.
 *  
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

class SortListenerFactory implements Listener {
	
    private Comparator<TableItem> currentComparator = null;
    
    private Collator col = Collator.getInstance(Locale.getDefault());
    
    // different algorithms have different numbers
    public static final int TITLE_COMPARATOR = 0;
    public static final int DATE_COMPARATOR = 1;
    public static final int SOURCE_COMPARATOR = 2;
    public static final int LINK_COMPARATOR = 3;
    public static final int DESCRIPTION_COMPARATOR = 4;
    
    // branch off different sorting algorithms for each column - most use strings
    private SortListenerFactory(int columnNum){
        switch (columnNum){

        // string compare title
        case TITLE_COMPARATOR:
            currentComparator = strComparator;
            break;
            
        // string compare authors
        case DATE_COMPARATOR:
            currentComparator = dateComparator;
            break; 
            
        // string compare sources
        case SOURCE_COMPARATOR:
            currentComparator = strComparator;
            break;
            
        // date compare published dates
        case LINK_COMPARATOR:
            currentComparator = strComparator;
            break;
            
        // string compare descriptions
        case DESCRIPTION_COMPARATOR:
        	currentComparator = strComparator;
        
        // string compare anything default - will never happen
        default:
            currentComparator = strComparator;
        }
    }
    
    // create a new listener once we know which column we're trying to sort
    public static Listener getListener(int columnNum){
        return new SortListenerFactory(columnNum);
    }
    
    private int colIndex = 0;
    private int updown   = 1;
         
    // String Comparator
    private Comparator<TableItem> strComparator = new Comparator<TableItem>(){
        public int compare(TableItem arg0, TableItem arg1){

            TableItem table1 = (TableItem)arg0;
            TableItem table2 = (TableItem)arg1;

            // the two strings that needs to be compared
            String word1 = (table1.getText(colIndex));
            String word2 = (table2.getText(colIndex));
            
            // trim the string if it has a ' prefix
            if (word1.startsWith("'")){
            	word1 = word1.substring(1, word1.length());
            }

            if (word2.startsWith("'")){
            	word2 = word2.substring(1, word2.length());
            }
            
            return (col.compare(word1,word2))*updown;
        }
    };
    
    private Comparator<TableItem> dateComparator = new Comparator<TableItem>(){
        public int compare(TableItem arg0, TableItem arg1){    
            TableItem table1 = (TableItem)arg0;
            TableItem table2 = (TableItem)arg1;
            
            // get the entire date from the column
            String columnDateTime1 = (table1.getText(colIndex)).trim();
            String columnDateTime2 = (table2.getText(colIndex)).trim();
            
            // set the delimiter to a single space
            String delim = "[ ]+";
            
            // separate string into tokens so we can get a date like "Nov 10 2008" by itself
            String[] dateToken1 = columnDateTime1.split(delim);
            String[] dateToken2 = columnDateTime2.split(delim);
            
            // convert a shorten month to a string number
            dateToken1[1] = monthToInt(dateToken1[1]);
            dateToken2[1] = monthToInt(dateToken2[1]);
            
            // create a date in the form of Month 
            String date1 = dateToken1[1] + '/' + dateToken1[2] + '/' + dateToken1[5];
            String date2 = dateToken2[1] + '/' + dateToken2[2] + '/' + dateToken2[5];
            
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
 
            Date comparableDate1 = null; 
            Date comparableDate2 = null;
            
            // try parsing a Date class object into the format of Month/Day/Year from a string
            try {
                comparableDate1 = dateFormat.parse(date1);
            }
            
            // catch an error if parsing fails
            catch (ParseException e){
            	System.out.println("First date caused this error\n");
            }
            
            // try parsing a date into Month/Day/Year
            try {              
                comparableDate2 = dateFormat.parse(date2);
            }
            
            // catch an error if parsing fails
            catch (ParseException e){
            	System.out.println("Second date caused this error\n");
            }
           
            // if a date equals to another date, compares the time
            // return true (*updown) if time1 is before time2
            // The expression (expr1) ? (expr2) : (expr3) evaluates to expr2 
            // if expr1 evaluates to TRUE, and expr3 if expr1 evaluates to FALSE.
            if (comparableDate1.equals(comparableDate2))
                return updown*(hourComparison(dateToken1[3], dateToken2[3]));
            
            // return true (*updown) if date1 is before date2
            return updown*(comparableDate1.before(comparableDate2) ? 1 : -1);
        }    
    };
   
    // handles the event of user pressing down on any of the column buttons
    public void handleEvent(Event e) {
        
    	// variable to determine which way the user wants to sort it
    	// example. [a-z] or [z-a]
        // The expression (expr1) ? (expr2) : (expr3) evaluates to expr2 
        // if expr1 evaluates to TRUE, and expr3 if expr1 evaluates to FALSE.
        updown = (updown == 1 ? -1 : 1);
        
        TableColumn currentColumn = (TableColumn)e.widget;
        Table table = currentColumn.getParent();
        
        // search for the column we need to sort by
        colIndex = searchColumnIndex(currentColumn);
        table.setRedraw(false);
        
        TableItem[] items = table.getItems();
        
        // sort the column based on the comparator
        Arrays.sort(items, currentComparator);
        
        // redraw the entire table length
        table.setItemCount(items.length);
       
        // find each tableItem and redraw it accordingly
        for (int i = 0; i < items.length; i++){   
            TableItem item = new TableItem(table,SWT.NONE,i);
            item.setText(getData(items[i]));
            items[i].dispose();
        } 
  
        table.setRedraw(true);     
    }
    
    // time of day comparison
    private int hourComparison(String currentTime1, String currentTime2){
    	
    	DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date comparableTime1 = null; 
        Date comparableTime2 = null;
        
        // try parsing a Date class object into the format of hour/minute/seconds from a string
        try {
            comparableTime1 = dateFormat.parse(currentTime1);
        }
        
        // catch an error if parsing fails
        catch (ParseException e){
        	System.out.println("First time caused this error\n");
        }
        
        // try parsing a date into Month/Day/Year
        try {              
            comparableTime2 = dateFormat.parse(currentTime2);
        }
        
        // catch an error if parsing fails
        catch (ParseException e){
        	System.out.println("Second time caused this error\n");
        }
        
        // if the time equals, there's really no point to sort, simply return 0
        if (comparableTime1.equals(comparableTime2)){
        	return 0;
        }
        
        // The expression (expr1) ? (expr2) : (expr3) evaluates to expr2 
        // if expr1 evaluates to TRUE, and expr3 if expr1 evaluates to FALSE.
        return (comparableTime1.before(comparableTime2) ? 1 : -1);
    }
    
    // get the data to redraw the table
    private String[] getData(TableItem t){
        Table table = t.getParent();
        
        int colCount = table.getColumnCount();
        String [] s = new String[colCount];
        
        for (int i = 0;i<colCount;i++)
            s[i] = t.getText(i);
                
        return s;
    }
    
    // search for which column we're sorting by
    private int searchColumnIndex(TableColumn currentColumn){
        Table table = currentColumn.getParent();
        
        int in = 0;
        
        for (int i = 0;i<table.getColumnCount();i++)
            if (table.getColumn(i) == currentColumn)
                in = i;
        
        return in;
    }

    // painful way to convert all the shorten month names to a string number
	private String monthToInt(String month){
		
		if (month.equals("Jan"))
			return "1";
		
		else if (month.equals("Feb"))
			return "2";
		
		else if (month.equals("Mar"))
			return "3";
		
		else if (month.equals("Apr"))
			return "4";
		
		else if (month.equals("May"))
			return "5";
		
		else if (month.equals("Jun"))
			return "6";
		
		else if (month.equals("Jul"))
			return "7";
		
		else if (month.equals("Aug"))
			return "8";
		
		else if (month.equals("Sep"))
			return "9";
		
		else if (month.equals("Oct"))
			return "10";
		
		else if (month.equals("Nov"))
			return "11";
		
		else if (month.equals("Dec"))
			return "11";
		
		else 
			return "0";
	}
}
