package src;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;

public class Model{

	static Model model = null;
	static int calllimits = 3;
	public static ArrayList<Topic> topics = null;
	public static ArrayList<City> cities = null;

	public Model(){
		
		MeetupAPIRequest request = MeetupAPIRequest.getInstance();
		int tried = 0;

		topics = new ArrayList<Topic>();
		while((topics.size()==0) && (tried<3)){
			tried+=1;
			try{
				ArrayList<JSONObject> topicjson = request.gettopics();
				for(int i=0; i<topicjson.size(); i++){
					Topic t = new Topic(topicjson.get(i));
					topics.add(t);
				}
				
			} catch(Exception e){e.printStackTrace();}
		}

		System.out.printf("Total topics: %d\n", topics.size());

		tried=0;
		cities = new ArrayList<City>();
		if(cities.size()==0&&tried<3){
			tried++;
			try{
				ArrayList<JSONObject> cityjson = request.getcities();
				for(int i=0; i<cityjson.size(); i++){
					City c = new City(cityjson.get(i));
					cities.add(c);
				}
				
			} catch(Exception e){e.printStackTrace();}
		}

		System.out.printf("Total cities: %d\n", cities.size());


	}

	public User createUser() throws Exception{
		Scanner sc = new Scanner(System.in);

		String name = "";
		while(name==""){
			System.out.print("Enter your name: ");
			name = sc.nextLine();
		}

		int city_i=-1;
		while(city_i<0 || city_i>=cities.size()){
			try{
				System.out.print("Enter your city: ");
				printcities();
				city_i = sc.nextInt();
			} catch(Exception e){}
		}

		User user = new User(name, cities.get(city_i));
		return user;



	}

	public ArrayList <Event> getEvent() throws Exception{

		Scanner sc = new Scanner(System.in);
		int tried = 0;

		
		int city_i = -1;
		while(city_i<0 || city_i>=cities.size()){
			try{
				System.out.print("Choose the city: ");
				printcities();
				city_i = sc.nextInt();
				 
			} catch(Exception e){}
		}

		int topic_i = -1;
		while(topic_i<0 || topic_i>=topics.size() ){
			try{
				System.out.print("Choose the topic: ");
				printtopics();
				topic_i = sc.nextInt();
				 
			}catch(Exception e){}
		}

		MeetupAPIRequest request = MeetupAPIRequest.getInstance();
		ArrayList <Event> events = new ArrayList<Event>();

		while(events.size()==0 && tried<3){ 
			events = request.getEvent(cities.get(city_i), topics.get(topic_i));
			tried++;
		}
		return events;


	}

	public ArrayList <Event> getEvent(City city) throws Exception{

		Scanner sc = new Scanner(System.in);

		int topic_i = -1;
		
		while(topic_i<0 || topic_i>=topics.size() ){
			try{
				System.out.print("Choose the topic: ");
				printtopics();
				topic_i = sc.nextInt();
				 
			} catch(Exception e){}
		}

		MeetupAPIRequest request = MeetupAPIRequest.getInstance();
		ArrayList <Event> events = new ArrayList<Event>();
		int tried = 0;


		while(events.size()==0 && tried<3){ 
			events = request.getEvent(city, topics.get(topic_i));
			tried++;
		}
		return events;


	}

	public static Model getInstance(){
		if(model==null){
			model = new Model();
		}
		return model;
	}

	

	public void printtopics(){
		for(int i=0; i<topics.size(); i++){
			Topic topic = topics.get(i);
			System.out.print((i)+"-"+topic.name+"  ");
		}
		System.out.println();
	}

	public void printcities(){
		for(int i=0; i<cities.size(); i++){
			City city = cities.get(i);
			System.out.print((i)+"-"+city.city+"  ");
		}
		System.out.println();
	}

}