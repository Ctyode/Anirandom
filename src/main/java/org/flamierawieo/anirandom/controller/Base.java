package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.FileLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.MongoClient;
import org.flamierawieo.anirandom.orm.mapping.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Base {

    public static final Morphia morphia;
    public static final Datastore datastore;

    private static FileLoader loader;
    private static PebbleEngine engine;
    private static Map<String, PebbleTemplate> templateCache;

    static {
        morphia = new Morphia();
        morphia.mapPackage("org.flamierawieo.anirandom.orm");
        datastore = morphia.createDatastore(new MongoClient(), "anirandom");
        datastore.ensureIndexes();

        loader = new FileLoader();
        loader.setPrefix("src/main/resources/templates/");
        engine = new PebbleEngine.Builder().loader(loader).build();
        templateCache = new HashMap<>();

    }

    public String render(String templateName, Map<String, Object> context) throws PebbleException, IOException {
        PebbleTemplate template = templateCache.get(templateName);
        if(template == null) {
            template = engine.getTemplate(templateName);
            templateCache.put(templateName, template);
        }
        Writer writer = new StringWriter();
        template.evaluate(writer, context);
        return writer.toString();
    }

    public User getAuthorizedUser(HttpServletRequest request) {
        Map<String, String> cookies = getCookies(request);
        if(cookies.containsKey("access_token")) {
            return datastore.createQuery(User.class).filter("accessTokens", cookies.get("access_token")).get();
        } else {
            return null;
        }
    }

    public Map<String, Object> getContext(HttpServletRequest request) {
        Map<String, Object> context = new HashMap<>();
        User user = getAuthorizedUser(request);
        if(user != null) {
            context.put("authorized", true);
            context.put("username", user.username);
        } else {
            context.put("authorized", false);
        }
        return context;
    }

    public Map<String, String> getCookies(HttpServletRequest request) {
        Map<String, String> cookies = new HashMap<>();
        Cookie[] requestCookies = request.getCookies();
        if (requestCookies != null) {
            for (Cookie cookie : requestCookies) {
                if(cookie.getValue() != null) {
                    cookies.put(cookie.getName(), cookie.getValue());
                }
            }
        }
        return cookies;
    }

}
