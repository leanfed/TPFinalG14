package com.grupo14.tpfinalg14.ui.vencimientos

// Importaciones necesarias para vistas, fragmentos, menú contextual, navegación, listas, y utilidades
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
import com.grupo14.tpfinalg14.repository.MateriaConVencimientos
import com.grupo14.tpfinalg14.ui.login.LoginFragment
import com.grupo14.tpfinalg14.utils.BottomBarUtils
import com.grupo14.tpfinalg14.viewmodel.VencimientosViewModel

// Fragmento que muestra los vencimientos académicos para todas las materias o una materia específica
class VencimientosFragment : Fragment() {

    // Vistas principales
    private lateinit var textMateria: TextView      // Título de la materia activa (solo en modo detalle)
    private lateinit var recycler: RecyclerView     // Lista de materias o de vencimientos según el modo
    private lateinit var btnMenu: TextView          // Botón ⋮ para desplegar el menú contextual

    // ViewModel que mantiene el estado de materia seleccionada y acceso a los datos
    private val viewModel: VencimientosViewModel by viewModels()

    // Infla el layout XML del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_vencimientos, container, false)

    // Se ejecuta después de que la vista fue creada. Aquí se configuran componentes, lógica y observadores.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Vincula las vistas con sus IDs del layout
        textMateria = view.findViewById(R.id.textTituloMateria)
        recycler = view.findViewById(R.id.recyclerViewVencimientos)
        btnMenu = view.findViewById(R.id.btnMenu)

        // Configura el RecyclerView con un layout vertical
        recycler.layoutManager = LinearLayoutManager(requireContext())

        // Observa si hay materia seleccionada. Si cambia, decide qué mostrar.
        viewModel.materiaSeleccionada.observe(viewLifecycleOwner) { materia ->
            if (materia == null) {
                mostrarResumen()              // Muestra el resumen de todas las materias
            } else {
                mostrarDetalleDeMateria(materia) // Muestra solo los vencimientos de esa materia
            }
        }

        // En el primer render si no hay materia activa, muestra el resumen
        if (viewModel.materiaSeleccionada.value == null) {
            mostrarResumen()
        }

        // Configura el botón ⋮ para mostrar un menú contextual con materias y opción "Cerrar sesión"
        btnMenu.setOnClickListener {
            val popup = PopupMenu(requireContext(), btnMenu)
            val materias = viewModel.obtenerMaterias()
            val actual = viewModel.materiaSeleccionada.value

            // Agrega al menú todas las materias distintas de la actualmente seleccionada
            materias.forEachIndexed { index, materia ->
                if (materia != actual) {
                    popup.menu.add(0, index, index, materia.nombre)
                }
            }

            // Agrega la opción de "Cerrar sesión" al final del menú
            popup.menu.add(0, -1, materias.size, "Cerrar sesión")

            // Maneja la acción de cada ítem seleccionado
            popup.setOnMenuItemClickListener { item ->
                if (item.itemId == -1) {
                    // Cierra sesión y vuelve al login
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

            // Muestra el menú
            popup.show()
        }

        // Configura la barra inferior con los datos de sesión (si están disponibles en arguments)
        BottomBarUtils.configurarBottomBarDesdeArguments(this)
    }

    // Muestra el resumen de vencimientos agrupado por materia
    private fun mostrarResumen() {
        textMateria.visibility = View.GONE                                  // Oculta el título porque no hay materia activa
        recycler.adapter = VencimientosAdapter(viewModel.obtenerMaterias()) // Muestra una lista con cada materia y su próximo vencimiento
    }

    // Muestra los detalles de los vencimientos para una materia específica
    private fun mostrarDetalleDeMateria(materia: MateriaConVencimientos) {
        textMateria.visibility = View.VISIBLE               // Muestra el nombre de la materia actual
        textMateria.text = materia.nombre
        recycler.adapter = DetalleVencimientosAdapter(materia.vencimientos) // Muestra todas las entregas de la materia
    }
}