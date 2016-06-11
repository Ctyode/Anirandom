package org.flamierawieo.anirandom.controller;

import org.bson.types.ObjectId;
import org.flamierawieo.anirandom.orm.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static org.flamierawieo.anirandom.Util.jsonify;

@RestController
public class RemoveFromCompleted extends Base {

    @RequestMapping("/anime/remove_from_completed")
    public String get(HttpServletRequest request,
                      @RequestParam(value = "anime") String animeId) {
        User user = getAuthorizedUser(request);
        if(user != null) {
            ObjectId animeObjectId = new ObjectId(animeId);
            datastore.update(user, datastore.createUpdateOperations(User.class).set("completedList", user.completedList.stream()
                                            .filter(r -> !r.anime.id.equals(animeObjectId)).collect(Collectors.toList())));
            return jsonify(new LinkedHashMap() {{
                put("status", "success");
                put("info", "nice!");
            }});
        } else {
            return jsonify(new LinkedHashMap() {{
                put("status", "fail");
                put("error", "not authorized");
            }});
        }
    }

}
