package org.flamierawieo.anirandom.orm.mapping;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Embedded
    public List<Review> completedList;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("_id", id.toHexString());
        map.put("username", username);
        map.put("completedList", completedList);
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

}
