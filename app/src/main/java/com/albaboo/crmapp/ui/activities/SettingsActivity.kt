package com.albaboo.crmapp.ui.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.albaboo.crmapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

// Actividad para ajustes de la app
class SettingsActivity : BaseActivity() {

    // Referencias de botones
    private lateinit var switchNotifications: androidx.appcompat.widget.SwitchCompat
    private lateinit var switchDarkMode: androidx.appcompat.widget.SwitchCompat
    private lateinit var spinnerLanguage: Spinner
    private lateinit var bottomNav: BottomNavigationView

    // Lista de idiomas disponibles
    private val languages = listOf(
        "Español" to "es",
        "English" to "en",
        "Català" to "ca"
    )

    // Flag para evitar bucles al actualizar UI
    private var isUpdatingUI = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        setSupportActionBar(findViewById(R.id.toolbar))

        // Inicializa vistas
        bottomNav = findViewById(R.id.bottomNavigation)
        switchNotifications = findViewById(R.id.switchNotifications)
        switchDarkMode = findViewById(R.id.switchDarkMode)
        spinnerLanguage = findViewById(R.id.spinnerLanguage)
        // Configura spinner de idiomas
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, languages.map { it.first })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.adapter = adapter

        setupBottomNavigation()
        setupListeners()
        refreshUI()
    }

    override fun onRestart() {
        super.onRestart()
        refreshUI()  // refresca UI al volver
    }

    // Actualiza la UI según preferencias guardadas
    private fun refreshUI() {
        isUpdatingUI = true
        bottomNav.selectedItemId = R.id.nav_settings
        switchNotifications.isChecked = getNotifications()
        switchDarkMode.isChecked = getDarkMode()
        // Selecciona idioma actual en spinner
        val currentLang = getLocale()
        val langIndex = languages.indexOfFirst { it.second == currentLang }
        if (langIndex != -1) {
            spinnerLanguage.setSelection(langIndex)
        }
        isUpdatingUI = false
    }

    // Configura listeners
    private fun setupListeners() {
        // Notificaciones
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (!isUpdatingUI) setNotifications(isChecked)
        }
        // Modo oscuro
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (!isUpdatingUI) setDarkMode(isChecked)
        }
        // Reproduce sonido al tocar spinner
        spinnerLanguage.setOnTouchListener { v, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP) {
                super.playClick()
            }
            v.performClick()
            false
        }
        // Cambia de idioma
        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!isUpdatingUI) {
                    val selectedLangCode = languages[position].second
                    if (selectedLangCode != getLocale()) setLocale(selectedLangCode)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    // Configura menú inferior
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
                // Ir a seguimientos
                R.id.nav_followUps -> {
                    super.playClick()
                    val intent = Intent(this, FollowUpListActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(
                        intent,
                        ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle()
                    )
                    finish()
                    true
                }
                // Ya esta en ajustes
                R.id.nav_settings -> true
                else -> false
            }
        }
    }

    // Guardar preferencias de notificaciones
    private fun setNotifications(enabled: Boolean) {
        super.playClick()
        getSharedPreferences("settings", MODE_PRIVATE).edit { putBoolean("notifications", enabled) }
    }

    private fun getNotifications() =
        getSharedPreferences("settings", MODE_PRIVATE).getBoolean("notifications", true)

    // Guardar preferencias de modo oscuro
    private fun setDarkMode(enabled: Boolean) {
        super.playClick()
        getSharedPreferences("settings", MODE_PRIVATE).edit { putBoolean("dark_mode", enabled) }
        val mode =
            if (enabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun getDarkMode(): Boolean {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        return prefs.getBoolean("dark_mode", false)
    }

    // Cambiar idioma
    private fun setLocale(languageCode: String) {
        getSharedPreferences("settings", MODE_PRIVATE).edit {
            putString(
                "app_language",
                languageCode
            )
        }
        recreate()
    }

    private fun getLocale() =
        getSharedPreferences("settings", MODE_PRIVATE).getString("app_language", "es") ?: "es"
}
