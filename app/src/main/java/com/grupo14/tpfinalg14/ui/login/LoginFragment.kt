package com.grupo14.tpfinalg14.ui.login

// Importaciones necesarias para la funcionalidad visual, navegación y acceso a Room
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.database.AppDatabase
import com.grupo14.tpfinalg14.ui.registro.RegisterActivity
import com.grupo14.tpfinalg14.ui.home.HomeFragment
import kotlinx.coroutines.launch

// Fragmento que representa la pantalla de login como parte de una interfaz modular
class LoginFragment : Fragment() {

    // Método que infla (renderiza) el XML como vista visual del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Asocia el fragmento con su archivo de layout: fragment_login.xml
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    // Método que se ejecuta luego de que la vista fue creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referencias a los campos de texto y botones de la interfaz
        val editEmail = view.findViewById<EditText>(R.id.editCorreo)
        val editPassword = view.findViewById<EditText>(R.id.editPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val textViewR = view.findViewById<TextView>(R.id.textViewR)

        // Texto completo que contiene el enlace
        val fullText = "¿No tienes una cuenta? Registrarte"

        // Crea una instancia de texto estilizado
        val spannable = SpannableString(fullText)

        // Determina los índices donde aparece la palabra "Registrarte"
        val startIndex = fullText.indexOf("Registrarte")
        val endIndex = startIndex + "Registrarte".length

        // Subraya la palabra "Registrarte"
        spannable.setSpan(
            UnderlineSpan(),
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Define el comportamiento clickeable para "Registrarte"
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Inicia la actividad RegisterActivity al hacer clic
                val intent = Intent(requireContext(), RegisterActivity::class.java)
                startActivity(intent)
            }
        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Asigna el texto formateado al TextView
        textViewR.text = spannable

        // Habilita la interacción en el texto (imprescindible para ClickableSpan)
        textViewR.movementMethod = LinkMovementMethod.getInstance()

        // Acción al hacer clic en el botón de inicio de sesión
        btnLogin.setOnClickListener {
            // Obtiene el email y contraseña ingresados por el usuario
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()

            // Verifica que los campos no estén vacíos
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Obtiene una instancia del DAO de usuario desde la base Room
            val usuarioDao = AppDatabase.getDatabase(requireContext()).usuarioDao()

            // Lanza una coroutine atada al ciclo de vida del fragmento
            lifecycleScope.launch {
                // Intenta autenticar al usuario con los datos ingresados
                val usuario = usuarioDao.login(email, password)

                if (usuario != null) {
                    // Si las credenciales son válidas, muestra bienvenida
                    Toast.makeText(
                        requireContext(),
                        "¡Bienvenido a Educat ${usuario.nombre}!",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Prepara el HomeFragment con los datos del usuario como argumentos
                    val homeFragment = HomeFragment().apply {
                        arguments = Bundle().apply {
                            putString("nombreCompleto", "${usuario.nombre} ${usuario.apellido}")
                            putString("email", usuario.email)
                            putString("documento", usuario.documento)
                        }
                    }

                    // Reemplaza este fragmento por HomeFragment en la actividad actual
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, homeFragment)
                        .commit()
                } else {
                    // Si el usuario no existe o los datos son incorrectos
                    Toast.makeText(requireContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}