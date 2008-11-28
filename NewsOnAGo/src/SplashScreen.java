import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

/**
 * The SplashScreen class loads things that needs to be loaded at startup 
 * It starts the splash screen of NewsOnAGo.  
 *  
 * @version 2.0 (11/27/2008)
 * @author NewsOnAGo team
 */

public class SplashScreen {

    // creates a splash screen when loading up
	static void createSplash(){
		
		// create new splash screen with progress bar
		final Display display = new Display();
	    final Shell splashScreen = new Shell(SWT.ON_TOP);
	    final ProgressBar bar = new ProgressBar(splashScreen, SWT.NONE);
	    final int[] count = new int[] { 4 };
	    
	    // load up the splash screen with the image
	    final Image image = loadImage(display);
	    
	    // set progress bar to load 4 times
	    bar.setMaximum(count[0]);
	    
	    // create a label-image for splash screen
	    Label label = new Label(splashScreen, SWT.NONE);
	    label.setImage(image);
	    
	    // set up the layout
	    FormLayout layout = new FormLayout();
	    splashScreen.setLayout(layout);
	    
	    // set up the progress bar
	    FormData progressData = new FormData();
	    progressData.left = new FormAttachment(0, 5);
	    progressData.right = new FormAttachment(100, -5);
	    progressData.bottom = new FormAttachment(100, -5);
	    bar.setLayoutData(progressData);
	    
	    // pack together the screen
	    splashScreen.pack();
	    
	    // set the splash screen to the center
	    Rectangle splashRect = splashScreen.getBounds();
	    Rectangle displayRect = display.getBounds();
	    int x = (displayRect.width - splashRect.width) / 2;
	    int y = (displayRect.height - splashRect.height) / 2;
	    splashScreen.setLocation(x, y);
	    splashScreen.open();
	    
	    // make a new thread to start to load up progress bar
	    display.asyncExec(new Runnable() {
	        public void run() {
	        	
	        	startUp(bar);
	            
	            // close the thread
	            splashScreen.close();
	            image.dispose();
	          
	        }
	    });
	    
	    // if splash screen isn't disposed, make the display sleep
	    while (!splashScreen.isDisposed()) {
	    	if (!display.readAndDispatch())
	    		display.sleep();
	    }
	    display.dispose();
	}
	
	// set up all the repositories for storing news sources, articles, and albums
	// load up all the saved news sources, articles, and albums
	static void startUp(ProgressBar bar){
		
		int progressIndex = 0;
		
      	// create news source repository 
      	// read all the stored news sources from the file "newssourcelist.ser" 
      	// put them into created news source repository
      	new NewsSourceRepository(".\\newssourcelist.ser");
		bar.setSelection(progressIndex+1);
		progressIndex++;
		sleepThread();
      	
      	     	
      	// create a new article repository 
      	// read all the stored articles from the file "articlelist.ser" 
      	// put them into created article repository
      	new ArticleRepository(".\\articlelist.ser");
		bar.setSelection(progressIndex+1);
		progressIndex++;
		sleepThread();
      	 	
      	// create a new album repository 
      	// read all the stored albums from the file "albumlist.ser" 
      	// put them into created album repository
      	new AlbumRepository(".\\albumlist.ser");
		bar.setSelection(progressIndex+1);
		progressIndex++;
		sleepThread();
		
	}
	
	// make a thread sleep
	static void sleepThread(){
        try {
            Thread.sleep(1000);
        } 
        catch (Throwable e) {
        }
	}
	
	// loading up an image
	static Image loadImage(Display display){
	
		Image image = null;
		
		// load image from InputStream 
		try {
			image = new Image(display, new FileInputStream(".\\NewsOnAGoIcon.jpg"));
		}
		
		// file was not found, create null image
		catch (IOException e) {
			image = null;
		}
		  
		// return the Image 
		return image;
	}
}
