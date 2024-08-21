package com.appicenter.services.TutorialService;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/tutorials")
public class TutorialController {

    @Autowired
    EurekaClient eurekaClient;

    @GetMapping("/users")
    public String getUsers() {
        //String usersUrl = "http://localhost:8080/users";

        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("UserService", true);
        String usersUrl  = instanceInfo.getHomePageUrl()+"/users";

        RestTemplate restTemplate = new RestTemplate();
        String users = restTemplate.getForObject(usersUrl, String.class);

        return "The Users are :" + users;
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable Integer userId) {
        //String usersUrl = "http://localhost:8080/users/" + userId;

        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("UserService", true);
        String usersUrl  = instanceInfo.getHomePageUrl()+"/users/"+userId;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(usersUrl, User.class);
    }
}

