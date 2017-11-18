package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class APIRequest {
 
 
	String city = "";
	public String topic = "";
	String radius = "25.0";
	String order = "distance";
	String key = getApiKey();

	public static String getApiKey(){
		String key = "a22572e5c3b65611273533a12321fb";
		return key;																//Return the key value.
	}
 
	public ArrayList <Event> getEvent(String path_code) throws Exception{
		String responseString = "";
 
		URI request = new URIBuilder()			//We build the request URI
			.setScheme("http")
			.setHost("api.meetup.com")
			.setPath(path_code)
			//List of parameters :
			.setParameter("topic", topic)
			.setParameter("city", city)
			.setParameter("radius", radius)
			//.setParameter("order", order)
			//End of params
			.setParameter("key", key)
			.build();
 
		HttpGet get = new HttpGet(request);			//Assign the URI to the get request
		System.out.println("Get request : "+get.toString());
 
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(get);
		responseString = EntityUtils.toString(response.getEntity());

		ArrayList<Event> eventsList = DecodeJSON(responseString);	
		System.out.println(eventsList.size());

		return eventsList;
	}
 
	
	
	public static ArrayList <Event> DecodeJSON(String responseString){
		ArrayList <Event> eventsList = new ArrayList<Event>();
		try{
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(responseString);
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

 
}