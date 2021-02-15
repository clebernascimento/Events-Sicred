package com.institutotransire.events.services.api

import com.institutotransire.events.services.model.DetailsEvents
import com.institutotransire.events.services.model.Events
import retrofit2.Call
import retrofit2.http.*

interface EventsController {
    @GET("/api/events")
    fun events(): Call<List<Events>>

    @GET("/api/events/{id}")
    fun geEvent(
            @Path("id") id: Int
    ): Call<Events?>?

    @POST("/checkin")
    fun checkin(
            @Body detailsEvents: DetailsEvents?): Call<Void?>?
}