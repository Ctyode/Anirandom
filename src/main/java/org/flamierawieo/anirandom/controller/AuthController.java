package org.flamierawieo.anirandom.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.flamierawieo.anirandom.mongo.MongoConfig;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthController {

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public void handle(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("back") String back,
                       HttpServletResponse response) {
        System.out.println("peedor " + username + " " + password);
        try {
            response.sendRedirect(back);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        DBCollection dbCollection = MongoConfig.mongoDatabase.getCollection("users");
//        BasicDBObject filter = new BasicDBObject();
//        filter.append("username", username);
//        DBObject user = dbCollection.findOne(filter);
//
//        // TODO: SHA512 PBKDF2 HMAC hashing x-d-d-d-d-d
//        if(user != null && user.get("password").equals(password)) {
//            response.addCookie(new Cookie("accesstoken", "8344531aa151a97f714c695bb1f1f8ee"));
//            response.setHeader("Location", "/");
//        } else {
//            response.setHeader("Location", back);
//        }
    }

}
