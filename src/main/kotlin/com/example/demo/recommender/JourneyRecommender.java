package com.example.demo.recommender;

import com.example.demo.entities.*;
import com.example.demo.services.CommentService;
import com.example.demo.services.InterestsService;
import com.example.demo.services.JourneyService;
import com.example.demo.services.UserService;
import com.example.demo.types.InterestKeyEntityType;
import com.vader.sentiment.analyzer.SentimentAnalyzer;
import com.vader.sentiment.analyzer.SentimentPolarities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JourneyRecommender {
    private final JourneyService journeyService;

    private final CommentService commentService;
    private final UserService userService;
    private final InterestsService interestsService;

    /* we should have info about what each user likes in terms of:
        destination features, countries (based on comments and reviews)
        activity types (based on comments) */


    public void analyseCommentsByUser(UserEntity user) {
        List<CommentEntity> comments = new ArrayList<>();
        // find the id of the last comment that was analysed
        try {
             long lastCommentId = interestsService.findFirstByUserOrderByLastCommentIdDesc(user).getLastCommentId();
             comments = commentService.findAllByUserAndIdGreaterThan(user, lastCommentId);
        } catch (NullPointerException e) {
            // there are no saved interests for the user. therefore all of his comments have to be analysed
            comments = commentService.findAllByUser(user);
        }

        for (CommentEntity comment : comments) {
            System.out.println(comment.getContent());
            final SentimentPolarities sentimentPolarities =
                    SentimentAnalyzer.getScoresFor(comment.getContent());
            System.out.println(sentimentPolarities);

            JourneyEntity journey = comment.getJourney();

            // calculate average compound polarity. According to the Vader Sentiment documentation:
//            positive sentiment: compound score >= 0.05
//            neutral sentiment: (compound score > -0.05) and (compound score < 0.05)
//            negative sentiment: compound score <= -0.05

            // look into destination: features and country
            try {
                // calculate the score for the country
                interestsService.calculateInterest(user, journey.getDestination().getCountry().getCountryCode(),
                        InterestKeyEntityType.COUNTRY,
                        sentimentPolarities.getCompoundPolarity(), comment.getId());
            } catch (NullPointerException e) {
                // the destination does not have a country (it is ok)
            }

            try {
                interestsService.calculateInterest(user, journey.getDestination().getFeatureCode(),
                        InterestKeyEntityType.FEATURE_CODE,
                        sentimentPolarities.getCompoundPolarity(), comment.getId());
            } catch (NullPointerException e) {
                // the destination does not have a feature code (it is ok)
            }

            try {
                interestsService.calculateInterest(user, journey.getDestination().getFeatureClass(),
                        InterestKeyEntityType.FEATURE_CLASS,
                        sentimentPolarities.getCompoundPolarity(), comment.getId());
            } catch (NullPointerException e) {
                // the destination does not have a feature class (it is ok)
            }


            for (ActivityEntity activity : journey.getActivities()) {

                try {
                    interestsService.calculateInterest(user, activity.getType().name(),
                            InterestKeyEntityType.ACTIVITY,
                            sentimentPolarities.getCompoundPolarity(), comment.getId());
                } catch (NullPointerException e) {
                    // no activity type
                }

            }
        }

//        Map<String, Double> interests = new HashMap<>();
//
//        System.out.println("Interests map: ");
//        for(Map.Entry<String, Double> interest: interests.entrySet()) {
//            System.out.println(interest.getKey() + ": " + interest.getValue());
//        }
//        return interests;
    }

    public Map<JourneyEntity, Double> recommendForUser(String username) {
        UserEntity user = userService.findUserByUsername(username);
        Map<JourneyEntity, Double> recommendationsWithScores = new HashMap<>();
        analyseCommentsByUser(user);
        // find journeys that are not created by this user and are accessible by him
        assert user != null;

        List<JourneyEntity> journeys = journeyService.findAllVisibleByUserAndNotByUser(user);

        // find scores for journeys
        for (JourneyEntity journey: journeys) {
            double score = 0.0;
            // for each journey characteristic, add the corresponding compound score from the interests map
            try {
                score += interestsService.findByKeyAndEntityAndUser(
                        journey.getDestination().getCountry().getCountryCode(), InterestKeyEntityType.COUNTRY, user
                ).getValue();
            } catch (NullPointerException ignored) {}

            try {
                score += interestsService.findByKeyAndEntityAndUser(
                        journey.getDestination().getFeatureClass(), InterestKeyEntityType.FEATURE_CLASS, user
                ).getValue();
            } catch (NullPointerException ignored) {}

            try {
                score += interestsService.findByKeyAndEntityAndUser(
                        journey.getDestination().getFeatureCode(), InterestKeyEntityType.FEATURE_CODE, user
                ).getValue();
            } catch (NullPointerException ignored) {}

            for (ActivityEntity activity: journey.getActivities()) {

                try {
                    score += interestsService.findByKeyAndEntityAndUser(
                            activity.getType().name(), InterestKeyEntityType.ACTIVITY, user
                    ).getValue();
                } catch (NullPointerException ignored) {}
            }
            recommendationsWithScores.put(journey, score);
            System.out.println(journey.getId() + ": " + score);
        }

        return recommendationsWithScores;
    }
}
