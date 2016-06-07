package org.flamierawieo.anirandom.controller;

import org.flamierawieo.anirandom.orm.Anime;
import org.mongodb.morphia.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.flamierawieo.anirandom.Util.jsonify;

@RestController
public class SearchController extends BaseController {

    @RequestMapping("/search")
    public String get(@RequestParam(value = "s") String searchString) {
        if(searchString.length() < 3) {
            return "";
        }
        Pattern regexp = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
        Query q = datastore.createQuery(Anime.class).filter("title", regexp);
        System.out.println(q.asList());
        List<Anime> animes = q.asList();
        return jsonify(new HashMap() {{
            put("results", animes.stream().map(Anime::toMap).collect(Collectors.toList()));
        }});
    }

}
