package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.flamierawieo.anirandom.orm.Review;
import org.flamierawieo.anirandom.orm.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UsersCompleted extends Base {

    private static String template;

    static {
//        try {
            template = "completed.html";
//            template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/completed.html")), "UTF-8");
//        } catch (IOException e) {
//            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, "", e);
//            template = "Can someone unfuck the situation, please?";
//        }
    }

    @Override
    public Map<String, Object> getContext(HttpServletRequest request) {
        Map<String, Object> context = super.getContext(request);
        User user = getAuthorizedUser(request);
        if(user != null && user.completedList != null) {
            context.put("completed_list", user.completedList.stream().map(Review::toMap).collect(Collectors.toList()));
            context.put("has_unreviewed_animes", user.completedList.stream().filter(a -> a.review == null).findFirst().isPresent());
            context.put("has_reviewed_animes", user.completedList.stream().filter(a -> a.review != null && a.review.length() > 0).findFirst().isPresent());
        }
        return context;
    }

    @RequestMapping("/completed")
    public String get(HttpServletRequest request) throws IOException, PebbleException {
        return render(template, getContext(request));
    }

}
