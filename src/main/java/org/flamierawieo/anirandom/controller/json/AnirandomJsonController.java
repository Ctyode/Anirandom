package org.flamierawieo.anirandom.controller.json;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.flamierawieo.anirandom.Anime;
import org.flamierawieo.anirandom.controller.IndexController;
import org.flamierawieo.anirandom.mongo.MongoConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class AnirandomJsonController {

    private static Set<Anime> animePool;

    static {
        animePool = new HashSet<>();
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/data.json"));
            jsonArray.forEach(data -> {
                JSONObject jsonObject = (JSONObject) data;
                String title = (String) jsonObject.get("title");
                String synopsis = (String) jsonObject.get("synopsis");
                String image = (String) jsonObject.get("image");
                JSONArray genres = (JSONArray) jsonObject.get("genres");
                Double rating = (Double) jsonObject.get("rating");
                Integer year = ((Long) jsonObject.get("year")).intValue();
                animePool.add(new Anime(title, synopsis, image, rating, year, genres));
            });
        } catch (IOException | ParseException e) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, "", e);
        }
    }

    @RequestMapping("/json/anirandom.json")
    public Anime handle(@RequestParam(value = "genre", defaultValue = "undefined") String genre,
                        @RequestParam(value = "year", defaultValue = "undefined") String year,
                        @RequestParam(value = "rating", defaultValue = "undefined") String rating) {
        DBCollection dbCollection = MongoConfig.mongoDatabase.getCollection("animes");
        BasicDBObject filter = new BasicDBObject();
        if(!"undefined".equals(rating)) {
            Double r = Double.parseDouble(rating);
            filter.append("rating", new BasicDBObject("$gt", r));
        }
        if(!"undefined".equals(year)) {
            Integer y = Integer.parseInt(year);
            filter.append("year", new BasicDBObject("$gt", y));
        }
        if(!"undefined".equals(genre)) {
            filter.append("genres", genre);
        }
        DBCursor cursor = dbCollection.find(filter);
        if(cursor.count() > 0) {
            cursor.skip(new Random().nextInt(cursor.count()));
        } else {
            return null;
        }
        return Anime.generateByDocument(cursor.next());
    }

}