# tuesthesis

Qs:
1. on delete cascade

To do:
* ~~get user (others)~~
* ~~country codes~~
* ~~pick a name~~ & ~~a colour palette~~, make a logo
1. ~~make it run~~
2. update stoplight
3. ~~use the enums~~
4. ~~friends = mutual follows~~
5. searches & filters
6. replies??
7. TESTS (in the end)
8. security & single sign on with oauth2
9. front end: vue
10. add posted on for journeys
11. machine learning:
~~* recommendations based on comments~~
* based on reviews
* ? follow recommendations (based on mutual friends)
~~data classes for responses~~
* notifications
* ~~user details~~
* default config like visibility (settings)

Front end to do list:
1. ~~finish follow requests~~
2. ~~search destinations~~
3. ~~journey adder - post to drafts~~
4. activity adder - add more
5. ~~comments under journeys~~
6. ~~single journey make it look ok + add activity carousel~~
7. ~~reviews under single destination~~
8. ~~comment adder~~
9. ~~review adder~~
10. finish user profile
11. settings
12. not found
13. ~~notifications (after impl in the backend)~~
14. explore - destinations, search, trending journeys
15. ~~vuelidate / vee-validate~~
16. notification pop ups
17. application config
18. axios interceptors
19. journey manager

Recommendations:
* cosine similarity: https://stackoverflow.com/questions/520241/how-do-i-calculate-the-cosine-similarity-of-two-vectors
* normalise the compound scores of each user using the total of their comments
* DB!!

https://sylhare.github.io/2021/07/19/Openapi-swagger-codegen-with-kotlin.html

must haves:
~~interests - add type~~
1. ~~post journey~~
2. save user profile from google
3. ~~lock user profiles~~
4. ~~repair get journeys by user (not showing drafts)~~
5. ~~jwt~~
6. ~~security: checks for username there~~
7. ~~notifications~~
8. ~~recommendations of destinations~~
9. ~~view journeys by destination~~
10. ~~countries for dest recs~~
11. ~~fix controller tests (esp delete controllers)~~
13. ~~post drafts, edit journeys in the frontend~~
14. ~~update fontawesome~~
15. delete reviews
16. tests for services
18. documentation
22. encode passwords

Future improvements (good to have):
* elastic search db for destinations
* collaborative recommendations
* reactions with pictures of faces - then analyse the emotion using ml
* filters
* follow recommendations
* future j list
* projection for user names when searching
22. rec: weight of params

to do:
private images, backend, db on azure

  C:\Program Files\MySQL\MySQL Server 8.0\bin>.\mysql.exe -h wiglobe-db.mysql.database.azure.com -u irinaahamad -p wiglobedb < "C:\\Users\\irina\\demodbdump.sql"

mvn spring-boot:run

IMPORTANT VALUE FROM APP.PROP FOR GOOGLE CLIENT ID
NOTIFICATINO CONSTRUCTOR

1. limit comment lenght in fronend
2. https
3. scr main java
4. dont throw exceptions in recommender
5. average rating in difff func


qs:
1. can we reset the free tier usage? we want to have a 1 month free Azure Database for MySQL flexible server
2. can we use a mysql database for more than a month?