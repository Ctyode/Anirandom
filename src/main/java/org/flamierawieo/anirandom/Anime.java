package org.flamierawieo.anirandom;

public class Anime {

    private final long id;
    private final String title;

    public Anime(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
