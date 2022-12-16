package com.example.demo.recommender;

import com.example.demo.entities.ActivityEntity;
import com.example.demo.entities.CommentEntity;
import com.example.demo.entities.JourneyEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.CommentService;
import com.example.demo.services.UserService;
import com.vader.sentiment.analyzer.SentimentAnalyzer;
import com.vader.sentiment.analyzer.SentimentPolarities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JourneyScoreCalculator {

    private final CommentService commentService;
    private final UserService userService;

    /* we should have info about what each user likes in terms of:
        destination features, countries (based on comments and reviews)
        activity types (based on comments) */


    public void analyseCommentsByUser(UserEntity user) {
        List<CommentEntity> comments = commentService.findAllByUser(user);
        Map<String, Double> interests = new HashMap<>();

        for (int commentCounter = 0; commentCounter < comments.size(); commentCounter++) {
            CommentEntity comment = comments.get(commentCounter);

            System.out.println(comment.getContent());
            final SentimentPolarities sentimentPolarities =
                    SentimentAnalyzer.getScoresFor(comment.getContent());
            System.out.println(sentimentPolarities);

            JourneyEntity journey = comment.getJourney();

            // calculate average polarity
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

            for(ActivityEntity activity: journey.getActivities()) {
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
    }

    public double calculateScoreForJourneyForUser(String username) throws IOException {
        double score = 0.0;
        analyseCommentsByUser(userService.findUserByUsername(username));

        return score;
    }
}
