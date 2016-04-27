package org.flamierawieo.anirandom.orm;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.List;

@Entity("users")
public class User {

    @Id
    public ObjectId id;
    public String username;
    public String email;
    public String password;
    public List<String> accessTokens;

    @Reference
    public List<Anime> planToWatchList;

    @Reference
    public List<Anime> completedList;

}
