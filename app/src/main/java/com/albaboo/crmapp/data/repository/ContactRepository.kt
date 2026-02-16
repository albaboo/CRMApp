package com.albaboo.crmapp.data.repository

import com.albaboo.crmapp.data.models.Contact
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

// Repositorio para manejar datos de contactos
class ContactRepository {

    // Referencia a Firebase
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.reference.child("contacts")

    // Lista de contactos por defecto (usada solo para cargar los datos)
    private val defaultContacts = listOf(
        Contact(
            name = "Juan Pérez",
            companyName = "Tech Corp",
            email = "juan@tech.com",
            phoneExt = "123",
            phone = "555-0100"
        ),
        Contact(
            name = "María Gómez",
            companyName = "Business Inc",
            email = "maria@business.com",
            phoneExt = "456",
            phone = "555-0200"
        )
    )

    // Obtiene contactos desde Firebase de forma asíncrona
    suspend fun getContacts(): List<Contact> {
        return try {
            val snapshot = reference.get().await()
            if (snapshot.exists()) {
                // Convierte cada elemento en Contact
                val contactsList = snapshot.children.mapNotNull { child ->
                    val contact = child.getValue(Contact::class.java)
                    contact?.copy(id = child.key ?: "")  // agrega ID de Firebase
                }
                contactsList
            } else {
                // Si no hay datos, genera contactos de prueba
                reference.setValue(defaultContacts).await()
                android.util.Log.e("FirebasePregenerate", "Se generaron datos basicos de muestra")
                getContacts() // vuelve a llamar para obtener los nuevos datos
            }
        } catch (e: Exception) {
            android.util.Log.e("FirebaseError", "Error al obtener contactos", e)
            listOf() // devuelve lista vacía si hay excepción
        }
    }

}
