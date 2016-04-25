package org.flamierawieo.anirandom;

import org.json.simple.JSONObject;

import java.util.Map;

public class Util {

    public static String jsonify(Map map) {
        return JSONObject.toJSONString(map);
    }

}
