# tuesthesis
Irina
I am coding a web platform that lets people share journeys with their friends. others can comment on these journeys. I am using kotlin and spring boot. I want to recommend journeys based on people's comments on certain journeys. each journey has a destination, which is a parameter based on which I want to recommend journeys. the sentiment analysis library which i want to use is vader sentiment for java. write the code for the recommendations.
Here is an example of how you could implement a journey recommendation system using Kotlin and the VADER sentiment analysis library:

Copy code
// Import the necessary libraries
import com.github.vader.sentiment.SentimentIntensityAnalyzer

// Define a function that takes a journey and its comments as input
// and returns a list of recommended journeys based on the destination
// and the sentiment of the comments
fun recommendJourneys(journey: Journey, comments: List<Comment>): List<Journey> {
// Create a sentiment analyzer
val sentimentAnalyzer = SentimentIntensityAnalyzer()

    // Compute the sentiment scores for each comment
    val sentimentScores = comments.map { comment ->
        sentimentAnalyzer.polarityScores(comment.text)
    }

    // Compute the average sentiment score for the journey
    val avgSentiment = sentimentScores.reduce { total, score ->
        total + score
    } / sentimentScores.size

    // Return a list of journeys with the same destination and a similar
    // average sentiment score
    return journeys.filter { journey ->
        journey.destination == journey.destination &&
        sentimentSimilarity(journey.avgSentiment, avgSentiment) > 0.5
    }
}
This code uses the VADER sentiment analysis library to compute the sentiment scores for each comment on a journey. It then computes the average sentiment score for the journey, and uses that score to recommend other journeys with the same destination and a similar sentiment score.

Of course, you would need to implement the Journey and Comment classes, as well as the sentimentSimilarity function, for this code to work. This is just a general example of how you could use VADER and Kotlin to implement a journey recommendation system. I hope this helps! Let me know if you have any other questions.



Irina
i want to make recommendations for journeys with characteristics like their destination. what other characteristics can i base my recommendations on
There are many other characteristics that you could use to make recommendations for journeys. Some examples include the duration of the journey, the mode of transportation (e.g. car, train, plane), the time of year, the budget, and the type of activities or experiences offered.

For example, if a user has commented that they enjoyed a journey to a particular destination because it had a lot of outdoor activities, you could recommend other journeys with a similar destination that also have a lot of outdoor activities. Or, if a user has commented that they enjoyed a journey because it was affordable, you could recommend other journeys with a similar destination that are also affordable.

In general, the more characteristics you can use to make recommendations, the more personalized and relevant the recommendations will be for each user. You can experiment with different characteristics and see which ones work best for your particular use case.


Here is an example of how you could use the Google Translate API to translate comments in different languages to English:

Copy code
// Import the necessary libraries
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.google.cloud.translate.Translation

// Define a function that takes a comment and its language as input
// and returns the translated text in English
fun translateComment(comment: String, language: String): String {
// Create a Translate client
val translate = TranslateOptions.getDefaultInstance().service

    // Translate the text from the specified language to English
    val translation: Translation = translate.translate(comment, Translate.TranslateOption.sourceLanguage(language), Translate.TranslateOption.targetLanguage("en"))

    // Return the translated text
    return translation.translatedText
}
This code uses the Google Translate API to translate the text of a comment from the specified language to English. Note that you will need to provide your own Google Cloud API key to use this code. You can find instructions on how to obtain and use an API key in the Google Translate API documentation.

To detect the language of a comment, you could use the Google Cloud Natural Language API. This API can detect the language of a given text, and it is also free to use up to a certain number of characters per day.

Here is an example of how you could use the Google Cloud Natural Language API to detect the language of a comment:

Copy code
// Import the necessary libraries
import com.google.cloud.language.v1.Document
import com.google.cloud.language.v1.LanguageServiceClient
import com.google.cloud.language.v1.Document.Type.PLAIN_TEXT

// Define a function that takes a comment as input and returns the detected language
fun detectCommentLanguage(comment: String): String {
// Create a LanguageServiceClient
val languageServiceClient = LanguageServiceClient.create()

    // Create a Document object
    val document = Document.newBuilder()
        .setContent(comment)
        .setType(PLAIN_TEXT)
        .build()

    // Detect the language of the document
    val response = languageServiceClient.analyzeSentiment(document)
    val language = response.documentSentiment.language

    // Return the detected language
    return language
}
This code uses the Google Cloud Natural Language API to detect the language of a given comment. As with the Google Translate API, you will need to provide your own Google Cloud API key to use this code. You can find instructions on how to obtain and use an API key in the Google Cloud Natural Language API documentation.

I hope this helps! Let me know if you have any other questions.



To do:
* ~~get user (others)~~
* pick a name & a colour palette, make a logo
1. ~~make it run~~
2. update stoplight
3. use the enums
4. friends = mutual follows
5. searches & filters
6. replies??
7. TESTS
8. security & single sign on with oauth2
9. front end: vue
10. machine learning:
* recommendations based on comments
* based on reviews
* ? follow recommendations
~~data classes for responses~~

Pages (front end):
1. user profile (mine and not mine)
2. home - recent journeys
3. single journey
4. search
5. log in
6. register
7. notifications

How do journeys work?
1. You click "create journey" which automatically sends some data with a post 
request so a journey is created in the db and an id is returned to the frontend
2. You already have an id so you can start adding some activities to the journey
3. You are still on the 'create journey' page, so you can continue adding info that will be sent to the db with a put request
4. When you are done, a final put request will get sent
Finished product: activities, mapped to a journey, using its id.