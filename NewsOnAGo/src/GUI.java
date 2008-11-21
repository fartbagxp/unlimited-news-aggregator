
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * The GUI class creates a graphical user interface (GUI) for the NewsOnAGo feed aggregator.
 *  
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class GUI
{
	/* Note: Displays are responsible from managing event loops and controlling threads while
	 *       Shell is the window of the application.
	 */

	// other objects have to access display 
	public static Display display;
	
	// other objects have to access shell 
    public static Shell shell;
    
    // For classes who needs to access the MainController 
    public static GUI newsOnAGo;

    // a GUI class to initial the shell and creates a menu for the shell
    public GUI(Display display, Shell shell){
    	GUI.display = display;
    	GUI.shell = shell;
    	GUI.newsOnAGo = this;
    	menuMaking();
    }
  
    // make beautiful menu for user to use, lalalala!
    public static void menuMaking(){
    	
        //create a "Get Headlines" button to get headline!
        shell.open();

        shell.setSize(1000, 700);

        shell.setText("NewsOnAGo");

       Menu menu = new Menu(shell, SWT.BAR);
       
       //creating a menu bar
       MenuItem file = new MenuItem(menu, SWT.CASCADE);
       file.setText("File");
       Menu filemenu = new Menu(shell, SWT.DROP_DOWN);
       file.setMenu(filemenu);
       MenuItem openItem = new MenuItem(filemenu, SWT.PUSH);
       openItem.setText("Open");
       MenuItem exitItem = new MenuItem(filemenu, SWT.PUSH);
       exitItem.setText("Exit");
       
       MenuItem help = new MenuItem(menu, SWT.CASCADE);
       help.setText("Help");
       Menu helpmenu = new Menu(shell, SWT.DROP_DOWN);
       help.setMenu(helpmenu);
       MenuItem helpContentItem = new MenuItem(helpmenu, SWT.PUSH);
       helpContentItem.setText("Help Contents");
       MenuItem aboutItem = new MenuItem(helpmenu, SWT.PUSH);
       aboutItem.setText("About NewsOnAGo");
                   
       // close the shell if the menu item "Exit" is pushed
       exitItem.addListener(SWT.Selection, new Listener(){
    	   public void handleEvent(Event event) {
    		   GUI.shell.close();
    	   }
       }); 
       
        
       TabFolder tabFolder = new TabFolder(shell, SWT.BORDER);
       TabItem sourceTab = new TabItem(tabFolder, SWT.NONE);
       sourceTab.setText("sources");
       SourceView.createTab(tabFolder, sourceTab);
       
       TabItem headTab = new TabItem(tabFolder, SWT.NONE);
       headTab.setText("headlines");
       HeadlineView.createTab(tabFolder, headTab);
        
       TabItem articleTab = new TabItem(tabFolder, SWT.NONE);
       articleTab.setText("articles");
       ArticleView.createTab(tabFolder, articleTab);
       
       TabItem albumTab = new TabItem(tabFolder, SWT.NONE);
       albumTab.setText("albums");
       AlbumView.createTab(tabFolder, albumTab);
       
       
       // Bound of table
       tabFolder.setBounds(4, 4, 980, 800);

       shell.setMenuBar(menu);
       shell.open();
        
       // close the shell
       while (!shell.isDisposed()) {
    	   if (!display.readAndDispatch())
    		   display.sleep();
       }
       display.dispose();
    }
}