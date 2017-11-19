package src;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Event {
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
	
	public Event(){
	}

	public Event(JSONObject event) {
		
		name = (String) event.get("name");
		
		if(event.containsKey("venue")){
			JSONObject venue = (JSONObject) event.get("venue");
			try {
				city = (String) venue.get("city");
				state = (String) venue.get("state");
				country = (String) venue.get("localized_country_name");
				address = (String) venue.get("address_1");
				lat = (Double) venue.get("lat");
				lon = (Double) venue.get("lon");}
			catch(Exception e){/*System.out.println("check venue format"+venue);*/}
		}
		url = (String) event.get("event_url");
		time = (Long) event.get("time");
		long utc_offset = (Long) event.get("utc_offset");
		date = new Date(time+utc_offset);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy h:mm,a");
		formattedDate = sdf.format(date);
		

		yes_rsvp_count = (Long)event.get("yes_rsvp_count");
	}
	
	public void printLocation() {
		System.out.println("     Location:  "+ address + ",  "+ city );
	}

	public void printEvent(){
		System.out.printf("\t%s, time:%s, location: %s, %s, %s, %s\n", name, formattedDate, address, city, state, country);
	}
	
	public boolean hasGeoCoordiates() {
		if(lat!=0 && lon!=0)
			return true;
		else
			return false;
	}
	

}
