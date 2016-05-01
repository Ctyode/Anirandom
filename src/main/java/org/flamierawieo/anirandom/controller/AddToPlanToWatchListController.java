package org.flamierawieo.anirandom.controller;

import org.bson.types.ObjectId;
import org.flamierawieo.anirandom.orm.Anime;
import org.flamierawieo.anirandom.orm.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

import static org.flamierawieo.anirandom.Util.jsonify;

@RequestMapping
public class AddToPlanToWatchListController extends BaseController {

    @RequestMapping("/anime/add_to_plan_to_watch_list")
    public String addAnimeToUsersPlanToWatchList(HttpServletRequest request,
                                                 @RequestParam(value = "anime") String animeId) {
        // TODO: can be optimized
        User user = getAuthorizedUser(request);
        if(user != null) {
            Anime anime = datastore.get(Anime.class, new ObjectId(animeId));
            if(anime != null) {
                if(!user.planToWatchList.contains(anime) && !user.completedList.contains(anime)) {
                    datastore.update(user, datastore.createUpdateOperations(User.class).add("planToWatchList", anime));
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
