package src;


import org.json.simple.JSONObject;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

class Event {
/*

Event class

	variable
	--------
	String name
	String city
	String state
	String country
	String address
	String url
	long time
	double lat
	double lon
	long yes_rsvp_count
	Date date
	String formattedDate
	String description

	method
	------
	Event: constructor. 
	hasGeoCoordiates: if the event location has geocoordinates or not. 

*/

	String name;
	String city;
	String state;
	String country;
	String address;
	String url;
	long time;
	double lat;
	double lon;
	long yes_rsvp_count;
	Date date;
	String formattedDate;
	String description;
	
	public Event(){
	}

	public Event(JSONObject event) {
		
		name = (String) event.get("name");
		description = (String) event.get("description");
		if(event.containsKey("venue")){
			JSONObject venue = (JSONObject) event.get("venue");
			try {
				city = (String) venue.get("city");
				state = (String) venue.get("state");
				country = (String) venue.get("localized_country_name");
				address = (String) venue.get("address_1") + ", " + city;
				lat = (Double) venue.get("lat");
				lon = (Double) venue.get("lon");}
			catch(Exception e){/*System.out.println("check venue format"+venue);*/}
		}
		url = (String) event.get("event_url");
		time = (Long) event.get("time");
		long utc_offset = (Long) event.get("utc_offset");
		date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy h:mm a");
		formattedDate = sdf.format(date);
		
		yes_rsvp_count = (Long)event.get("yes_rsvp_count");
	}
	
	public boolean hasGeoCoordiates() {
		if(lat!=0 && lon!=0)
			return true;
		else
			return false;
	}
	

}



public class EventList{
/*

Class EventList

	variable
	--------
	events
*/
        ArrayList<Event> events;
        void setList(ArrayList<Event> theEvents){
            this.events = theEvents;
        }
}





