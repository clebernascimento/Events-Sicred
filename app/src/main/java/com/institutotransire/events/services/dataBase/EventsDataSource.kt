package com.institutotransire.events.services.dataBase

import com.institutotransire.events.services.api.EventsController
import com.institutotransire.events.services.model.DetailsEvents
import com.institutotransire.events.services.model.Events
import retrofit2.Call
import retrofit2.Callback

class EventsDataSource : RetrofitConfig() {
    
    private var api: EventsController? = null
    
    fun setApi() {
        api = retrofit.create(EventsController::class.java)
    }

    companion object {
        var instance: EventsDataSource? = null
            get() {
                if (field == null) {
                    field = EventsDataSource()
                }
                return field
            }
            private set
    }

    init {
        setApi()
    }

    fun listEvents(callback: Callback<List<Events>?>) {
        val call = api!!.events()
        call.enqueue(callback)
    }

    fun listEventId(id: Int, callback: Callback<Events>?) {
        val call = api!!.geEvent(id)
        call?.enqueue(callback)
    }

    fun checkin(detailsEvents: DetailsEvents?, callback: Callback<Void?>) {
        val call = api!!.checkin(detailsEvents)
        if (call != null){
            call.enqueue(callback)
        }
    }
}

@JvmName("enqueueT")
private fun <T> Call<T>.enqueue(callback: Callback<List<Events?>?>) {

}

private fun <T> Call<T>?.enqueue(callback: Callback<Void>?) {

}

private fun Any?.enqueue(callback: Callback<Events>?) {
}


