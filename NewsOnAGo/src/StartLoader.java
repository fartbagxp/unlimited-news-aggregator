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
    	
    	// create a splash screen
    	SplashScreen.createSplash();
		
    	// start up NewsOnAGo
		new StartLoader();
		
		// write the data before the user closes the application
		writeData();
    }
    
    // write news sources, articles, and albums into 
    public static void writeData(){
    	
	    // write all the news sources into the file "newssourcelist.ser" stored in the computer disk 
		NewsSourceRepository.writeNewsSourcesIntoDisk(".\\newssourcelist.ser");
		          
		// write all the articles into the file "articlelist.ser" stored in the computer disk 
		ArticleRepository.writeArticlesIntoDisk(".\\articlelist.ser");
		          
	    // write all the albums into the file "albumlist.ser" stored in the computer disk 
		AlbumRepository.writeAlbumsIntoDisk(".\\albumlist.ser");
    }
}
