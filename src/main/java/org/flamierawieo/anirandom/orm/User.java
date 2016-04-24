package org.flamierawieo.anirandom.orm;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

@Entity("users")
public class User {
    public @Id ObjectId id;
    public String username;
    public String email;
    public String password;
    public List<String> accessTokens;
}
