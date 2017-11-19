
package src;

import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONObject;
 
public class Main {

	

	public static void main(String[] args) throws Exception{
		MeetupAPIRequest request;

		Model model = Model.getInstance();
		ArrayList<City> cities = model.cities;
		ArrayList<Topic> topics = model.topics;
 		if(cities.size()==0 || topics.size()==0){
 			System.out.println("Check api key is valid or not.");
 			System.exit(0);
 		}
		
		User user; 

	
		user = model.createUser();

		while(true){
			ArrayList<Event> events = model.getEvent(user.city);
			System.out.printf("%d suggested events nearby: \n", events.size());
			for(int i=0; i<events.size(); i++){
				events.get(i).printEvent();
			}
		}
	
		
 
 
	}

	
 
 
}