package org.flamierawieo.anirandom.orm.dao;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class BaseDao {

    public static class DaoResponse {

        private boolean success;
        private String info;

        public DaoResponse(boolean success, String info) {
            this.success = success;
            this.info = info;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getInfo() {
            return info;
        }

    }

    public static final Morphia morphia;
    public static final Datastore datastore;

    public static final DaoResponse success = response(true, "nice!");

    static {
        morphia = new Morphia();
        morphia.mapPackage("org.flamierawieo.anirandom.orm.mapping");
        datastore = morphia.createDatastore(new MongoClient(), "anirandom");
        datastore.ensureIndexes();
    }

    public static DaoResponse response(boolean status, String info) {
        return new DaoResponse(status, info);
    }

    public static DaoResponse response(boolean status, Throwable t) {
        return new DaoResponse(status, t.getMessage());
    }

    public static DaoResponse fail(String s) {
        return response(false, s);
    }

    public static DaoResponse fail(Throwable t) {
        return response(false, t);
    }

}
