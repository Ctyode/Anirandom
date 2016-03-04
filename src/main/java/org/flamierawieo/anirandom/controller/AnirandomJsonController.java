package org.flamierawieo.anirandom.controller;

import org.flamierawieo.anirandom.Anime;
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
import java.util.stream.Collectors;

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
                animePool.add(new Anime(title, synopsis, image, rating, genres));
            });
        } catch (IOException | ParseException e) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, "", e);
        }
    }

    private static Anime anirandom(Set<Anime> pool) {
        int size = pool.size();
        int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for(Anime obj : pool) {
            if (i == item)
                return obj;
            i = i + 1;
        }
        return null; // ??
    }

    @RequestMapping("/anirandom.json")
    public Anime handle(@RequestParam(value = "genre", defaultValue = "undefined") String genre,
                        @RequestParam(value = "year", defaultValue = "undefined") String year,
                        @RequestParam(value = "rating", defaultValue = "undefined") String rating) {
        Set<Anime> pool = new HashSet<>(animePool);
        if(!"undefined".equals(genre)) {
            pool = pool.stream().filter(a -> a.getGenres().contains(genre)).collect(Collectors.toSet());
        }
        if(!"undefined".equals(rating)) {
            Double r = Double.parseDouble(rating);
            pool = pool.stream().filter(a -> a.getRating() >= r).collect(Collectors.toSet());
        }
        return anirandom(pool);
    }

}