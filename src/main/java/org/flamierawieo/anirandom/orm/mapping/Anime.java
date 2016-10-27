package org.flamierawieo.anirandom.orm.mapping;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity("animes")
public class Anime {

    @Id
    public ObjectId id;
    public String myAnimeListLink;
    public String title;
    public String synopsis;
    public String image;
    public Double rating;
    public Integer year;
    public List<String> genres;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("_id", id.toHexString());
        map.put("title", title);
        map.put("myAnimeListLink", myAnimeListLink);
        map.put("synopsis", synopsis);
        map.put("image", image);
        map.put("rating", rating);
        map.put("year", year);
        map.put("genres", genres);
        return map;
    }

    public String jsonify() {
        return JSONObject.toJSONString(toMap());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Anime anime = (Anime) o;

        return id != null ? id.equals(anime.id) : anime.id == null;

    }

}
