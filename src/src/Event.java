package src;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;


public class Event {
	String name;
	String city;
	String address;
	String url;
	long time;
	double lat;
	double lon;
	long yes_rsvp_count;
	
	public Event(JSONObject event) {
		name = (String) event.get("name");
	
		if(event.containsKey("venue")){
			JSONObject venue = (JSONObject) event.get("venue");
			try {
				city = (String) venue.get("city");
				address = (String) venue.get("address_1");
				lat = (Double) venue.get("lat");
				lon = (Double) venue.get("lon");}
			catch(Exception e){/*System.out.println("check venue format"+venue);*/}
		}
		url = (String) event.get("event_url");
		time = (Long) event.get("time");
		yes_rsvp_count = (Long)event.get("yes_rsvp_count");
	}
	
	public void printLocation() {
		System.out.println("     Location:  "+ address + ",  "+ city );
	}
	
	public boolean hasGeoCoordiates() {
		if(lat!=0 && lon!=0)
			return true;
		else
			return false;
	}
	

}
