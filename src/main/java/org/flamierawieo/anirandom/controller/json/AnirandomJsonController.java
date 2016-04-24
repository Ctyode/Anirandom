package org.flamierawieo.anirandom.controller.json;

import org.flamierawieo.anirandom.orm.Anime;
import org.mongodb.morphia.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

import static org.flamierawieo.anirandom.mongo.MongoConfig.datastore;

@RestController
public class AnirandomJsonController {

    @RequestMapping("/json/anirandom.json")
    public String handle(@RequestParam(value = "genre", defaultValue = "undefined") String genre,
                         @RequestParam(value = "year", defaultValue = "undefined") String year,
                         @RequestParam(value = "rating", defaultValue = "undefined") String rating) {
        Query query = datastore.createQuery(Anime.class);
        if(!"undefined".equals(rating)) {
            Double r = Double.parseDouble(rating);
            query = query.filter("rating >=", r);
        }
        if(!"undefined".equals(year)) {
            Integer y = Integer.parseInt(year);
            query = query.filter("year >=", y);

        }
        if(!"undefined".equals(genre)) {
            query = query.filter("genres", genre);
        }
        List animeList = query.asList();
        return animeList.size() > 0 ? ((Anime) animeList.get(new Random().nextInt(animeList.size()))).jsonify() : null;
    }

}