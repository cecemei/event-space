package src;


import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;

public class City{
	String city;
	String state;
	String country;

	public City(JSONObject cityjson){
		city = (String) cityjson.get("city");
		state = (String) cityjson.get("state");
		country = (String) cityjson.get("country");
	}
	
}