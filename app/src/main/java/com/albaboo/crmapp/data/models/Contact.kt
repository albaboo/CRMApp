package com.albaboo.crmapp.data.models

// Modelo de datos para un contacto
data class Contact(
    var id: String = "",
    var name: String = "",
    var companyName: String = "",
    var email: String = "",
    var phoneExt: String = "",
    var phone: String = ""
) {

    // Convierte un Contact en un Map para guardar en Firebase
    fun toMap(): Map<String, Any> = mapOf(
        "name" to name,
        "companyName" to companyName,
        "email" to email,
        "phoneExt" to phoneExt,
        "phone" to phone
    )

    companion object {
        // Crea un Contact desde un Map (recuperado de Firebase)
        fun fromMap(id: String = "", data: Map<String, Any>): Contact = Contact(
            id = id,
            name = data["name"] as? String ?: "",
            companyName = data["companyName"] as? String ?: "",
            email = data["email"] as? String ?: "",
            phoneExt = data["phoneExt"] as? String ?: "",
            phone = data["phone"] as? String ?: ""
        )
    }
}