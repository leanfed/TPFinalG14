package com.grupo14.tpfinalg14.utils

// Importaciones necesarias para acceder a vistas, argumentos y navegación entre fragments
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.ui.calendario.CalendarioFragment
import com.grupo14.tpfinalg14.ui.home.HomeFragment
import com.grupo14.tpfinalg14.ui.perfil.PerfilFragment

// Objeto utilitario que encapsula la configuración de la barra inferior de navegación
object BottomBarUtils {

    // Extrae los argumentos del fragmento actual y configura la BottomBar con ellos
    fun configurarBottomBarDesdeArguments(fragment: Fragment) {
        val args = fragment.arguments ?: return  // Si no hay argumentos, no hace nada

        val nombreCompleto = args.getString("nombreCompleto") ?: return
        val email = args.getString("email") ?: return
        val documento = args.getString("documento") ?: return

        // Reutiliza la función principal con los valores extraídos
        configurarBottomBar(fragment, nombreCompleto, email, documento)
    }

    // Configura los botones de navegación inferior según el fragmento activo y los datos del usuario
    fun configurarBottomBar(fragment: Fragment, nombreCompleto: String, email: String, documento: String) {
        val rootView = fragment.view ?: return

        // Busca el contenedor de la barra inferior dentro del layout del fragmento
        val bottomBarScope = rootView.findViewById<ViewGroup>(R.id.bottom_bar_include) ?: rootView

        // Prepara un bundle con los datos del usuario para reenviar al navegar
        val bundle = Bundle().apply {
            putString("nombreCompleto", nombreCompleto)
            putString("email", email)
            putString("documento", documento)
        }

        // Asigna la acción del ícono de calendario: abre CalendarioFragment con los datos de sesión
        bottomBarScope.findViewById<ImageView>(R.id.icon_calendar)?.setOnClickListener {
            fragment.reemplazarConDatos(CalendarioFragment(), bundle)
        }

        // Asigna la acción del ícono de inicio: vuelve a HomeFragment con los datos del usuario
        bottomBarScope.findViewById<ImageView>(R.id.icon_home)?.setOnClickListener {
            fragment.reemplazarConDatos(HomeFragment(), bundle)
        }

        // Asigna la acción del ícono de perfil: extrae nombre y apellido y abre PerfilFragment
        bottomBarScope.findViewById<ImageView>(R.id.icon_profile)?.setOnClickListener {
            val partes = nombreCompleto.split(" ")                            // Divide el nombre completo
            val nombre = partes.firstOrNull() ?: ""                           // Extrae el primer nombre
            val apellido = partes.drop(1).joinToString(" ")                   // Junta el resto como apellido

            // Prepara un nuevo bundle separado para PerfilFragment con nombre y apellido separados
            val perfilBundle = Bundle().apply {
                putString("nombre", nombre)
                putString("apellido", apellido)
                putString("email", email)
                putString("documento", documento)
            }

            fragment.reemplazarConDatos(PerfilFragment(), perfilBundle)
        }
    }

    // Función auxiliar que reemplaza el fragmento actual por otro, asignando el bundle correspondiente
    private fun Fragment.reemplazarConDatos(destino: Fragment, bundle: Bundle) {
        destino.arguments = bundle  // Asigna los datos al nuevo fragmento
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, destino)      // Reemplaza el fragmento actual por el nuevo
            .addToBackStack(null)                           // Permite volver atrás
            .commit()
    }
}