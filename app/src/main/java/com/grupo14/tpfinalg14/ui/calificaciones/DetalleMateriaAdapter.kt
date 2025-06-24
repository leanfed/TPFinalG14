// Paquete donde se encuentra el adaptador encargado de mostrar el detalle de calificaciones por materia
package com.grupo14.tpfinalg14.ui.calificaciones

// Importaciones necesarias para trabajar con vistas y listas en RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.models.Calificacion

// Adaptador que muestra una lista detallada de calificaciones individuales dentro de una materia
class DetalleMateriaAdapter(private val calificaciones: List<Calificacion>) :
    RecyclerView.Adapter<DetalleMateriaAdapter.ViewHolder>() {

    // Clase interna que representa una fila de la lista (ViewHolder)
    // Contiene las vistas que se completan con datos en cada ítem
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tipo: TextView = view.findViewById(R.id.textTipo)  // Muestra el tipo o descripción de la evaluación
        val nota: TextView = view.findViewById(R.id.textNota)  // Muestra la nota obtenida en esa evaluación
    }

    // Crea e infla el layout del ítem de lista (item_calificacion_detalle.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calificacion_detalle, parent, false)
        return ViewHolder(view)
    }

    // Asigna los valores reales del modelo Calificacion a cada vista del ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val calificacion = calificaciones[position]

        // Se muestra el nombre de la calificación (Ej: "Parcial 1", "TP 2")
        holder.tipo.text = calificacion.materia

        // Se muestra el valor numérico de la nota
        holder.nota.text = "Nota: ${calificacion.nota}"
    }

    // Devuelve la cantidad total de elementos a renderizar en la lista
    override fun getItemCount(): Int = calificaciones.size
}