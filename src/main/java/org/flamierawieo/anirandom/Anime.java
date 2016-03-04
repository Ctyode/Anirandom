package org.flamierawieo.anirandom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Anime {

    private String title;
    private String synopsis;
    private String image;
    private Double rating;
    private List<String> genres;

    public Anime(String title, String synopsis, String image, Double rating, Collection<String> genres) {
        this.title = title;
        this.synopsis = synopsis;
        this.image = image;
        this.rating = rating;
        this.genres = new ArrayList<>(genres);
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

    public List<String> getGenres() {
        return genres;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && title.equals(((Anime) o).title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    public Anime merge(Anime o) {
        if(equals(o)) {
            List<String> genres = new ArrayList<>();
            this.genres.stream().filter(genre -> !genres.contains(genre)).forEach(genres::add);
            o.getGenres().stream().filter(genre -> !genres.contains(genre)).forEach(genres::add);
            return new Anime(title, synopsis, image, rating, genres);
        } else {
            throw new RuntimeException("Not equals");
        }
    }

}
