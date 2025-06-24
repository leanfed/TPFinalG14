// Paquete que contiene la lógica visual para la sección de calificaciones
package com.grupo14.tpfinalg14.ui.calificaciones

// Importaciones necesarias para vistas, fragmentos, menú, recycler y observación de datos
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
import com.grupo14.tpfinalg14.models.Calificacion
import com.grupo14.tpfinalg14.repository.MateriaConCalificaciones
import com.grupo14.tpfinalg14.ui.login.LoginFragment
import com.grupo14.tpfinalg14.utils.BottomBarUtils
import com.grupo14.tpfinalg14.viewmodel.CalificacionesViewModel

// Fragmento que muestra la lista de calificaciones o el detalle de una materia
class CalificacionesFragment : Fragment() {

    // Vistas de la interfaz
    private lateinit var titulo: TextView                // Título visible cuando se muestra el detalle de una materia
    private lateinit var recycler: RecyclerView          // Lista para mostrar el resumen o el detalle
    private lateinit var btnMenu: TextView               // Botón que abre el menú contextual

    // ViewModel que contiene lógica y LiveData para manejar el estado
    private val viewModel: CalificacionesViewModel by viewModels()

    // Infla el layout del fragmento desde el XML correspondiente
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_calificaciones, container, false)

    // Lógica de configuración de la vista una vez creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referencias a elementos visuales definidos en fragment_calificaciones.xml
        titulo = view.findViewById(R.id.textTituloMateria)
        recycler = view.findViewById(R.id.recyclerViewCalificaciones)
        btnMenu = view.findViewById(R.id.btnMenu)

        // Configura layout vertical para la lista de calificaciones
        recycler.layoutManager = LinearLayoutManager(requireContext())

        // Observa si se debe mostrar el resumen de todas las materias
        viewModel.mostrarResumen.observe(viewLifecycleOwner) { mostrar ->
            if (mostrar) {
                titulo.visibility = View.GONE
                recycler.adapter = CalificacionesAdapter(viewModel.obtenerMaterias())
            }
        }

        // Observa si el usuario selecciona una materia específica para ver su detalle
        viewModel.materiaSeleccionada.observe(viewLifecycleOwner) { materia ->
            materia?.let { mostrarDetalleDeMateria(it) }
        }

        // Configura el botón de menú (⋮)
        btnMenu.setOnClickListener {
            val popup = PopupMenu(requireContext(), btnMenu)
            val materias = viewModel.obtenerMaterias()
            val actual = viewModel.materiaSeleccionada.value

            // Agrega una opción por cada materia excepto la que ya está activa
            materias.forEachIndexed { index, materia ->
                if (materia != actual) {
                    popup.menu.add(0, index, index, materia.nombre)
                }
            }

            // Agrega la opción "Cerrar sesión" como última entrada del menú
            popup.menu.add(0, -1, materias.size, "Cerrar sesión")

            // Acciones al seleccionar un ítem del menú
            popup.setOnMenuItemClickListener { item ->
                if (item.itemId == -1) {
                    // Cierra sesión y vuelve al LoginFragment
                    Toast.makeText(requireContext(), "Sesión finalizada con éxito", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, LoginFragment())
                        .commit()
                } else {
                    // Cambia la materia seleccionada en el ViewModel
                    viewModel.seleccionarMateria(item.itemId)
                }
                true
            }

            // Muestra el menú emergente
            popup.show()
        }

        // Configura la BottomBar si hay argumentos con datos de sesión
        BottomBarUtils.configurarBottomBarDesdeArguments(this)
    }

    // Muestra la vista detallada con todas las calificaciones de la materia seleccionada
    private fun mostrarDetalleDeMateria(materia: MateriaConCalificaciones) {
        titulo.visibility = View.VISIBLE
        titulo.text = materia.nombre

        // Convierte cada calificación de la materia en una lista nueva para el adapter
        val lista = materia.calificaciones.map {
            Calificacion(it.materia, it.tipo, it.nota)
        }

        // Asigna un adapter de detalle que muestra todas las calificaciones de la materia
        recycler.adapter = DetalleMateriaAdapter(lista)
    }
}