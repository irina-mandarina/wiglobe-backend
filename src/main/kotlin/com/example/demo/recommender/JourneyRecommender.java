package com.example.demo.recommender;

import com.example.demo.entities.ActivityEntity;
import com.example.demo.entities.CommentEntity;
import com.example.demo.entities.JourneyEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.models.responseModels.Activity;
import com.example.demo.models.responseModels.Destination;
import com.example.demo.models.responseModels.Journey;
import com.example.demo.models.responseModels.UserNames;
import com.example.demo.services.CommentService;
import com.example.demo.services.JourneyService;
import com.example.demo.services.UserService;
import com.vader.sentiment.analyzer.SentimentAnalyzer;
import com.vader.sentiment.analyzer.SentimentPolarities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JourneyRecommender {
    private final JourneyService journeyService;

    private final CommentService commentService;
    private final UserService userService;

    /* we should have info about what each user likes in terms of:
        destination features, countries (based on comments and reviews)
        activity types (based on comments) */


    public Map<String, Double> analyseCommentsByUser(UserEntity user) {
        List<CommentEntity> comments = commentService.findAllByUser(user);
        Map<String, Double> interests = new HashMap<>();

        for (int commentCounter = 0; commentCounter < comments.size(); commentCounter++) {
            CommentEntity comment = comments.get(commentCounter);

            System.out.println(comment.getContent());
            final SentimentPolarities sentimentPolarities =
                    SentimentAnalyzer.getScoresFor(comment.getContent());
            System.out.println(sentimentPolarities);

            JourneyEntity journey = comment.getJourney();

            // calculate average compound polarity. According to the Vader Sentiment documentation:
//            positive sentiment: compound score >= 0.05
//            neutral sentiment: (compound score > -0.05) and (compound score < 0.05)
//            negative sentiment: compound score <= -0.05
            if (interests.containsKey(journey.getDestination().getCountryCode())) {
                interests.put( journey.getDestination().getCountryCode(),
                        (interests.get(journey.getDestination().getCountryCode())*commentCounter + sentimentPolarities.getCompoundPolarity()) / (commentCounter+1) );
            }
            else {
                interests.put( journey.getDestination().getCountryCode(),
                        (double) sentimentPolarities.getCompoundPolarity());
            }

            if (interests.containsKey(journey.getDestination().getFeatureCode())) {
                interests.put( journey.getDestination().getFeatureCode(),
                        (interests.get(journey.getDestination().getFeatureCode())*commentCounter + sentimentPolarities.getCompoundPolarity()) / (commentCounter+1) );
            }
            else {
                interests.put( journey.getDestination().getFeatureCode(),
                        (double) sentimentPolarities.getCompoundPolarity());
            }

            if (interests.containsKey(journey.getDestination().getFeatureClass())) {
                interests.put( journey.getDestination().getFeatureClass(),
                        (interests.get(journey.getDestination().getFeatureClass())*commentCounter + sentimentPolarities.getCompoundPolarity()) / (commentCounter+1) );
            }
            else {
                interests.put( journey.getDestination().getFeatureClass(),
                        (double) sentimentPolarities.getCompoundPolarity());
            }

            if (interests.containsKey(journey.getDestination().getFeatureClass())) {
                interests.put( journey.getDestination().getFeatureClass(),
                        (interests.get(journey.getDestination().getFeatureClass())*commentCounter + sentimentPolarities.getCompoundPolarity()) / (commentCounter+1) );
            }
            else {
                interests.put( journey.getDestination().getFeatureClass(),
                        (double) sentimentPolarities.getCompoundPolarity());
            }

            for (ActivityEntity activity: journey.getActivities()) {
                if ( interests.containsKey(activity.getType().name()) ) {
                    interests.put( activity.getType().name(),
                            (interests.get(activity.getType().name())*commentCounter + sentimentPolarities.getCompoundPolarity()) / (commentCounter+1) );
                }
                else {
                    interests.put( activity.getType().name(),
                            (double) sentimentPolarities.getCompoundPolarity() );
                }
            }
        }

        System.out.println("Interests map: ");
        for(Map.Entry<String, Double> interest: interests.entrySet()) {
            System.out.println(interest.getKey() + ": " + interest.getValue());
        }
        return interests;
    }

//    public double calculateScoreForJourneyForUser(UserEntity user, JourneyEntity journey) throws IOException {
//        double score = 0.0;
//        analyseCommentsByUser(user);
//
//        return score;
//    }

    public List<Journey> recommendForUser(String username) {
        UserEntity user = userService.findUserByUsername(username);
        Map<JourneyEntity, Double> recommendationsWithScores = new HashMap<>();
        Map<String, Double> interests = analyseCommentsByUser(user);
        // find journeys that are not created by this user
        assert user != null;
        List<JourneyEntity> journeys = journeyService.findJourneyEntitiesByUserNot(user);
        // find scores for journeys
        for (JourneyEntity journey: journeys) {
            double score = 0.0;
            // for each journey characteristic, add the corresponding compound score from the interests map
            if (interests.containsKey( journey.getDestination().getCountryCode()) ) {
                score += interests.get( journey.getDestination().getCountryCode() );
            }
            if (interests.containsKey( journey.getDestination().getFeatureClass()) ) {
                score += interests.get( journey.getDestination().getFeatureClass() );
            }
            if (interests.containsKey( journey.getDestination().getFeatureCode()) ) {
                score += interests.get( journey.getDestination().getFeatureCode() );
            }
            for (ActivityEntity activity: journey.getActivities()) {
                if ( interests.containsKey(activity.getType().name()) ) {
                    score += interests.get( activity.getType().name() );
                }
            }
            recommendationsWithScores.put(journey, score);
            System.out.println(journey.getId() + ": " + score);
        }

        //recommendationsWithScores = (Map<JourneyEntity, Double>) recommendationsWithScores.entrySet().stream().sorted(Map.Entry.<JourneyEntity, Double>comparingByValue());

        List<Journey> recs = null;
        for(Map.Entry<JourneyEntity, Double> recommendation: recommendationsWithScores.entrySet()) {
            System.out.println(recommendation.getKey().getId() + ": " + recommendation.getValue());
            List<Activity> activities = null;
            for (ActivityEntity activity: recommendation.getKey().getActivities()) {
                activities.add(new Activity(
                        activity.getId(),
                        activity.getTitle(),
                        activity.getDescription(),
                        activity.getType(),
                        activity.getDate(),
                        activity.getLocation()
                ));
            }
            recs.add(new Journey(
                    recommendation.getKey().getId(),
                    new UserNames(
                            recommendation.getKey().getUser().getUsername(),
                            recommendation.getKey().getUser().getFirstName(),
                            recommendation.getKey().getUser().getLastName()
                    ),
                    recommendation.getKey().getStartDate(),
                    recommendation.getKey().getEndDate(),
                    recommendation.getKey().getDescription(),
                    new Destination(
                            recommendation.getKey().getDestination().getLatitude(),
                            recommendation.getKey().getDestination().getLongitude(),
                            recommendation.getKey().getDestination().getName(),
                            recommendation.getKey().getDestination().getCountryCode(),
                            recommendation.getKey().getDestination().getFeatureClass(),
                            recommendation.getKey().getDestination().getFeatureCode()
                    ),
                    activities,
                    recommendation.getKey().getVisibility()
            ));
        }

        return recs;
    }
}
