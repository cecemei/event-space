package src;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;

import java.util.logging.Level;

public class Data{

/* 
Store city/topic/event data from MeetupAPIRequest. 

 	Variable 
 	--------
 	Data model: static. Singleton. 
 	Int calllimits: Make the same API request no more than 3 times even if the result is not desirable. 
 	ArrayList<Topic> topics: a list of topics from the API.
 	ArrayList<City> cities: a list of cities from the API. 

 	Method
 	------
 	Data: default constructor.
 	getInstance: static. Singleton. Returns an instance of Data.
 	queryEvents: get a list of events from the API. 
 
 */

	static Data model = null;
	static int calllimits = 3;
	public static ArrayList<Topic> topics = new ArrayList<Topic>();
	public static ArrayList<City> cities = new ArrayList<City>();

	public Data(){
		try{
			MeetupAPIRequest request = MeetupAPIRequest.getInstance();
			int tried = 0;

			topics = new ArrayList<Topic>();
			while((topics.size()==0) && (tried<3)){
				tried+=1;
				ArrayList<JSONObject> topicjson = request.gettopics();
				for(int i=0; i<topicjson.size(); i++){
					Topic t = new Topic(topicjson.get(i));
					topics.add(t);
				}
					
				
			}

			Main.LOGGER.log(Level.INFO, String.format("Total topics: %d\n", topics.size()));

			tried=0;
			cities = new ArrayList<City>();
			if(cities.size()==0&&tried<3){
				tried++;
				ArrayList<JSONObject> cityjson = request.getcities();
				for(int i=0; i<cityjson.size(); i++){
					City c = new City(cityjson.get(i));
					cities.add(c);
				}
					
			}

			Main.LOGGER.log(Level.INFO, String.format("Total cities: %d\n", cities.size()));

		} catch (Exception exc){
			String message = exc.toString()+"\n";
			StackTraceElement[] stack = exc.getStackTrace();

    		for(StackTraceElement e : stack){
    			 message += e.toString()+"\n";
    		}   
    		Main.LOGGER.log(Level.SEVERE,message);
			
		}


	}

	public static Data getInstance(){
		if(model==null){
			model = new Data();
		}
		return model;
	}
	

	public ArrayList <Event> queryEvents(int city_i, int topic_i) throws Exception{

		int tried = 0;
		MeetupAPIRequest request = MeetupAPIRequest.getInstance();
		ArrayList <Event> events = new ArrayList<Event>();

		while(events.size()==0 && tried<3){ 
			events = request.getEvent(cities.get(city_i), topics.get(topic_i));
			tried++;
		}
		return events;

	}

	

	

}