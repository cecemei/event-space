package src;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Controller
public class TheController {
/*
Configuration. Redirect. 
*/
    @RequestMapping(value="/")
    public String greeting() {
        return "home";
    }
}

@Configuration
class TheResourceHandler extends WebMvcConfigurerAdapter {
/*
Configuration. Redirect. 
*/
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        
        registry.addRedirectViewController("/home", "/home.html");
        
    }

}