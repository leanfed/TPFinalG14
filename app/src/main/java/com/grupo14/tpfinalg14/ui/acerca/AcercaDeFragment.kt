// Paquete que agrupa los fragmentos relacionados con la sección "Acerca de" de la app
package com.grupo14.tpfinalg14.ui.acerca

// Importaciones necesarias para manejo de texto enriquecido, vistas y navegación de fragmentos
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.ui.login.LoginFragment
import com.grupo14.tpfinalg14.ui.calificaciones.CalificacionesFragment
import com.grupo14.tpfinalg14.ui.cronograma.CronogramaFragment
import com.grupo14.tpfinalg14.ui.presentismo.PresentismoFragment
import com.grupo14.tpfinalg14.ui.vencimientos.VencimientosFragment
import com.grupo14.tpfinalg14.utils.BottomBarUtils

// Fragmento que representa la sección "Acerca de" e incluye la política de privacidad
class AcercaDeFragment : Fragment() {

    // Declaración de vistas para el texto de privacidad y botón de menú contextual
    private lateinit var textPoliticaPrivacidad: TextView
    private lateinit var btnMenuAcercaDe: TextView

    // Infla el layout XML asociado al fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_acerca_de, container, false)

    // Configura lógica de vistas una vez creada la vista
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Asigna referencias a las vistas del layout
        textPoliticaPrivacidad = view.findViewById(R.id.textPoliticaPrivacidad)
        btnMenuAcercaDe = view.findViewById(R.id.btnMenu)

        // Aplica configuración visual para la BottomBar según los argumentos recibidos
        BottomBarUtils.configurarBottomBarDesdeArguments(this)

        // Carga los datos del usuario pasados como argumentos (nombre, email, documento)
        val args = arguments ?: return
        val bundle = Bundle().apply {
            putString("nombreCompleto", args.getString("nombreCompleto"))
            putString("email", args.getString("email"))
            putString("documento", args.getString("documento"))
        }

        // Abre menú contextual al hacer clic sobre el botón de menú (⋮)
        btnMenuAcercaDe.setOnClickListener {
            val popup = PopupMenu(requireContext(), btnMenuAcercaDe)
            popup.menuInflater.inflate(R.menu.menu_navigation, popup.menu)
            popup.menu.findItem(R.id.nav_acerca_de).isVisible = false // Oculta la opción actual del menú

            // Define acciones según el ítem del menú seleccionado
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.nav_calificaciones -> abrirFragmento(CalificacionesFragment().apply { arguments = bundle })
                    R.id.nav_vencimientos -> abrirFragmento(VencimientosFragment().apply { arguments = bundle })
                    R.id.nav_presentismo -> abrirFragmento(PresentismoFragment().apply { arguments = bundle })
                    R.id.nav_cronograma -> abrirFragmento(CronogramaFragment().apply { arguments = bundle })
                    R.id.nav_logout -> abrirFragmento(LoginFragment())
                }
                true
            }

            popup.show() // Muestra el menú emergente
        }

        // Texto de la política de privacidad con estructura multilínea
        val texto = """
            Última actualización: 25/05/2025

            Bienvenido a Educat App

            Tu privacidad es muy importante para nosotros, y nos comprometemos a proteger la información que compartes mientras utilizas nuestros servicios.

            1. Información que recopilamos
            • Datos personales: Nombre, dirección de correo electrónico, institución educativa, etc.
            • Datos de uso: Información sobre cómo interactúas con la app (funciones utilizadas, tiempo de uso, preferencias).
            • Archivos y contenido: Si subes archivos o imágenes dentro de la aplicación, estos pueden ser almacenados temporalmente para proporcionar una mejor experiencia.
            • Datos del dispositivo: Tipo de dispositivo, sistema operativo, versión de la app.

            2. Uso de la información
            • Mejorar la experiencia del usuario y personalizar la interfaz.
            • Analizar patrones de uso para optimizar la aplicación.
            • Garantizar la seguridad de los datos y prevenir fraudes.
            • Ofrecer soporte técnico cuando sea necesario.

            3. Compartición de datos
            Nos comprometemos a no vender ni compartir tu información personal con terceros, salvo en las siguientes situaciones:
            • Requerimientos legales: Si es necesario cumplir con normativas o responder a solicitudes legales.
            • Servicios externos: Si integramos herramientas de terceros (como autenticación por Google), solo compartiremos datos esenciales para el funcionamiento.

            4. Seguridad de la información
            Implementamos medidas de seguridad para proteger tu información contra accesos no autorizados, pérdidas o alteraciones. Sin embargo, recuerda que ninguna transmisión de datos en internet es completamente segura, por lo que te recomendamos:
            • Usar contraseñas seguras.
            • Evitar compartir datos sensibles.

            5. Derechos del usuario
            Como usuario, tienes derecho a:
            • Acceder a los datos que almacenamos.
            • Solicitar correcciones o eliminación de información personal.
            • Limitar el uso de ciertos datos.

            Si deseas ejercer tus derechos o tienes consultas, puedes contactarnos a través de atencionUsuarios@educat.com

            6. Cambios en esta política
            Podemos actualizar esta política de privacidad en el futuro. Notificaremos los cambios en la app antes de que entren en vigor. Te recomendamos revisar periódicamente esta página para estar al tanto de las actualizaciones.

            Versión 1.0
        """.trimIndent()

        // Convierte el texto plano en objeto Spannable para aplicar estilos
        val spannableTexto = SpannableString(texto)

        // Lista con los títulos principales que se desean resaltar en negrita
        val titulos = listOf(
            "1. Información que recopilamos",
            "2. Uso de la información",
            "3. Compartición de datos",
            "4. Seguridad de la información",
            "5. Derechos del usuario",
            "6. Cambios en esta política"
        )

        // Itera por los títulos y aplica estilo en negrita si se encuentran en el texto
        for (titulo in titulos) {
            val startIndex = texto.indexOf(titulo)
            if (startIndex != -1) {
                spannableTexto.setSpan(
                    StyleSpan(Typeface.BOLD),           // Aplica tipo de texto en negrita
                    startIndex,                         // Inicio del título
                    startIndex + titulo.length,         // Fin del título
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE    // El estilo no se extiende si se edita el texto
                )
            }
        }

        // Asigna el texto enriquecido a la vista final
        textPoliticaPrivacidad.text = spannableTexto
    }

    // Método auxiliar que permite reemplazar el fragmento actual por otro dentro del contenedor principal
    private fun abrirFragmento(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Cambia el fragmento activo
            .addToBackStack(null)                       // Permite regresar con el botón "Atrás"
            .commit()                                   // Aplica la transacción
    }
}