package com.grupo14.tpfinalg14.ui.perfil

// Importaciones necesarias para trabajar con fragments, vistas y utilidades
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.utils.BottomBarUtils

// Fragmento responsable de mostrar los datos del perfil del usuario (nombre, apellido, email y documento)
class PerfilFragment : Fragment() {

    // Variables que se vinculan a las vistas del layout para mostrar los datos del usuario
    private lateinit var textNombre: TextView           // Muestra el nombre del usuario
    private lateinit var textApellido: TextView         // Muestra el apellido del usuario
    private lateinit var textDocumento: TextView        // Muestra el número de documento del usuario
    private lateinit var textEmail: TextView            // Muestra la dirección de correo electrónico del usuario

    // Infla la vista del fragmento a partir del layout XML correspondiente (fragment_perfil.xml)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_perfil, container, false)

    // Se llama justo después de que la vista fue creada. Acá se inicializan vistas y se cargan datos
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Se vinculan las variables a los elementos visuales del layout mediante findViewById
        textNombre = view.findViewById(R.id.textNombre)
        textApellido = view.findViewById(R.id.textApellido)
        textDocumento = view.findViewById(R.id.textDocumento)
        textEmail = view.findViewById(R.id.textEmail)

        // Recupera los argumentos enviados al fragmento (usualmente desde HomeFragment o Login)
        val args = arguments
        val nombre = args?.getString("nombre") ?: "N/A"
        val apellido = args?.getString("apellido") ?: "N/A"
        val documento = args?.getString("documento") ?: "N/A"
        val email = args?.getString("email") ?: "N/A"
        val nombreCompleto = "$nombre $apellido"

        // Asigna los valores recuperados a las vistas para mostrar la información
        textNombre.text = "Nombre: $nombre"
        textApellido.text = "Apellido: $apellido"
        textDocumento.text = "Documento: $documento"
        textEmail.text = "Email: $email"

        // Configura la BottomBar con los datos del usuario actual (nombre completo, email y documento)
        BottomBarUtils.configurarBottomBar(this, nombreCompleto, email, documento)
    }
}