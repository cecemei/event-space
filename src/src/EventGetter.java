package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;



public class EventGetter {
 
 
	String city = "";
	public String topic = "";
	String radius = "25.0";
	String order = "distance";
 
	public String getEvent(String path_code,String key) throws Exception{
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

		return responseString;
	}
 
	public String getApiKey(){
		String key = "a22572e5c3b65611273533a12321fb";
		return key;																//Return the key value.
	}
	
 
}