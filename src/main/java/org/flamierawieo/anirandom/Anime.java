package org.flamierawieo.anirandom;

public class Anime {

    private final String title;
    private final String synopsis;
    private final String image;
    private final double score;

    public Anime(String title, String synopsis, String image, double score) {
        this.title = title;
        this.synopsis = synopsis;
        this.image = image;
        this.score = score;
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

    public double getScore() {
        return score;
    }

}
