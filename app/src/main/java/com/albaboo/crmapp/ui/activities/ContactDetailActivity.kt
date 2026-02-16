package com.albaboo.crmapp.ui.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.albaboo.crmapp.R

// Actividad para mostrar detalles de un contacto
class ContactDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura toolbar con botón de retorno
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            super.playClick()
            finish()
        }
        // Obtiene datos enviados desde MainActivity
        val name = intent.getStringExtra("CONTACT_NAME")
        val company = intent.getStringExtra("CONTACT_COMPANY")
        val email = intent.getStringExtra("CONTACT_EMAIL")
        val phone = intent.getStringExtra("CONTACT_PHONE")
        // Asigna datos a los TextViews
        findViewById<TextView>(R.id.tvDetailName).text = name
        findViewById<TextView>(R.id.tvDetailCompany).text = company
        findViewById<TextView>(R.id.tvDetailEmail).text = email
        findViewById<TextView>(R.id.tvDetailPhone).text = phone
    }
}
