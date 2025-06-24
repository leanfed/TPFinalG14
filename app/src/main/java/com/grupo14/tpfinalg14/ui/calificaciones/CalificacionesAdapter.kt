// Paquete que contiene la lógica de presentación de calificaciones en lista
package com.grupo14.tpfinalg14.ui.calificaciones

// Importaciones necesarias para manejar vistas y listas en RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.repository.CalificacionesRepository
import com.grupo14.tpfinalg14.repository.MateriaConCalificaciones

// Adaptador de RecyclerView que muestra una lista de materias con su última calificación
class CalificacionesAdapter(private val materias: List<MateriaConCalificaciones>) :
    RecyclerView.Adapter<CalificacionesAdapter.ViewHolder>() {

    // ViewHolder representa una fila de la lista y contiene las vistas a mostrar
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val materia: TextView = view.findViewById(R.id.textMateria)     // Texto con el nombre de la materia
        val tipo: TextView = view.findViewById(R.id.textTipo)           // Texto con el tipo de evaluación (TP/Parcial)
        val nota: TextView = view.findViewById(R.id.textNota)           // Texto con la nota obtenida
        val estado: TextView = view.findViewById(R.id.textEstado)       // Texto con el estado calculado (Promocionado, Regular, etc.)
    }

    // Crea una nueva vista a partir del layout XML y la envuelve en un ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calificacion, parent, false) // Infla el layout individual de cada ítem
        return ViewHolder(view) // Devuelve una nueva instancia de ViewHolder
    }

    // Asocia los datos del modelo con las vistas del ViewHolder según la posición actual
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val materia = materias[position] // Obtiene la materia actual según la posición

        holder.materia.text = materia.nombre // Establece el nombre de la materia

        // Obtiene la última calificación registrada para la materia
        val ultimaCalificacion = materia.calificaciones.last()
        holder.tipo.text = "Tipo: ${ultimaCalificacion.tipo}"  // Muestra el tipo de esa última calificación
        holder.nota.text = "Nota: ${ultimaCalificacion.nota}"  // Muestra la nota de la última evaluación

        // Calcula el promedio de todas las calificaciones de la materia
        val promedio = materia.calificaciones.map { it.nota }.average()

        // Muestra el estado académico resultante del promedio
        holder.estado.text = CalificacionesRepository.obtenerEstado(promedio)
    }

    // Devuelve la cantidad total de elementos en la lista
    override fun getItemCount() = materias.size
}