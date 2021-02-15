package com.institutotransire.events.services.model

import lombok.Data
import java.io.Serializable

@Data
data class Events (
    var id: Int?,
    var title: String?,
    var date: Long = 0,
    var image: String?,
    var description: String?,
    var longitude: Double?,
    var latitude: Double?,
    var price: Double?
) : Serializable