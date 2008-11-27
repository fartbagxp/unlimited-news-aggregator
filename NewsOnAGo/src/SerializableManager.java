import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The SerializableManager class deals with:
 *  - user data reading from the computer disk.
 *  - user data writing to the computer disk. 
 * It is used to permanently save NewsSources, Articles, and Albums into the computer disk. 
 * 
 * @version 2.0 (11/19/2008)
 * @author NewsOnAGo team
 */

public class SerializableManager {
	
	// read (get) the data from the computer disk by a specific directory  
	public static Object getDataFromDisk(String directory) { 
		
		Object temp = null;
	    try{
	      FileInputStream fis = new FileInputStream(directory);
	      ObjectInputStream ois = new ObjectInputStream(fis);
	      temp = ois.readObject();
	      fis.close();
	      ois.close();
	    }
	    catch(FileNotFoundException e){ 
	    	// if the file is not found, return null
	    	return temp;
	    } 
	    //if the exception other than FileNotFoundException occurs
	    catch (Exception e) { 
	    	// show error message in the console
			e.printStackTrace();
		} 
		return temp;
	}
	
	// write (save) the data into the computer disk by a specific directory
	public static void saveDataToDisk(Object object, String directory) {
		
	    try{
	      FileOutputStream fos = new FileOutputStream(directory);
	      ObjectOutputStream oos = new ObjectOutputStream(fos);
	      oos.writeObject(object);
	      oos.flush();
	      fos.close();
	    }
	    //if the exception occurs
	    catch(Exception e){
	    	
	    	// show error message in the console
	    	e.printStackTrace();
	    }
	}
	
}