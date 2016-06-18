package org.flamierawieo.anirandom.orm.mapping;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

import java.util.LinkedHashMap;
import java.util.Map;

@Embedded
public class Review {

    @Reference
    public Anime anime;
    public String review;
    public int rating;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("anime", anime.toMap());
        map.put("review", review);
        map.put("rating", rating);
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        return anime != null ? anime.equals(review.anime) : review.anime == null;

    }

}
