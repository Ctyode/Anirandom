package org.flamierawieo.anirandom.controller;

import org.bson.types.ObjectId;
import org.flamierawieo.anirandom.orm.Anime;
import org.flamierawieo.anirandom.orm.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

import static org.flamierawieo.anirandom.Util.jsonify;

public class AddToCompletedListController extends BaseController {

    @RequestMapping("/anime/add_to_completed_list")
    public String addAnimeToUsersCompletedList(HttpServletRequest request,
                                               @RequestParam(value = "anime") String animeId) {
        // TODO: can be optimized
        User user = getAuthorizedUser(request);
        if(user != null) {
            Anime anime = datastore.get(Anime.class, new ObjectId(animeId));
            if(anime != null) {
                if(!user.completedList.contains(anime) && !user.planToWatchList.contains(anime)) {
                    datastore.update(user, datastore.createUpdateOperations(User.class).add("completedList", anime));
                    return jsonify(new LinkedHashMap() {{
                        put("status", "success");
                        put("info", "nice!");
                    }});
                } else {
                    return jsonify(new LinkedHashMap() {{
                        put("status", "questionable");
                        put("info", "anime is already in user's plan to watch or completed list");
                    }});
                }
            } else {
                return jsonify(new LinkedHashMap() {{
                    put("status", "fail");
                    put("error", "nonexistent anime");
                }});
            }
        } else {
            return jsonify(new LinkedHashMap() {{
                put("status", "fail");
                put("error", "not authorized");
            }});
        }
    }

}
