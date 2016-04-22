package org.flamierawieo.anirandom.controller;

import com.hubspot.jinjava.Jinjava;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.flamierawieo.anirandom.mongo.MongoConfig;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public String render(String template, Map<String, Object> config) {
        Jinjava jinjava = new Jinjava();
        return jinjava.render(template, config);
    }

    public Map<String, Object> getContext(HttpServletRequest request) {
        Map<String, Object> context = new HashMap<>();
        String accessToken = getCookies(request).get("access_token");
        boolean authorized = accessToken != null;
        context.put("authorized", authorized);
        if(authorized) {
            DBCollection dbCollection = MongoConfig.mongoDatabase.getCollection("users");
            DBObject user = dbCollection.findOne(new BasicDBObject("access_tokens", accessToken));
            context.put("username", user.get("username"));
        }
        return context;
    }

    public Map<String, String> getCookies(HttpServletRequest request) {
        Map<String, String> cookies = new HashMap<>();
        Cookie[] requestCookies = request.getCookies();
        if(requestCookies != null) {
            for (Cookie cookie : requestCookies) {
                cookies.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookies;
    }

}
