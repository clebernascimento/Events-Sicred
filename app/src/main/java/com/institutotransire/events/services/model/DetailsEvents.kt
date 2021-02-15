package com.institutotransire.events.services.model

import lombok.AllArgsConstructor
import lombok.Data

@Data
@AllArgsConstructor
data class DetailsEvents(
        private var eventId: Int.Companion,
        private var name: String?,
        private var email: String?
)