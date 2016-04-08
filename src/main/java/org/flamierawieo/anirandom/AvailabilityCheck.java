package org.flamierawieo.anirandom;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.flamierawieo.anirandom.mongo.MongoConfig;

public class AvailabilityCheck {

    public boolean availabilityCheck(String username, String email) {
        DBCollection dbCollection = MongoConfig.mongoDatabase.getCollection("users");
        BasicDBObject usernameFilter = new BasicDBObject();
        BasicDBObject emailFilter = new BasicDBObject();
        usernameFilter.append("username", username);
        emailFilter.append("email", email);
        DBCursor usernameCursor = dbCollection.find(usernameFilter);
        DBCursor emailCursor = dbCollection.find(emailFilter);
        return usernameCursor.count() == 0 && emailCursor.count() == 0;
    }

}
