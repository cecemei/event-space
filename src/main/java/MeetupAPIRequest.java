package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.net.URI; 

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.logging.Level;

public class MeetupAPIRequest {
 /* 

 Construct url to fetch city/topic/event data from meet up API. 

 	Variable 
 	--------
 	MeetupAPIRequest instance: static. Singleton. 
 	String key: api key. 

 	Method
 	------
 	MeetupAPIRequest: default constructor. 
 	getInstance: static. Singleton. Returns an instance of MeetupApiRequest.
 	badResult: Check httpresponse header if the status code is not 200/OK. Returns Void.
 	gettopics: Make API request, get a list of topics. Returns ArrayList<Topic>. 
 	getcities: Make API request, get a list of cities. Returns ArrayList<City>.
 	getEvent: Make API request, get a list of events. Return ArrayList<Event>.

 */
 	private static MeetupAPIRequest instance = null;
	
	 
	
	private static String key = 
	"6d75526a39297e167b781f4c3c6b3c";//"6f47431b44364721e382101e244613";

	MeetupAPIRequest(){}

	public static MeetupAPIRequest getInstance(){
		if(instance == null){
			instance = new MeetupAPIRequest();
		}
		return instance;
	}

	public void badResult (int statusCode, Header[] headers) throws Exception{
		if(statusCode==429){     //too many request
			for(Header h:headers){
				if(h.getName().equals("X-RateLimit-Reset")){
					int reset= Integer.parseInt(h.getValue());
					Thread.sleep(reset);
				}
			}
		} else {
			System.out.printf("API error with response code %d\n", statusCode);
			Main.LOGGER.log(Level.SEVERE, String.format("API error with response code %d\n", statusCode));
			System.exit(0);
		}
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
		//System.out.println("Get request : "+get.toString());
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(get);

		Main.LOGGER.log(Level.INFO, "Get request : "+get.toString());
 		Main.LOGGER.log(Level.INFO, "Response : "+response.toString());

		int code = response.getStatusLine().getStatusCode();
		if(code==200){
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
			
		} else {
			Header[] headers = response.getAllHeaders();
			this.badResult(code, headers);
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
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(get);
		
		Main.LOGGER.log(Level.INFO, "Get request : "+get.toString());
		Main.LOGGER.log(Level.INFO, "Response : "+response.toString());

		int code = response.getStatusLine().getStatusCode();
		if(code==200){
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
		} else {
			Header[] headers = response.getAllHeaders();
			this.badResult(code, headers);
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
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(get);

		Main.LOGGER.log(Level.INFO, "Get request : "+get.toString());
		Main.LOGGER.log(Level.INFO, "Response : "+response.toString());

		int code = response.getStatusLine().getStatusCode();
		ArrayList<Event> eventsList = new ArrayList<Event>();
		if(code==200){
			responseString = EntityUtils.toString(response.getEntity());
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(responseString);
			JSONArray results = (JSONArray) obj.get("results");
			JSONObject meta = (JSONObject) obj.get("meta");
			
			Iterator i = results.iterator(); 
			while(i.hasNext()){
				JSONObject event = (JSONObject) i.next();
				Event meetupEvent = new Event(event);
				if(meetupEvent.hasGeoCoordiates() && meetupEvent.yes_rsvp_count>0) {
					eventsList.add(meetupEvent);
				}
			}
		} else {
			Header[] headers = response.getAllHeaders();
			this.badResult(code, headers);
		}	

		return eventsList;
	}
 
	
 
}