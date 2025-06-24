package com.grupo14.tpfinalg14.ui.splash

// Importaciones necesarias para manejar vistas, temporizadores y navegación de actividades
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.grupo14.tpfinalg14.ui.login.LoginActivity
import com.grupo14.tpfinalg14.R

// Actividad que se muestra brevemente al iniciar la aplicación. Normalmente se usa para mostrar el logo de la app.
class SplashActivity : AppCompatActivity() {

    private lateinit var logoImageView: ImageView      // Imagen que contiene el logo de la aplicación

    // Handler que permite programar tareas con retardo en el hilo principal de la interfaz
    private val handler = Handler(Looper.getMainLooper())

    // Se ejecuta cuando la actividad se crea por primera vez
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Asocia la vista con el layout 'activity_splash.xml'
        setContentView(R.layout.activity_splash)

        // Enlaza el logo desde el layout con la variable declarada
        logoImageView = findViewById(R.id.logoImageView)

        // 🔸 Mostrar el logo con 1 segundo de retardo desde el inicio
        handler.postDelayed({
            logoImageView.visibility = View.VISIBLE     // Cambia la visibilidad del logo de invisible a visible
        }, 1000)

        // 🔸 Después de 3 segundos desde que inició la actividad, se lanza el Login
        handler.postDelayed({
            val intent = Intent(this, LoginActivity::class.java)  // Prepara el intent para ir al Login
            startActivity(intent)                                 // Inicia la actividad de Login
            finish()                                              // Cierra la SplashActivity para que no quede en el historial
        }, 3000)
    }
}