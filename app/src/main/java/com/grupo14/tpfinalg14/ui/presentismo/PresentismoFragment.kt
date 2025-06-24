package com.grupo14.tpfinalg14.ui.presentismo

// Importaciones necesarias para inflar vistas, observar datos, navegar entre fragmentos y cargar layouts
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.models.Presentismo
import com.grupo14.tpfinalg14.ui.login.LoginFragment
import com.grupo14.tpfinalg14.utils.BottomBarUtils
import com.grupo14.tpfinalg14.viewmodel.PresentismoViewModel

// Fragmento encargado de mostrar el módulo de presentismo.
// Puede mostrar tanto un resumen general como el detalle por materia.
class PresentismoFragment : Fragment() {

    // Vistas de la interfaz
    private lateinit var tituloGeneral: TextView           // Título "Presentismo" (visible en modo resumen)
    private lateinit var tituloMateria: TextView           // Título con el nombre de la materia seleccionada
    private lateinit var recycler: RecyclerView            // Lista que muestra los registros de asistencia
    private lateinit var btnMenu: TextView                 // Botón ⋮ que despliega el menú contextual

    // ViewModel que contiene la lógica y los datos relacionados al presentismo
    private val viewModel: PresentismoViewModel by viewModels()

    // Infla el layout XML asociado a este fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_presentismo, container, false)

    // Se ejecuta una vez que la vista ha sido creada. Aquí se configuran componentes y lógica visual.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Vincula las vistas del layout con sus variables correspondientes
        tituloGeneral = view.findViewById(R.id.textTituloPresentismo)
        tituloMateria = view.findViewById(R.id.textMateriaSeleccionada)
        recycler = view.findViewById(R.id.recyclerViewPresentismo)
        btnMenu = view.findViewById(R.id.btnMenu)

        // Configura el RecyclerView con un layout vertical
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.isNestedScrollingEnabled = true // Permite scroll dentro del ScrollView si lo hubiera

        // Observa si hay una materia seleccionada en el ViewModel y actualiza la vista
        viewModel.materiaSeleccionada.observe(viewLifecycleOwner) { materia ->
            if (materia == null) {
                mostrarResumen()            // Si no hay materia activa, se muestra el resumen general
            } else {
                mostrarDetalleDeMateria(materia) // Si hay materia, se muestra su detalle
            }
        }

        // Si aún no se ha seleccionado ninguna materia al inicio, se muestra el resumen por defecto
        if (viewModel.materiaSeleccionada.value == null) {
            mostrarResumen()
        }

        // Configura el botón ⋮ para mostrar un menú contextual con materias y logout
        btnMenu.setOnClickListener {
            val popup = PopupMenu(requireContext(), btnMenu)
            val materias = viewModel.obtenerMaterias()                // Lista de materias disponibles
            val actual = viewModel.materiaSeleccionada.value          // Materia actual, si hubiera

            // Agrega al menú todas las materias excepto la que ya está seleccionada
            materias.forEachIndexed { index, materia ->
                if (materia != actual) {
                    popup.menu.add(0, index, index, materia)
                }
            }

            // Agrega la opción "Cerrar sesión" al final del menú
            popup.menu.add(0, -1, materias.size, "Cerrar sesión")

            // Define qué hacer cuando se elige una opción del menú
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    -1 -> { // Si se elige "Cerrar sesión"
                        Toast.makeText(requireContext(), "Sesión finalizada con éxito", Toast.LENGTH_SHORT).show()
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, LoginFragment())
                            .commit()
                    }
                    else -> {
                        // Cambia la materia seleccionada en el ViewModel
                        viewModel.seleccionarMateria(materias[item.itemId])
                    }
                }
                true
            }

            // Muestra el menú emergente
            popup.show()
        }

        // Configura la BottomBar utilizando argumentos del fragmento si existen
        BottomBarUtils.configurarBottomBarDesdeArguments(this)
    }

    // Muestra el resumen general de presentismo para todas las materias
    private fun mostrarResumen() {
        tituloGeneral.visibility = View.VISIBLE       // Título general "Presentismo"
        tituloMateria.visibility = View.GONE          // Oculta el nombre de materia porque no hay una seleccionada

        recycler.adapter = PresentismoAdapter(
            viewModel.obtenerResumen(),               // Lista general de registros de asistencia
            mostrarPorcentaje = true,                 // Muestra el presentismo como porcentaje
            mostrarMateria = true                     // Muestra el nombre de la materia en cada fila
        )
    }

    // Muestra el detalle completo de asistencia para una materia específica
    private fun mostrarDetalleDeMateria(materia: String) {
        tituloGeneral.visibility = View.GONE          // Oculta el título general
        tituloMateria.apply {
            visibility = View.VISIBLE                 // Muestra el nombre de la materia actual
            text = materia
        }

        recycler.adapter = PresentismoAdapter(
            viewModel.obtenerDetalle(materia),        // Lista de registros de asistencia para esta materia
            esDetalleMateria = true                   // Formato especial para vista de detalle
        )
    }
}