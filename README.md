# tuesthesis
To do:
* ~~get user (others)~~
* pick a name & a colour palette, make a logo
1. ~~make it run~~
2. update stoplight
3. ~~use the enums~~
4. ~~friends = mutual follows~~
5. searches & filters
6. replies??
7. TESTS
8. security & single sign on with oauth2
9. front end: vue
10. machine learning:
* recommendations based on comments *doing
* based on reviews
* ? follow recommendations
~~data classes for responses~~
* notifications
* user details
* default config like visibility

Pages (front end):
1. user profile (mine and not mine)
2. ~~home - recent journeys~~
3. single journey
4. search
5. ~~log in~~
6. ~~register~~
7. notifications
8. settings
9. not found

How do journeys work?
1. You click "create journey" which automatically sends some data with a post 
request so a journey is created in the db and an id is returned to the frontend
2. You already have an id so you can start adding some activities to the journey
3. You are still on the 'create journey' page, so you can continue adding info that will be sent to the db with a put request
4. When you are done, a final put request will get sent
Finished product: activities, mapped to a journey, using its id.