package org.flamierawieo.anirandom.orm;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.LinkedHashMap;
import java.util.Map;

public class Review {

    @Id
    public ObjectId id;
    @Reference
    public Anime anime;
    public String review;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("_id", id.toHexString());
        map.put("anime", anime.toMap());
        map.put("review", review);
        return map;
    }

}
