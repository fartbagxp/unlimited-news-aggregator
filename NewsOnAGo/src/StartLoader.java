import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * The StartLoader class provides a startup method for NewsOnAGo application.
 * It starts the execution of NewsOnAGo.  
 *  
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class StartLoader {
	
	Display display;
	Shell shell;
	
	// create a new shell and display
	private StartLoader(){
		display = new Display();
		shell = new Shell(display);
		new GUI(display, shell);
	}
	
	// program starts running from here
    public static void main(String[] args) {
    	
    	// create a new news source repository 
    	// read all the stored news sources from the file "newssourcelist.ser" 
    	// put them into created news source repository
    	new NewsSourceRepository(".\\newssourcelist.ser");
    	
    	// create a new article repository 
    	// read all the stored articles from the file "articlelist.ser" 
    	// put them into created article repository
    	new ArticleRepository(".\\articlelist.ser");
    	 	
    	// create a new album repository 
    	// read all the stored albums from the file "albumlist.ser" 
    	// put them into created album repository
    	new AlbumRepository(".\\albumlist.ser");
    	
    	// start up NewsOnAGo
        new StartLoader();
        
        // write all the news sources into the file "newssourcelist.ser" stored in the computer disk 
        NewsSourceRepository.writeNewsSourcesIntoDisk(".\\newssourcelist.ser");
        
        // write all the articles into the file "articlelist.ser" stored in the computer disk 
        ArticleRepository.writeArticlesIntoDisk(".\\articlelist.ser");
        
        // write all the albums into the file "albumlist.ser" stored in the computer disk 
        AlbumRepository.writeAlbumsIntoDisk(".\\albumlist.ser");
   }
}
