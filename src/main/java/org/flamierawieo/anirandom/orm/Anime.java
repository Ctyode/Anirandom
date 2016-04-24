package org.flamierawieo.anirandom.orm;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

@Entity("animes")
public class Anime {

    @Id
    private ObjectId id;
    private String title;
    private String synopsis;
    private String image;
    private Double rating;
    private Integer year;
    private List<String> genres;

    public ObjectId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getImage() {
        return image;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getYear() {
        return year;
    }

    public List<String> getGenres() {
        return genres;
    }

}
