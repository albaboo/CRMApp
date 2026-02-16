package com.albaboo.crmapp.ui.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.albaboo.crmapp.R
import com.albaboo.crmapp.data.models.FollowUp
import com.albaboo.crmapp.data.repository.FollowUpRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

// Actividad para mostrar la lista de seguimientos
class FollowUpListActivity : BaseActivity() {

    private lateinit var cardsContainer: LinearLayout
    private lateinit var bottomNav: BottomNavigationView

    // Repositorio para obtener los follow-ups
    private val repository = FollowUpRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_follow_up_list)

        // Inicializa vistas
        cardsContainer = findViewById(R.id.followUpContainer)
        setSupportActionBar(findViewById(R.id.toolbar))
        bottomNav = findViewById(R.id.bottomNavigation)

        setupBottomNavigation()
        refreshUI()
    }

    override fun onRestart() {
        super.onRestart()
        refreshUI() // refresca UI al volver
    }

    // Actualiza la interfaz con los follow-ups
    private fun refreshUI() {
        bottomNav.selectedItemId = R.id.nav_followUps
        lifecycleScope.launch {
            val followUps = repository.getFollowUps()
            displayFollowUps(followUps)
        }
    }

    // Configura la navegación inferior
    private fun setupBottomNavigation() {
        val colorStateList = android.content.res.ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(getColor(R.color.primary), getColor(R.color.secondary))
        )
        bottomNav.itemTextColor = colorStateList
        bottomNav.itemIconTintList = colorStateList

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                // Ir a contactos
                R.id.nav_contacts -> {
                    super.playClick()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(
                        intent,
                        ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle()
                    )
                    finish()
                    true
                }
                // Ya esta en follow-ups
                R.id.nav_followUps -> true
                // Ir a ajustes
                R.id.nav_settings -> {
                    super.playClick()
                    val intent = Intent(this, SettingsActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(
                        intent,
                        ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle()
                    )
                    finish()
                    true
                }

                else -> false
            }
        }
    }

    // Muestra cada follow-up como tarjeta
    private fun displayFollowUps(followUps: List<FollowUp>) {
        cardsContainer.removeAllViews()
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        for (followUp in followUps) {
            val cardView =
                layoutInflater.inflate(R.layout.item_followup_card, cardsContainer, false)
            // Asigna datos a la tarjeta
            cardView.findViewById<TextView>(R.id.tvTitle).text = followUp.title
            cardView.findViewById<TextView>(R.id.tvType).text = followUp.type
            cardView.findViewById<TextView>(R.id.tvDate).text = sdf.format(followUp.date)
            // Configura botón para ver detalles
            cardView.findViewById<Button>(R.id.btnDetails).setOnClickListener {
                super.playClick()
                startActivity(Intent(this, FollowUpDetailActivity::class.java).apply {
                    putExtra("FOLLOWUP_ID", followUp.id)
                    putExtra("FOLLOWUP_TITLE", followUp.title)
                    putExtra("FOLLOWUP_TYPE", followUp.type)
                    putExtra("FOLLOWUP_DATE", followUp.date.time)
                    putExtra("FOLLOWUP_LOCATION", followUp.location)
                    putExtra("FOLLOWUP_DESC", followUp.description)
                }, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
            }
            // Agrega tarjeta al contenedor
            cardsContainer.addView(cardView)
        }
    }
}
