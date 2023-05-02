package com.example.demo.recommender

import com.example.demo.entities.ActivityEntity
import com.example.demo.entities.CommentEntity
import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import com.example.demo.services.CommentService
import com.example.demo.services.InterestsService
import com.example.demo.services.JourneyService
import com.example.demo.services.UserService
import com.example.demo.types.InterestKeyEntityType
import com.vader.sentiment.analyzer.SentimentAnalyzer
import com.vader.sentiment.analyzer.SentimentPolarities
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class JourneyRecommender(private val journeyService: JourneyService, private val commentService: CommentService,
                         private val userService: UserService, private val interestsService: InterestsService) {

    /* we should have info about what each user likes in terms of:
        destination features, countries (based on comments and reviews)
        activity types (based on comments) */
    private fun analyseCommentsByUser(user: UserEntity) {
        // find the id of the last comment that was analysed
        val comments: List<CommentEntity?>

        val lastInterest = interestsService.findFirstByUserOrderByLastCommentIdDesc(user)

        if (lastInterest == null){
            // there are no saved interests for the user. therefore all of his comments have to be analysed
            comments = commentService.findAllByUser(user)
        }
        else {
            comments = commentService.findAllByUserAndIdGreaterThan(user, lastInterest.lastCommentId)
        }

        for (comment in comments) {
            val sentimentPolarities: SentimentPolarities = SentimentAnalyzer.getScoresFor(comment.content)
            val journey: JourneyEntity = comment.journey

            // calculate average compound polarity. According to the Vader Sentiment documentation:
//            positive sentiment: compound score >= 0.05
//            neutral sentiment: (compound score > -0.05) and (compound score < 0.05)
//            negative sentiment: compound score <= -0.05

            // look into destination: features and country
            if (journey.destination != null && journey.destination!!.country != null) {
                interestsService.calculateInterest(
                    user, journey.destination!!.country!!.countryCode,
                    InterestKeyEntityType.COUNTRY,
                    sentimentPolarities.compoundPolarity.toDouble(), comment.id
                )
            }

            if (journey.destination != null && journey.destination!!.featureCode.isNotEmpty()) {
                interestsService.calculateInterest(
                    user, journey.destination!!.featureCode,
                    InterestKeyEntityType.FEATURE_CODE,
                    sentimentPolarities.compoundPolarity.toDouble(), comment.id
                )
            }

            if (journey.destination != null && journey.destination!!.featureClass.isNotEmpty()) {
                interestsService.calculateInterest(
                    user, journey.destination!!.featureClass,
                    InterestKeyEntityType.FEATURE_CLASS,
                    sentimentPolarities.compoundPolarity.toDouble(), comment.id
                )
            }

            for (activity in journey.activities) {
                if (activity.type != null) {
                    interestsService.calculateInterest(
                        user, activity.type.toString(),
                        InterestKeyEntityType.ACTIVITY,
                        sentimentPolarities.compoundPolarity.toDouble(), comment.id
                    )
                }
            }
        }
    }

    fun recommendJourneysToUser(username: String): Map<JourneyEntity, Double> {
        val user: UserEntity = userService.findUserByUsername(username)!!
        var recommendationsWithScores: HashMap<JourneyEntity, Double> = HashMap()
        analyseCommentsByUser(user)
        val journeys: List<JourneyEntity> = journeyService.findAllVisibleByUserAndNotByUser(user)

        // find scores for journeys
        for (journey in journeys) {
            recommendationsWithScores[journey] = calculateJourneyPolarityScoreForUser(journey, user)
        }
        return recommendationsWithScores
    }

    private fun calculateJourneyPolarityScoreForUser(journey: JourneyEntity, user: UserEntity): Double {
        var score = 0.0
        // for each journey characteristic, add the corresponding compound score from the interests map
        if (journey.destination != null) {
            if (journey.destination!!.country != null) {
                val interest = interestsService.findByKeyAndEntityAndUser(
                        journey.destination!!.country!!.countryCode, InterestKeyEntityType.COUNTRY, user
                )
                if (interest != null) {
                    score += interest.value
                    interestsService.decreaseInterest(interest)
                }
            }

            if (journey.destination!!.featureClass.isNotEmpty()) {
                val interest = interestsService.findByKeyAndEntityAndUser(
                        journey.destination!!.featureClass, InterestKeyEntityType.FEATURE_CLASS, user
                )
                if (interest != null) {
                    score += interest.value
                    interestsService.decreaseInterest(interest)
                }
            }

            if (journey.destination!!.featureCode.isNotEmpty()) {
                val interest = interestsService.findByKeyAndEntityAndUser(
                        journey.destination!!.featureCode, InterestKeyEntityType.FEATURE_CODE, user
                )
                if (interest != null) {
                    score += interest.value
                    interestsService.decreaseInterest(interest)
                }
            }

        }
        for (activity in journey.activities) {
            if (activity.type != null) {
                val interest = interestsService.findByKeyAndEntityAndUser(
                        activity.type.toString(), InterestKeyEntityType.ACTIVITY, user
                )
                if (interest != null) {
                    score += interest.value
                    interestsService.decreaseInterest(interest)
                }
            }
        }
        return score
    }
}