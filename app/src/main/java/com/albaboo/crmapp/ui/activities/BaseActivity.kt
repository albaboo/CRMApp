package com.albaboo.crmapp.ui.activities

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale

// Clase base para todas las actividades
open class BaseActivity : AppCompatActivity() {

    // Variables para gestionar el sonido
    private var soundPool: SoundPool? = null
    private var soundId: Int = 0
    private var streamId: Int = 0

    // Cambia el idioma de la app según preferencias
    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("settings", MODE_PRIVATE)
        // Obtengo el idioma guardado o el predeterminado
        val lang = prefs.getString("app_language", Locale.getDefault().language) ?: "es"
        val locale = Locale.forLanguageTag(lang)
        // Configuro el nuevo idioma
        Locale.setDefault(locale)
        val config = newBase.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtener si el modo oscuro está activado
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val theme = prefs.getBoolean("dark_mode", false)
        // Aplica modo oscuro o claro
        val targetMode =
            if (theme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(targetMode)
        // Configuro atributos de audio
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        // Inicializa SoundPool
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()
        // Carga sonido de clic del sistema
        soundId = soundPool?.load("/system/media/audio/ui/Effect_Tick.ogg", 1) ?: 0
    }

    // Quita animaciones al cerrar la actividad
    override fun finish() {
        super.finish()
        // Si es Android 14 o superior
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, 0, 0)
        } else {
            // Versiones anteriores
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }
    }

    // Libera recursos cuando se destruye la actividad
    override fun onDestroy() {
        super.onDestroy()
        soundPool?.release()
        soundPool = null
    }

    // Reproducir sonido de clic
    fun playClick() {
        try {
            // Si ya hay sonido, se detiene
            if (streamId != 0)
                soundPool?.stop(streamId)
            // Reproduce el sonido
            streamId = soundPool?.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f) ?: 0
        } catch (e: Exception) {
            android.util.Log.e("SONIDO_ERROR", e.toString())
        }

    }
}
