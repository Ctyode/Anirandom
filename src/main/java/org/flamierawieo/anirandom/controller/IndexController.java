package org.flamierawieo.anirandom.controller;

import com.hubspot.jinjava.Jinjava;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.flamierawieo.anirandom.mongo.MongoConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class IndexController {

    private static String template;

    static {
        try {
            template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/index.html")), "UTF-8");
        } catch (IOException e) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, "", e);
            template = "Can someone unfuck the situation, please?";
        }
    }

    @RequestMapping("/")
    public String handle(HttpServletRequest request) {
        Jinjava jinjava = new Jinjava();
        Map<String, Object> context = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                }
            }
        }
        boolean authorized = accessToken != null;
        context.put("authorized", authorized);
        if(authorized) {
            DBCollection dbCollection = MongoConfig.mongoDatabase.getCollection("users");
            DBObject user = dbCollection.findOne(new BasicDBObject("access_tokens", accessToken));
            context.put("username", user.get("username"));
        }
        return jinjava.render(template, context);
    }

}