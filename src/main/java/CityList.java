package src;


import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;

class City{
/*

Class City

	variable
	--------
	city
	state
	country

*/
	String city;
	String state;
	String country;

	public City(JSONObject cityjson){
		city = (String) cityjson.get("city");
		state = (String) cityjson.get("state");
		country = (String) cityjson.get("country");
	}
	
}

public class CityList{
/*

Class CityList

	variable
	--------
	cities
*/
        ArrayList<City> cities;
        void setList(ArrayList<City> thecities){
            this.cities = thecities;
        }
}