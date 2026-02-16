package com.albaboo.crmapp.data.repository

import com.albaboo.crmapp.data.models.FollowUp
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.util.Date

// Repositorio para manejar datos de follow-ups
class FollowUpRepository {

    // Referencia a Firebase
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.reference.child("follow_ups")
    // Lista de follow-ups por defecto (usada si Firebase está vacío)
    private val defaultFollowUps = listOf(
        FollowUp(
            title = "Reunión inicial",
            type = "Presentación",
            contactId =  "1",
            date = Date(),
            location = "Oficina",
            description = "Presentar producto"
        ),
        FollowUp(
            title = "Seguimiento",
            type = "Llamada",
            contactId = "2",
            date = Date(),
            location = "Virtual",
            description = "Confirmar interés"
        )
    )

    // Obtiene follow-ups desde Firebase de forma asíncrona
    suspend fun getFollowUps(): List<FollowUp> {
        return try {
            val snapshot = reference.get().await()
            if (snapshot.exists()) {
                // Si hay datos, convierte cada elemento en FollowUp
                val contactsList = snapshot.children.mapNotNull { child ->
                    val contact = child.getValue(FollowUp::class.java)
                    contact?.copy(id = child.key ?: "") // agrega ID de Firebase
                }
                contactsList
            } else {
                // Si no hay datos, genera seguimientos de prueba
                reference.setValue(defaultFollowUps).await()
                android.util.Log.e("FirebasePregenerate", "Se generaron datos basicos de muestra")
                getFollowUps() // vuelve a llamar para obtener los nuevos datos
            }
        } catch (e: Exception) {
            android.util.Log.e("FirebaseError", "Error al obtener seguimientos", e)
            listOf() // devuelvo lista vacía si hay excepción
        }
    }
}