package com.grupo14.tpfinalg14.ui.home

// Importaciones necesarias para inflar vistas, navegación, UI y acceso a datos
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.repository.CalificacionesRepository
import com.grupo14.tpfinalg14.repository.CronogramaRepository
import com.grupo14.tpfinalg14.repository.PresentismoRepository
import com.grupo14.tpfinalg14.repository.VencimientosRepository
import com.grupo14.tpfinalg14.ui.acerca.AcercaDeFragment
import com.grupo14.tpfinalg14.ui.calificaciones.CalificacionesFragment
import com.grupo14.tpfinalg14.ui.cronograma.CronogramaFragment
import com.grupo14.tpfinalg14.ui.login.LoginFragment
import com.grupo14.tpfinalg14.ui.presentismo.PresentismoFragment
import com.grupo14.tpfinalg14.ui.vencimientos.VencimientosFragment
import com.grupo14.tpfinalg14.utils.BottomBarUtils
import com.grupo14.tpfinalg14.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    // Vistas principales del encabezado
    private lateinit var textUserName: TextView               // Muestra el nombre del usuario logueado
    private lateinit var textMateria: TextView                // Muestra la materia actualmente seleccionada
    private lateinit var btnMenu: TextView                    // Botón con los tres puntos para abrir el menú contextual

    // Tarjetas que resumen información clave de cada módulo
    private lateinit var cardCalificaciones: CardView         // Card de calificaciones
    private lateinit var cardVencimientos: CardView           // Card de vencimientos
    private lateinit var cardPresentismo: CardView            // Card de presentismo
    private lateinit var cardCronograma: CardView             // Card del cronograma semanal

    // Campos visibles dentro de cada tarjeta (textos secundarios)
    private lateinit var textTituloCalificaciones: TextView           // Título de la sección "Calificaciones"
    private lateinit var textEvaluacionCalificaciones: TextView       // Tipo de evaluación más reciente (ej: "TP 2")
    private lateinit var textNotaCalificaciones: TextView             // Nota obtenida en la última evaluación

    private lateinit var textTituloVencimientos: TextView             // Título de la sección "Vencimientos"
    private lateinit var textTrabajoVencimientos: TextView            // Tipo de trabajo próximo
    private lateinit var textFechaVencimientos: TextView              // Fecha de entrega

    private lateinit var textTituloPresentismo: TextView              // Título de la sección "Presentismo"
    private lateinit var textPorcentajePresentismo: TextView          // Porcentaje de asistencia del usuario

    private lateinit var textTituloCronograma: TextView               // Título de la sección "Cronograma semanal"
    private lateinit var textDiaCronograma: TextView                  // Día en que se cursa la materia
    private lateinit var textHorarioCronograma: TextView              // Horario de cursada

    // ViewModel que mantiene la materia seleccionada para cargar sus datos
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false) // Infla el layout asociado al fragmento

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa todas las vistas accediendo por ID
        textUserName = view.findViewById(R.id.textUserName)
        textMateria = view.findViewById(R.id.textMateria)
        btnMenu = view.findViewById(R.id.btnMenu)

        cardCalificaciones = view.findViewById(R.id.card_calificaciones)
        cardVencimientos = view.findViewById(R.id.card_vencimientos)
        cardPresentismo = view.findViewById(R.id.card_presentismo)
        cardCronograma = view.findViewById(R.id.card_cronograma)

        textTituloCalificaciones = view.findViewById(R.id.textTituloCalificaciones)
        textEvaluacionCalificaciones = view.findViewById(R.id.textEvaluacionCalificaciones)
        textNotaCalificaciones = view.findViewById(R.id.textNotaCalificaciones)
        textTituloVencimientos = view.findViewById(R.id.textTituloVencimientos)
        textTrabajoVencimientos = view.findViewById(R.id.textTrabajoVencimientos)
        textFechaVencimientos = view.findViewById(R.id.textFechaVencimientos)
        textTituloPresentismo = view.findViewById(R.id.textTituloPresentismo)
        textPorcentajePresentismo = view.findViewById(R.id.textPorcentajePresentismo)
        textTituloCronograma = view.findViewById(R.id.textTituloCronograma)
        textDiaCronograma = view.findViewById(R.id.textDiaCronograma)
        textHorarioCronograma = view.findViewById(R.id.textHorarioCronograma)

        // Recupera los datos del usuario desde argumentos enviados desde el Login o splash screen
        val nombreCompleto = arguments?.getString("nombreCompleto") ?: "Nombre Apellido"
        val emailUsuario = arguments?.getString("email") ?: "Email no disponible"
        val documentoUsuario = arguments?.getString("documento") ?: "Documento no disponible"

        // Bundle que permite reenviar estos datos a otros fragmentos
        val bundle = Bundle().apply {
            putString("nombreCompleto", nombreCompleto)
            putString("email", emailUsuario)
            putString("documento", documentoUsuario)
        }

        // Muestra el nombre con una animación sutil
        updateUserName(nombreCompleto)

        // Observa cambios en la materia activa seleccionada y actualiza las cards-resumen
        viewModel.materiaActual.observe(viewLifecycleOwner) { materia ->
            actualizarResumen(materia)
        }

        // Configura la barra inferior con los datos del usuario
        BottomBarUtils.configurarBottomBar(this, nombreCompleto, emailUsuario, documentoUsuario)

        // Configura el menú contextual superior derecho (⋮)
        btnMenu.setOnClickListener {
            val popup = PopupMenu(requireContext(), btnMenu)
            popup.menuInflater.inflate(R.menu.menu_navigation, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.nav_calificaciones -> abrirFragmento(CalificacionesFragment().apply { arguments = bundle })
                    R.id.nav_vencimientos   -> abrirFragmento(VencimientosFragment().apply   { arguments = bundle })
                    R.id.nav_presentismo    -> abrirFragmento(PresentismoFragment().apply    { arguments = bundle })
                    R.id.nav_cronograma     -> abrirFragmento(CronogramaFragment().apply     { arguments = bundle })
                    R.id.nav_acerca_de      -> abrirFragmento(AcercaDeFragment().apply       { arguments = bundle })
                    R.id.nav_logout         -> {
                        Toast.makeText(requireContext(), "Sesión finalizada con éxito", Toast.LENGTH_SHORT).show()
                        abrirFragmento(LoginFragment())
                    }
                }
                true
            }
            popup.show()
        }

        // Listeners de las tarjetas que permiten navegar directamente a su sección
        cardCalificaciones.setOnClickListener { abrirFragmento(CalificacionesFragment().apply { arguments = bundle }) }
        cardVencimientos.setOnClickListener   { abrirFragmento(VencimientosFragment().apply   { arguments = bundle }) }
        cardPresentismo.setOnClickListener    { abrirFragmento(PresentismoFragment().apply    { arguments = bundle }) }
        cardCronograma.setOnClickListener     { abrirFragmento(CronogramaFragment().apply     { arguments = bundle }) }
    }

    // Muestra el nombre del usuario suavemente (animación de fade-in)
    private fun updateUserName(nombre: String) {
        textUserName.text = nombre
        textUserName.alpha = 0f
        textUserName.animate().alpha(1f).setDuration(800).start()
    }

    // Carga los datos correspondientes a la materia activa y actualiza las tarjetas visuales
    private fun actualizarResumen(materiaActual: String) {
        val cal = CalificacionesRepository.listaMaterias.find { it.nombre == materiaActual }?.calificaciones?.lastOrNull()
        val ven = VencimientosRepository.listaMaterias.find { it.nombre == materiaActual }?.vencimientos?.lastOrNull()
        val crono = CronogramaRepository.listaMaterias.find { it.nombreMateria == materiaActual }
        val porcentaje = PresentismoRepository.calcularPorcentajePresentismo(materiaActual)

        textMateria.text = materiaActual

        textTituloCalificaciones.text = "Calificaciones"
        textEvaluacionCalificaciones.text = cal?.tipo ?: "Sin evaluaciones"
        textNotaCalificaciones.text = cal?.let { "Nota: ${it.nota}" } ?: "Nota no disponible"

        textTituloVencimientos.text = "Vencimientos"
        textTrabajoVencimientos.text = ven?.tipo ?: "Sin vencimientos"
        textFechaVencimientos.text = ven?.fecha ?: "Fecha no disponible"

        textTituloPresentismo.text = "Presentismo"
        textPorcentajePresentismo.text = "$porcentaje%"

        textTituloCronograma.text = "Cronograma semanal"
        textDiaCronograma.text = crono?.let { "Día: ${it.dias}" } ?: "Sin cronograma"
        textHorarioCronograma.text = crono?.let { "Horario: ${it.horario}" } ?: "Horario no disponible"
    }

    // Navega hacia otro fragmento reemplazando el contenido actual (permite "volver atrás")
    private fun abrirFragmento(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}