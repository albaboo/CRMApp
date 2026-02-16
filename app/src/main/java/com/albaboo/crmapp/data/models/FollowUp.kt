package com.albaboo.crmapp.data.models

import com.google.firebase.Timestamp
import java.util.Date
import kotlin.to

// Modelo de datos para un seguimiento (follow-up)
data class FollowUp(var id: String = "",
                    var title: String = "",
                    var type: String = "",
                    var contactId: String = "",
                    var date: Date = Date(),
                    var location: String = "",
                    var description: String = ""
) {

    // Convierte FollowUp en Map para guardar en Firebase
    fun toMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "type" to type,
            "contact" to contactId,
            "date" to Timestamp(date),
            "location" to location,
            "description" to description
        )
    }

    companion object {
        // Crea un FollowUp desde un Map (recuperado de Firebase)
        fun fromFirestore(documentId: String, data: Map<String, Any>): FollowUp {
            return FollowUp(
                id = documentId,
                title = data["title"] as? String ?: "",
                type = data["type"] as? String ?: "",
                date = (data["date"] as? Timestamp)?.toDate() ?: Date(),
                location = data["location"] as? String ?: "",
                description = data["description"] as? String ?: "",
                contactId = data["contactId"] as? String ?: ""
            )
        }
    }
}
