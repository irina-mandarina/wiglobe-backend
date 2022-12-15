package com.example.demo.recommender;

//import com.example.demo.entities.JourneyEntity;
import com.vader.sentiment.analyzer.SentimentAnalyzer;
import com.vader.sentiment.analyzer.SentimentPolarities;

import java.io.IOException;
import java.util.ArrayList;

public class JourneyScoreCalculator {

    /* we should have info about what each user likes in terms of:
        destination features, countries (based on comments and reviews)
        activity types (based on comments) */
    ArrayList<String> sentences = new ArrayList<String>() {{
        add("VADER is smart, handsome, and funny.");
        add("VADER is smart, handsome, and funny!");
        add("VADER is very smart, handsome, and funny.");
        add("VADER is VERY SMART, handsome, and FUNNY.");
        add("VADER is VERY SMART, handsome, and FUNNY!!!");
        add("VADER is VERY SMART, really handsome, and INCREDIBLY FUNNY!!!");
        add("The book was good.");
        add("The book was kind of good.");
        add("The plot was good, but the characters are uncompelling and the dialog is not great.");
        add("A really bad, horrible book.");
        add("At least it isn't a horrible book.");
        add(":) and :D");
        add("");
        add("Today sux");
        add("Today sux!");
        add("Today SUX!");
        add("Today kinda sux! But I'll get by, lol");
    }};

    public double calculateScoreForJourney() throws IOException {
        double score = 0.0;
        for (String sentence : sentences) {
            System.out.println(sentence);
            final SentimentPolarities sentimentPolarities =
                    SentimentAnalyzer.getScoresFor(sentence);
            System.out.println(sentimentPolarities);
        }

        return score;
    }
}
