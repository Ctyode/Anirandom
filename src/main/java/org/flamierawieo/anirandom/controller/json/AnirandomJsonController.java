package org.flamierawieo.anirandom.controller.json;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.flamierawieo.anirandom.controller.IndexController;
import org.flamierawieo.anirandom.mongo.MongoConfig;
import org.flamierawieo.anirandom.orm.Anime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mongodb.morphia.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class AnirandomJsonController {

//    private static Set<Anime> animePool;

//    static {
//        animePool = new HashSet<>();
//        try {
//            JSONParser parser = new JSONParser();
//            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/data.json"));
//            jsonArray.forEach(data -> {
//                JSONObject jsonObject = (JSONObject) data;
//                String title = (String) jsonObject.get("title");
//                String synopsis = (String) jsonObject.get("synopsis");
//                String image = (String) jsonObject.get("image");
//                JSONArray genres = (JSONArray) jsonObject.get("genres");
//                Double rating = (Double) jsonObject.get("rating");
//                Integer year = ((Long) jsonObject.get("year")).intValue();
//                animePool.add(new Anime(title, synopsis, image, rating, year, genres));
//            });
//        } catch (IOException | ParseException e) {
//            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, "", e);
//        }
//    }

    @RequestMapping("/json/anirandom.json")
    public String handle(@RequestParam(value = "genre", defaultValue = "undefined") String genre,
                         @RequestParam(value = "year", defaultValue = "undefined") String year,
                         @RequestParam(value = "rating", defaultValue = "undefined") String rating) {
//        DBCollection dbCollection = MongoConfig.mongoDatabase.getCollection("animes");
//        BasicDBObject filter = new BasicDBObject();
        Query query = MongoConfig.datastore.createQuery(Anime.class);
        if(!"undefined".equals(rating)) {
            Double r = Double.parseDouble(rating);
//            filter.append("rating", new BasicDBObject("$gt", r));
            query = query.filter("rating>=", r);
        }
        if(!"undefined".equals(year)) {
            Integer y = Integer.parseInt(year);
//            filter.append("year", new BasicDBObject("$gt", y));
            query = query.filter("year>=", y);

        }
        if(!"undefined".equals(genre)) {
//            filter.append("genres", genre);
            query = query.filter("genre", genre);
        }
//        DBCursor cursor = dbCollection.find(filter);
        List<Anime> animeList = query.asList();
        Anime anime = null;
        if(animeList.size() > 0) {
//            cursor.skip(new Random().nextInt(cursor.count()));
            anime = animeList.get(new Random().nextInt(animeList.size()));
        }
        if(anime != null) {
            Map map = new LinkedHashMap<>();
            map.put("_id", anime.getId().toHexString());
            map.put("title", anime.getTitle());
            map.put("synopsis", anime.getSynopsis());
            map.put("image", anime.getImage());
            map.put("rating", anime.getRating());
            map.put("year", anime.getYear());
            map.put("genres", anime.getGenres());
            return JSONObject.toJSONString(map);
        } else {
            return null;
        }
//        return JSONObject.toJSONString(cursor.next().toMap());
    }

}