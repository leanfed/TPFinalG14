package com.grupo14.tpfinalg14.ui.login

import android.content.Intent // Permite cambiar de actividad
import android.graphics.Color // Utilizado para definir colores personalizados
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString // Permite aplicar estilos a partes del texto
import android.text.method.LinkMovementMethod // Permite interacción en textos
import android.text.style.ClickableSpan // Define comportamiento clickeable en texto
import android.text.style.ForegroundColorSpan // Aplica color al texto (solo se usa para el color gris)
import android.text.style.UnderlineSpan // Aplica subrayado a partes del texto
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.grupo14.tpfinalg14.MainActivity
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.ui.registro.RegisterActivity

// Clase principal para la pantalla de inicio de sesión
class LoginActivity : AppCompatActivity() {

    // Método que se ejecuta cuando la actividad se crea por primera vez
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establece el archivo de layout asociado (activity_login.xml)
        setContentView(R.layout.activity_login)

        // Referencia al botón de "Iniciar sesión"
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Referencia al TextView que contiene el texto "¿No tienes una cuenta? Registrate"
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        // Texto base del mensaje
        val text = "¿No tienes una cuenta? Registrate"

        // Se crea una cadena con capacidad de estilos
        val spannable = SpannableString(text)

        // Se define color gris para "¿No tienes una cuenta? "
        val grayColor = Color.parseColor("#DFDEDE")
        spannable.setSpan(
            ForegroundColorSpan(grayColor), // Aplica el color gris
            0, // Desde el inicio
            22, // Hasta el carácter 22 exclusivo (justo antes de "Registrate")
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Índices donde comienza y termina la palabra "Registrate"
        val start = 22
        val end = text.length

        // Aplica subrayado a "Registrate"
        spannable.setSpan(
            UnderlineSpan(), // Subraya la parte correspondiente
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Define el comportamiento clickeable para "Registrate"
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Al hacer clic, inicia RegisterActivity
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }

        // Asigna el comportamiento clickeable al texto "Registrate"
        spannable.setSpan(
            clickableSpan,
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Aplica el texto estilizado al TextView
        tvRegister.text = spannable

        // Permite interacción con el texto (imprescindible para ClickableSpan)
        tvRegister.movementMethod = LinkMovementMethod.getInstance()

        // Elimina el fondo azul al presionar el texto clickeable
        tvRegister.highlightColor = Color.TRANSPARENT

        // Listener del botón "Iniciar sesión"
        btnLogin.setOnClickListener {
            // Crea un intent para abrir MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Finaliza LoginActivity para que el usuario no pueda volver atrás con el botón físico
            finish()
        }
    }
}