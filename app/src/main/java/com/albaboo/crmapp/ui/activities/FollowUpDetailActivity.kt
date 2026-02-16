package com.albaboo.crmapp.ui.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.albaboo.crmapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Actividad para mostrar detalles de un follow-up
class FollowUpDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_follow_up_detail)
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
        // Obiene datos enviados desde FollowUpListActivity
        val title = intent.getStringExtra("FOLLOWUP_TITLE")
        val type = intent.getStringExtra("FOLLOWUP_TYPE")
        val dateLong = intent.getLongExtra("FOLLOWUP_DATE", 0)
        val location = intent.getStringExtra("FOLLOWUP_LOCATION")
        val description = intent.getStringExtra("FOLLOWUP_DESC")
        // Asigna datos a los TextViews
        findViewById<TextView>(R.id.tvDetailTitle).text = title
        findViewById<TextView>(R.id.tvDetailType).text = type
        findViewById<TextView>(R.id.tvDetailLocation).text = location
        findViewById<TextView>(R.id.tvDetailDescription).text = description
        // Formatea y muestra la fecha si existe
        if (dateLong != 0L) {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            findViewById<TextView>(R.id.tvDetailDate).text = sdf.format(Date(dateLong))
        }
    }
}
