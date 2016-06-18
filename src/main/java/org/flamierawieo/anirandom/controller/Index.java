package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.flamierawieo.anirandom.orm.mapping.Anime;
import org.mongodb.morphia.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
public class Index extends Base {

    @RequestMapping("/")
    public String get(HttpServletRequest request) throws IOException, PebbleException {
        return render("index.html", getContext(request));
    }

    @RequestMapping("/json/anirandom.json")
    public String getRandomAnime(@RequestParam(value = "genre", defaultValue = "undefined") String genre,
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