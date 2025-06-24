package com.grupo14.tpfinalg14.ui.vencimientos

// Importaciones necesarias para inflar vistas, administrar listas y vincular datos
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.models.Vencimiento

// Adaptador para un RecyclerView que muestra una lista detallada de vencimientos para una materia
class DetalleVencimientosAdapter(private val vencimientos: List<Vencimiento>) :
    RecyclerView.Adapter<DetalleVencimientosAdapter.ViewHolder>() {

    // ViewHolder interno que representa cada ítem visual de la lista de vencimientos
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tipo: TextView = view.findViewById(R.id.textTipo)       // Muestra el tipo de entrega (ej: TP1, Examen final)
        val fecha: TextView = view.findViewById(R.id.textFecha)     // Muestra la fecha límite del vencimiento
    }

    // Método que infla el layout XML para cada ítem del RecyclerView y crea un ViewHolder asociado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Se infla el layout item_vencimiento_detalle.xml y se vincula al ViewHolder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vencimiento_detalle, parent, false)
        return ViewHolder(view)
    }

    // Método que asocia los datos reales con las vistas del ViewHolder según su posición en la lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Se accede al objeto Vencimiento correspondiente a la posición actual
        val vencimiento = vencimientos[position]

        // Se muestra el tipo de entrega en el TextView (ej: "TP1", "Proyecto Final")
        holder.tipo.text = vencimiento.tipo

        // Se muestra la fecha de entrega en el TextView, por ejemplo: "20/07/2025"
        holder.fecha.text = "${vencimiento.fecha}"
    }

    // Devuelve el número total de elementos en la lista, que determina cuántos ítems debe renderizar el RecyclerView
    override fun getItemCount(): Int = vencimientos.size
}