package src;


import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;

class Topic{

	String name;
	String urlkey;

	/*public Topic(String n, String u){
		name = n;
		urlkey = u;
	}*/

	public Topic(JSONObject topicjson){
		name = (String) topicjson.get("name");
		urlkey = (String) ((JSONObject)topicjson.get("topic")).get("urlkey");

	}
}


public class TopicList{
        ArrayList<Topic> topics;
        void setList(ArrayList<Topic> theTopics){
            this.topics = theTopics;
        }
}