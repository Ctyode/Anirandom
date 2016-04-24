package org.flamierawieo.anirandom.controller;

import com.hubspot.jinjava.Jinjava;
import org.flamierawieo.anirandom.orm.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.flamierawieo.anirandom.mongo.MongoConfig.datastore;

public class BaseController {

    public String render(String template, Map<String, Object> config) {
        Jinjava jinjava = new Jinjava();
        return jinjava.render(template, config);
    }

    public Map<String, Object> getContext(HttpServletRequest request) {
        Map<String, Object> context = new HashMap<>();
        String accessToken = getCookies(request).get("access_token");
        boolean tryingToAuthorize = accessToken != null;
        if(tryingToAuthorize) {
            List<User> queryList = datastore.createQuery(User.class).filter("accessTokens", accessToken).asList();
            if(queryList.size() > 0) {
                context.put("authorized", true);
                context.put("username", queryList.get(0).username); // i guess??
            } else {
                context.put("authorized", false);
            }
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
