package org.flamierawieo.anirandom.mongo;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoConfig {

    public static final Morphia morphia;
    public static final Datastore datastore;

    static {
        morphia = new Morphia();
        morphia.mapPackage("org.flamierawieo.anirandom.orm");
        datastore = morphia.createDatastore(new MongoClient(), "anirandom");
        datastore.ensureIndexes();
    }

}
