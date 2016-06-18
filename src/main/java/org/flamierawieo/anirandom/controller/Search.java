package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.flamierawieo.anirandom.orm.dao.AnimeDao;
import org.flamierawieo.anirandom.orm.mapping.Anime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.flamierawieo.anirandom.Util.jsonify;

@RestController
public class Search extends Base {

    @RequestMapping("/search.json")
    public String json(@RequestParam(value = "s") String searchString) {
        if(searchString.length() < 3) {
            return jsonify(new HashMap() {{
                put("results", new ArrayList<>());
            }});
        }
        Pattern p = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
        List<Anime> animes = new AnimeDao().searchAnimes(p);
        return jsonify(new HashMap() {{
            put("results", animes.stream().map(Anime::toMap).collect(Collectors.toList()));
        }});
    }

    @RequestMapping("/search")
    public String get(HttpServletRequest request) throws IOException, PebbleException {
        return render("search.html", getContext(request));
    }

}
