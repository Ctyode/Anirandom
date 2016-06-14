package org.flamierawieo.anirandom.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.flamierawieo.anirandom.orm.Review;
import org.flamierawieo.anirandom.orm.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class OthersCompleted extends Base {

    private static final String template = "completed.html";

    public Map<String, Object> getContext(HttpServletRequest request, String otherUsername) {
        Map<String, Object> context = super.getContext(request);
        User user = getAuthorizedUser(request);
        User other = datastore.createQuery(User.class).filter("username", otherUsername).get();
        if (other != null && other.completedList != null) {
            context.put("other", true);
            context.put("completed_list", other.completedList.stream().map(Review::toMap).collect(Collectors.toList()));
            context.put("has_unreviewed_animes", other.completedList.stream().filter(a -> a.review == null).findFirst().isPresent());
            context.put("has_reviewed_animes", other.completedList.stream().filter(a -> a.review != null && a.review.length() > 0).findFirst().isPresent());
        }
        return context;
    }

    @RequestMapping("/profile/{username}")
    public String get(@PathVariable("username") String otherUsername,
                      HttpServletRequest request) throws IOException, PebbleException {
        return render(template, getContext(request, otherUsername));
    }


}
