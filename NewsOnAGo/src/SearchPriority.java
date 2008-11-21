
import org.eclipse.swt.widgets.TableItem;

/**
 * The SearchPriority class holds:
 *  - headline information from TableItem. 
 *  - number of times an article appears.
 *  
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class SearchPriority {
	
	// hold onto headline information
	public TableItem headline;
	
	// hold onto the number of times an item appears
	public int numAppearance;
	
	// new class constructor to hold a tableItem (headline) and the number of appearance
	public SearchPriority(TableItem headline, int numAppearance){
		this.headline = headline;
		this.numAppearance = numAppearance;
	}
}