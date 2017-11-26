
package src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONObject;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

@SpringBootApplication
public class Main {
/*


*/
	final static Logger LOGGER = Logger.getLogger("src.Main");
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;

	

	public static void main(String[] args) throws Exception{
		
		startLogger();
		//start spring boot
		SpringApplication.run(Main.class, args);
		
		/*Data model = Data.getInstance();
		for(int i=0;i<3;i++)
			model.queryEvents(0,0);*/
	} 

	public static void startLogger() throws Exception{
		fileTxt = new FileHandler("Logging.txt");
		formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        LOGGER.addHandler(fileTxt);
	}

	
 
 
}