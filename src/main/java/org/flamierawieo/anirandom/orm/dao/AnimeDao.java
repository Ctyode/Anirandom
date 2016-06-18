package org.flamierawieo.anirandom.orm.dao;

import org.bson.types.ObjectId;
import org.flamierawieo.anirandom.orm.mapping.Anime;
import org.mongodb.morphia.query.Query;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class AnimeDao extends BaseDao {

    public Anime getAnimeById(ObjectId objectId) {
        return datastore.get(Anime.class, objectId);
    }

    public Anime getRandomAnime(String genre, Integer year, Double rating) {
        Query<Anime> query = datastore.createQuery(Anime.class);
        if(rating != null) {
            query = query.filter("rating >=", rating);
        }
        if(year != null) {
            query = query.filter("year >=", year);
        }
        if(genre != null) {
            query = query.filter("genres", genre);
        }
        List<Anime> animeList = query.asList();
        if(animeList.size() > 0) {
            return animeList.get(new Random().nextInt(animeList.size()));
        } else {
            return null;
        }
    }

    public List<Anime> searchAnimes(Pattern p) {
        Query<Anime> q = datastore.createQuery(Anime.class).filter("title", p);
        return q.asList();
    }

}
