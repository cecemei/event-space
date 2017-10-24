
package src;
import java.util.ArrayList;
import java.util.Iterator;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
 
public class Main {
	public static void main(String[] args) {
 
		String key = "";
		String path_code = "/2/open_events";								//PathCode for get-events
																			//More PathCodes : http://www.meetup.com/meetup_api/docs/
		String events = "";
 
		EventGetter eventGetter = new EventGetter();
		key = eventGetter.getApiKey();
 
		
		eventGetter.topic = "tech,photo,art";											//Set parameters
		eventGetter.city = "San Francisco";	
		eventGetter.radius = "25.0";
		try{
			events = eventGetter.getEvent(path_code, key);
		}catch(Exception e){e.printStackTrace();}
		
		ArrayList<Event> eventsList = DecodeJSON(events);	
		System.out.println(eventsList.size());
 
 
	}
 
	public static ArrayList <Event> DecodeJSON(String events){
		ArrayList <Event> eventsList = new ArrayList<Event>();
		try{
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(events);
			JSONArray results = (JSONArray) obj.get("results");
			JSONObject meta = (JSONObject) obj.get("meta");
			System.out.println(meta.get("total_count"));
			System.out.println("Results : ");
			int count = 0;
			Iterator i = results.iterator(); 
			while(i.hasNext()){
				JSONObject event = (JSONObject) i.next();
				Event meetupEvent = new Event(event);
				if(meetupEvent.hasGeoCoordiates() && meetupEvent.yes_rsvp_count>10) {
					count++;
					eventsList.add(meetupEvent);
					System.out.println(String.format("Event %2d: %s",count,meetupEvent.name) );
					meetupEvent.printLocation();
				}
			}
		}
		catch(Exception e){e.printStackTrace();}
		return eventsList;
	}
	
	public static boolean Save2File(String filename) {
		return true;
	
	}
 
}