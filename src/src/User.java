package src;

import java.util.ArrayList;
import java.util.Iterator;

public class User {
	public String name;
	public City city;
	public ArrayList<Topic> followedtopics = new ArrayList<Topic>();

	public User(String username, City currentcity){
		name = username;
		city = currentcity;
	}
}