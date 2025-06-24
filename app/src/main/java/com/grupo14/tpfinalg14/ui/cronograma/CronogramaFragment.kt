// Paquete que contiene la lógica visual del módulo de cronograma académico
package com.grupo14.tpfinalg14.ui.cronograma

// Importaciones necesarias para vistas, navegación, adaptadores y ViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.ui.acerca.AcercaDeFragment
import com.grupo14.tpfinalg14.ui.calificaciones.CalificacionesFragment
import com.grupo14.tpfinalg14.ui.login.LoginFragment
import com.grupo14.tpfinalg14.ui.presentismo.PresentismoFragment
import com.grupo14.tpfinalg14.ui.vencimientos.VencimientosFragment
import com.grupo14.tpfinalg14.utils.BottomBarUtils
import com.grupo14.tpfinalg14.viewmodel.CronogramaViewModel

// Fragmento que muestra la lista de materias con sus días y horarios asignados
class CronogramaFragment : Fragment() {

    // Vistas de la interfaz de usuario
    private lateinit var titulo: TextView               // Título de la pantalla
    private lateinit var recycler: RecyclerView         // Lista del cronograma
    private lateinit var btnMenu: TextView              // Botón para desplegar el menú contextual

    // ViewModel que contiene los datos observables del cronograma
    private val viewModel: CronogramaViewModel by viewModels()

    // Infla el layout XML del fragmento (fragment_cronograma.xml)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_cronograma, container, false)

    // Configura la lógica una vez que la vista fue creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Asigna vistas del layout a variables locales
        titulo = view.findViewById(R.id.textTituloCronograma)
        recycler = view.findViewById(R.id.recyclerViewCronograma)
        btnMenu = view.findViewById(R.id.btnMenu)

        // Aplica un layout lineal vertical al RecyclerView
        recycler.layoutManager = LinearLayoutManager(requireContext())

        // Observa el LiveData de la ViewModel para actualizar la lista
        viewModel.cronograma.observe(viewLifecycleOwner) { lista ->
            recycler.adapter = CronogramaAdapter(lista)
        }

        // Configura la barra inferior si hay argumentos presentes
        BottomBarUtils.configurarBottomBarDesdeArguments(this)

        // Recupera datos de usuario para mantenerlos entre fragmentos
        val args = arguments ?: return
        val bundle = Bundle().apply {
            putString("nombreCompleto", args.getString("nombreCompleto"))
            putString("email", args.getString("email"))
            putString("documento", args.getString("documento"))
        }

        // Abre menú contextual al presionar el botón de menú
        btnMenu.setOnClickListener {
            val popup = PopupMenu(requireContext(), btnMenu)
            popup.menuInflater.inflate(R.menu.menu_navigation, popup.menu)
            popup.menu.findItem(R.id.nav_cronograma)?.isVisible = false // Oculta la opción actual

            // Maneja las acciones del menú
            popup.setOnMenuItemClickListener { item ->
                val destino = when (item.itemId) {
                    R.id.nav_calificaciones -> CalificacionesFragment()
                    R.id.nav_vencimientos   -> VencimientosFragment()
                    R.id.nav_presentismo    -> PresentismoFragment()
                    R.id.nav_acerca_de      -> AcercaDeFragment()
                    R.id.nav_logout         -> LoginFragment()
                    else -> null
                }
                // Asocia los datos del usuario solo si no es logout
                destino?.arguments = if (item.itemId != R.id.nav_logout) bundle else null
                destino?.let { abrirFragmento(it) }
                true
            }

            // Muestra el menú emergente
            popup.show()
        }
    }

    // Reemplaza el fragmento actual por otro dentro del contenedor principal
    private fun abrirFragmento(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Permite volver con el botón "Atrás"
            .commit()
    }
}