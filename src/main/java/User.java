package src;

import java.util.ArrayList;

public class User {
/*

User class

	variable
	--------
	String name
	City city
	String email
	ArrayList<Topic> followedtopics

	method
	------
	User: constructor. 

*/

	public String name;
	public City city;
	String email;
	public ArrayList<Topic> followedtopics = new ArrayList<Topic>();

	public User(String username, City currentcity){
		name = username;
		city = currentcity;
	}

	public User(String username){
		name = username;
		
	}

	public User(String username, String theEmail){
		name = username;
		email = theEmail;
	}
}