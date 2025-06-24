// Paquete donde se agrupa la funcionalidad relacionada al calendario de la app
package com.grupo14.tpfinalg14.ui.calendario

// Importaciones necesarias para inflar vistas, trabajar con fragmentos y acceder a utilidades
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.utils.BottomBarUtils

// Fragmento responsable de mostrar la sección de calendario en la interfaz
class CalendarioFragment : Fragment() {

    // Método que infla el layout XML asociado al fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_calendario, container, false)
    // Retorna la vista que se va a renderizar a partir del archivo fragment_calendario.xml

    // Método que se ejecuta cuando la vista ya fue creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura la barra de navegación inferior utilizando los argumentos recibidos
        BottomBarUtils.configurarBottomBarDesdeArguments(this)
    }
}