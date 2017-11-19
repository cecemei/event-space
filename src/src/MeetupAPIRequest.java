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

public class MeetupAPIRequest {
 
 	private static MeetupAPIRequest instance = null;
	
	 
	
	private static String key = "6f47431b44364721e382101e244613"; //"6d75526a39297e167b781f4c3c6b3c";//"6f47431b44364721e382101e244613";//"a22572e5c3b65611273533a12321fb";

	protected MeetupAPIRequest(){
		  

	}

	public static MeetupAPIRequest getInstance(){
		if(instance == null){
			instance = new MeetupAPIRequest();
		}
		return instance;
	}



	public ArrayList<JSONObject> gettopics() throws Exception{

		ArrayList<JSONObject> topics = new ArrayList<JSONObject>();
		
		URI request = new URIBuilder()
			.setScheme("http")
			.setHost("api.meetup.com")
			.setPath("/2/topic_categories")
			.setParameter("page", "40")
			.setParameter("key", key)
			.build();
		HttpGet get = new HttpGet(request);			//Assign the URI to the get request
		System.out.println("Get request : "+get.toString());
 
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(get);
		String responseString = EntityUtils.toString(response.getEntity());
		
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(responseString);
		JSONArray results = (JSONArray) obj.get("results");
		JSONObject meta = (JSONObject) obj.get("meta");
		
		Iterator i = results.iterator(); 
		while(i.hasNext()){
			JSONObject topic = (JSONObject) i.next();
			topics.add(topic);
		}
		return topics;


	}

	public ArrayList<JSONObject> getcities() throws Exception{

		ArrayList<JSONObject> cities = new ArrayList<JSONObject>();
		
		URI request = new URIBuilder()
			.setScheme("http")
			.setHost("api.meetup.com")
			.setPath("/2/cities")
			.setParameter("page", "200")
			.setParameter("key", key)
			.build();
		HttpGet get = new HttpGet(request);			//Assign the URI to the get request
		System.out.println("Get request : "+get.toString());
 
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(get);
		String responseString = EntityUtils.toString(response.getEntity());
		
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(responseString);
		JSONArray results = (JSONArray) obj.get("results");
		JSONObject meta = (JSONObject) obj.get("meta");
		
		Iterator i = results.iterator(); 
		while(i.hasNext()){
			JSONObject city = (JSONObject) i.next();
			cities.add(city);
			
		}
		return cities;


	}
 
	public ArrayList <Event> getEvent(City city, Topic topic) throws Exception{
		String responseString = "";
 		
		//Build the request URI
		URI request = new URIBuilder()
			.setScheme("http")
			.setHost("api.meetup.com")
			.setPath("/2/open_events")
			.setParameter("topic", topic.urlkey)
			.setParameter("city", city.city)
			.setParameter("state", city.state)
			.setParameter("country", city.country)
			.setParameter("page", "200")
			.setParameter("key", key)
			.setParameter("time", ",1w")
			.build();
 
		HttpGet get = new HttpGet(request);			//Assign the URI to the get request
		System.out.println("Get request : "+get.toString());
 
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(get);
		responseString = EntityUtils.toString(response.getEntity());

		ArrayList<Event> eventsList = DecodeJSON(responseString);	
		

		return eventsList;
		
	}
 
	
	
	public static ArrayList <Event> DecodeJSON(String responseString){
		ArrayList <Event> eventsList = new ArrayList<Event>();
		try{
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(responseString);
			JSONArray results = (JSONArray) obj.get("results");
			JSONObject meta = (JSONObject) obj.get("meta");
			

			int count = 0;
			Iterator i = results.iterator(); 
			while(i.hasNext()){
				JSONObject event = (JSONObject) i.next();
				Event meetupEvent = new Event(event);
				if(meetupEvent.hasGeoCoordiates() && meetupEvent.yes_rsvp_count>0) {
					count++;
					eventsList.add(meetupEvent);
					

				}
			}
		}
		catch(Exception e){e.printStackTrace();}
		return eventsList;
	}

 
}