package com.albaboo.crmapp.ui.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.albaboo.crmapp.R
import com.albaboo.crmapp.data.models.Contact
import com.albaboo.crmapp.data.repository.ContactRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

// Actividad principal donde se muestran los contactos
class MainActivity : BaseActivity() {

    // Contenedor donde van las tarjetas
    private lateinit var cardsContainer: LinearLayout
    private lateinit var bottomNav: BottomNavigationView

    // Repositorio para obtener los contactos
    private val repository = ContactRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            val params = toolbar.layoutParams as android.view.ViewGroup.MarginLayoutParams
            params.topMargin = systemBars.top
            toolbar.layoutParams = params

            insets
        }
        // Inicializa vistas
        cardsContainer = findViewById(R.id.cardsContainer)
        setSupportActionBar(findViewById(R.id.toolbar))
        bottomNav = findViewById(R.id.bottomNavigation)

        setupBottomNavigation()
        refreshUI()
    }

    // Reinicia la actividad sin animación
    override fun onRestart() {
        super.onRestart()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        startActivity(intent)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }
    }

    // Refresca la interfaz
    private fun refreshUI() {
        bottomNav.selectedItemId = R.id.nav_contacts
        // Carga contactos en segundo plano
        lifecycleScope.launch {
            val contacts = repository.getContacts()
            displayContacts(contacts)
        }
    }

    // Configura menú inferior
    private fun setupBottomNavigation() {
        // Cambia colores según seleccionado
        val colorStateList = android.content.res.ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                getColor(R.color.primary),
                getColor(R.color.secondary)
            )
        )
        bottomNav.itemTextColor = colorStateList
        bottomNav.itemIconTintList = colorStateList

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                // Ya está en contactos
                R.id.nav_contacts -> true
                // Ir a seguimientos
                R.id.nav_followUps -> {
                    super.playClick()
                    val intent = Intent(this, FollowUpListActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(
                        intent,
                        ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle()
                    )
                    true
                }
                // Ir a ajustes
                R.id.nav_settings -> {
                    super.playClick()
                    val intent = Intent(this, SettingsActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(
                        intent,
                        ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle()
                    )
                    true
                }

                else -> false
            }
        }
    }

    // Muestra los contactos en tarjetas
    private fun displayContacts(contacts: List<Contact>) {
        // Limpia antes de volver a cargar
        cardsContainer.removeAllViews()

        for (contact in contacts) {
            // Infla el diseño de la tarjeta
            val cardView = layoutInflater.inflate(R.layout.item_contact_card, cardsContainer, false)
            // Asigna datos a la tarjeta
            cardView.findViewById<TextView>(R.id.tvName).text = contact.name
            cardView.findViewById<TextView>(R.id.tvCompany).text = contact.companyName
            cardView.findViewById<TextView>(R.id.tvEmail).text = contact.email
            // Configura botón para ver detalles
            cardView.findViewById<Button>(R.id.btnDetails).setOnClickListener {
                super.playClick()
                startActivity(Intent(this, ContactDetailActivity::class.java).apply {
                    putExtra("CONTACT_ID", contact.id)
                    putExtra("CONTACT_NAME", contact.name)
                    putExtra("CONTACT_COMPANY", contact.companyName)
                    putExtra("CONTACT_EMAIL", contact.email)
                    putExtra("CONTACT_EXT", contact.phoneExt)
                    putExtra("CONTACT_PHONE", contact.phone)
                }, ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle())
            }
            // Agrega la tarjeta al contenedor
            cardsContainer.addView(cardView)
        }
    }
}
