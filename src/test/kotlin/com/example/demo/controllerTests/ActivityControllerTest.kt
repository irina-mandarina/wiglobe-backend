package com.example.demo.controllerTests

import com.example.demo.controllers.ActivityController
import com.example.demo.models.requestModels.ActivityRequest
import com.example.demo.models.responseModels.Activity
import com.example.demo.services.ActivityService
import com.example.demo.types.ActivityType
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import kotlin.test.assertNotNull
import kotlin.test.assertSame

class ActivityControllerTest {

    val activityService = mockkClass(ActivityService::class)
    val activityController = ActivityController(activityService)
    val sampleUsername = "kiril"
    val sampleJourneyId = 1L
    val sampleActivityId = 1L
    @Test
    fun activityServiceAddsActivityToJourney_WhenAddActivityToJourneyCalled_ActivityAddedToJourney() {
        // GIVEN:
        val sampleActivityReq = mockkClass(ActivityRequest::class)
        every {
            activityService.addActivityToJourney(sampleUsername, sampleActivityReq, sampleJourneyId)
        } returns ResponseEntity.ok().body(
            mockkClass(Activity::class)
        )

        // WHEN:
        val responseFromController = activityController.addActivityToJourney(sampleUsername, sampleActivityReq, sampleJourneyId)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, activityService.addActivityToJourney(sampleUsername, sampleActivityReq, sampleJourneyId))
    }

    @Test
    fun activityServiceEditsActivityForJourney_WhenEditJourneyForActivityCalled_ActivityEdited() {
        // GIVEN:
        val sampleActivityReq = mockkClass(ActivityRequest::class)
        every {
            activityService.editActivityForJourney(sampleUsername, sampleActivityReq, sampleJourneyId, sampleActivityId)
        } returns ResponseEntity.ok().body(
            mockkClass(Activity::class)
        )

        // WHEN:
        val responseFromController = activityController.editActivityForJourney(sampleUsername, sampleActivityReq, sampleJourneyId, sampleActivityId)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, activityService.editActivityForJourney(sampleUsername, sampleActivityReq, sampleJourneyId, sampleActivityId))
    }

    @Test
    fun activityServiceDeletesActivityFromJourney_WhenDeleteJourneyFromActivityCalled_ActivityDeleted() {
        // GIVEN:
        every {
            activityService.deleteActivityFromJourney(sampleUsername, sampleJourneyId, sampleActivityId)
        } returns ResponseEntity.ok().body(
            null
        )

        // WHEN:
        val responseFromController = activityController.deleteActivityFromJourney(sampleUsername, sampleJourneyId, sampleActivityId)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, activityService.deleteActivityFromJourney(sampleUsername, sampleJourneyId, sampleActivityId))
    }

    @Test
    fun activityServiceReturnsActivitiesForJourney_WhenGetActivitiesFromJourneyCalled_ActivitiesReturned() {
        // GIVEN:
        every {
            activityService.getActivitiesForJourney(sampleUsername, sampleJourneyId)
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(Activity::class)
            )
        )

        // WHEN:
        val responseFromController = activityController.getActivitiesForJourney(sampleUsername, sampleJourneyId)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, activityService.getActivitiesForJourney(sampleUsername, sampleJourneyId))
    }

    @Test
    fun activityServiceReturnsAllActivityTypes_WhenGetAllActivityTypesCalled_AllActivityTypesReturned() {
        // GIVEN:
        every {
            activityService.getAllActivityTypes()
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(ActivityType::class)
            )
        )

        // WHEN:
        val responseFromController = activityController.getAllActivityTypes()

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, activityService.getAllActivityTypes())
    }
}