package src;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class TheRestController {
/*
The rest controller class.

variable
--------
Data model
GsonBuilder builder
Gson gson

method
------
greeting: url="user", method=GET
register: url="user", method=POST 
getCities: url="cities", method=GET
getTopics: url="topics", method=GET
getEvents: url="events", method=GET

*/
    private static Data model = Data.getInstance();
    private static GsonBuilder builder = new GsonBuilder();
    private static Gson gson = builder.create();
        

    @RequestMapping(value="/user", method=RequestMethod.GET)
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return name;
    }
    
    @RequestMapping(value="/user", method=RequestMethod.POST)
    public User register(@RequestParam(value="username") String name) {
        System.out.println(name);
        return new User(name);
    }

    

    @RequestMapping(value="/cities", method=RequestMethod.GET)
    public String getCities() {
        ArrayList<City> cities = model.cities;
        CityList citylist = new CityList();
        citylist.setList(cities);
        return gson.toJson(citylist);
    }

    @RequestMapping(value="/topics", method=RequestMethod.GET)
    public String getTopics() {
        ArrayList<Topic> topics = model.topics;
        TopicList topiclist = new TopicList();
        topiclist.setList(topics);
        return gson.toJson(topiclist);
    }

    @RequestMapping(value="/events", method=RequestMethod.GET)
    public String getEvents(@RequestParam(value="city_i") int city_i, @RequestParam(value="topic_i") int topic_i ) throws Exception{
        ArrayList<Event> events = model.queryEvents(city_i, topic_i); 
        EventList eventlist = new EventList();
        eventlist.setList(events);
        return gson.toJson(eventlist);
    }



}