package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.flamierawieo.anirandom.orm.dao.AnimeDao;
import org.flamierawieo.anirandom.orm.mapping.Anime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
        AnimeDao animeDao = new AnimeDao();
        String sGenre = null;
        Integer iYear = null;
        Double dRating = null;
        if(!"undefined".equals(rating)) {
            dRating = Double.parseDouble(rating);
        }
        if(!"undefined".equals(year)) {
            iYear = Integer.parseInt(year);
        }
        if(!"undefined".equals(genre)) {
            sGenre = genre;
        }
        Anime anime = animeDao.getRandomAnime(sGenre, iYear, dRating);
        if(anime != null) {
            return anime.jsonify();
        } else {
            return null;
        }
    }

}