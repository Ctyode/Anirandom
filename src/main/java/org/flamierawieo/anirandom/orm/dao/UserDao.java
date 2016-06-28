package org.flamierawieo.anirandom.orm.dao;

import org.flamierawieo.anirandom.orm.mapping.Anime;
import org.flamierawieo.anirandom.orm.mapping.Review;
import org.flamierawieo.anirandom.orm.mapping.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserDao extends BaseDao {

    public User getUserByAccessToken(String accessToken) {
        return datastore.createQuery(User.class).filter("accessTokens", accessToken).get();
    }

    public User getUserByUsername(String username) {
        return datastore.createQuery(User.class).filter("username", username).get();
    }

    public DaoResponse createUser(User user) {
        if(datastore.createQuery(User.class)
                .filter("username", user.username)
                .filter("email", user.email).asList().size() == 0) {
            datastore.save(user);
            return success;
        } else {
            return fail("already exists");
        }
    }

    public DaoResponse addAccessToken(User user, String accessToken) {
        try {
            datastore.update(user,
                    datastore.createUpdateOperations(User.class)
                            .add("accessTokens", accessToken));
            return success;
        } catch(Throwable t) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "", t);
            return fail(t);
        }
    }

    public DaoResponse addToPlanToWatch(User user, Anime anime) {
        try {
            if(user.planToWatchList != null &&
                    user.planToWatchList.contains(anime)) {
                return response(false, "info is already in users plan to watch list");
            }
            Review testReview = new Review();
            testReview.anime = anime;
            if(user.completedList != null &&
                    user.completedList.contains(testReview)) {
                return response(false, "info is already in users completed list");
            }
            List<Anime> newPlanToWatchList = user.planToWatchList;
            if(newPlanToWatchList == null) {
                newPlanToWatchList = new ArrayList<>();
            }
            newPlanToWatchList.add(anime);
            datastore.update(user,
                    datastore.createUpdateOperations(User.class)
                        .set("planToWatchList", newPlanToWatchList));
            return success;
        } catch(Throwable t) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "", t);
            return fail(t);
        }
    }

    public DaoResponse removeFromPlanToWatch(User user, Anime anime) {
        try {
            datastore.update(user,
                    datastore.createUpdateOperations(User.class)
                            .set("planToWatchList", user.planToWatchList
                                    .stream()
                                    .filter(a -> !a.equals(anime))
                                    .collect(Collectors.toList())));
            return success;
        } catch(Throwable t) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "", t);
            return fail(t);
        }
    }

    public DaoResponse addToCompleted(User user, Anime anime) {
        try {
            Review review = new Review();
            review.anime = anime;
            if(user.planToWatchList != null &&
                    user.planToWatchList.contains(anime)) {
                return fail("Anime is already in users plan to watch list");
            }
            if(user.completedList != null &&
                    user.completedList.contains(review)) {
                return fail("Anime is already in users completed list");
            }
            List<Review> newCompletedList = user.completedList;
            if(newCompletedList == null) {
                newCompletedList = new ArrayList<>();
            }
            newCompletedList.add(review);
            datastore.update(user, datastore.createUpdateOperations(User.class)
                    .set("completedList", newCompletedList));
            return success;
        } catch(Throwable t) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "", t);
            return fail(t);
        }
    }

    public DaoResponse moveToCompleted(User user, Anime anime) {
        try {
            Review review = new Review();
            if(user.completedList == null) {
                user.completedList = new ArrayList<>();
            }
            List<Review> reviews = user.completedList
                    .stream()
                    .filter(r -> !r.anime.equals(anime))
                    .collect(Collectors.toList());
            review.anime = anime;
            reviews.add(review);
            datastore.update(user,
                    datastore.createUpdateOperations(User.class)
                            .set("planToWatchList", user.planToWatchList
                                    .stream()
                                    .filter(a -> !a.equals(anime))
                                    .collect(Collectors.toList()))
                            .set("completedList", reviews));
            return success;
        } catch(Throwable t) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "", t);
            return fail(t);
        }
    }

    public DaoResponse removeFromCompleted(User user, Anime anime) {
        try {
            List<Review> newCompletedList = user.completedList
                    .stream()
                    .filter(r -> !r.anime.equals(anime))
                    .collect(Collectors.toList());
            datastore.update(user, datastore.createUpdateOperations(User.class)
                    .set("completedList", newCompletedList));
            return success;
        } catch(Throwable t) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "", t);
            return fail(t);
        }
    }

    public DaoResponse editCompleted(User user, Review review) {
        try {
            List<Review> reviews = user.completedList
                    .stream()
                    .filter(r -> !r.anime.equals(review.anime))
                    .collect(Collectors.toList());
            reviews.add(review);
            datastore.update(user, datastore.createUpdateOperations(User.class).set("completedList", reviews));
            return success;
        } catch(Throwable t) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "", t);
            return fail(t);
        }
    }

}
