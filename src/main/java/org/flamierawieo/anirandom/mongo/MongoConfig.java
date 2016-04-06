package org.flamierawieo.anirandom.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoConfig {

    public static final MongoClient mongoClient = new MongoClient();
    public static final DB mongoDatabase = mongoClient.getDB("anirandom");

}
