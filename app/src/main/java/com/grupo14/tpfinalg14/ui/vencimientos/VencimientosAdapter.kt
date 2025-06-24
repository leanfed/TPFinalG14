package com.grupo14.tpfinalg14.ui.vencimientos

// Importaciones necesarias para inflar layouts, manejar vistas y trabajar con listas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.repository.MateriaConVencimientos

// Adaptador para un RecyclerView que muestra por cada materia su próximo vencimiento
class VencimientosAdapter(
    private val materias: List<MateriaConVencimientos> // Lista de materias con sus respectivos vencimientos
) : RecyclerView.Adapter<VencimientosAdapter.ViewHolder>() {

    // ViewHolder interno que representa cada fila de la lista
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textNombre: TextView = view.findViewById(R.id.textNombreMateria)            // Muestra el nombre de la materia
        val textProximoVencimiento: TextView = view.findViewById(R.id.textProximoVencimiento)  // Muestra el próximo vencimiento
    }

    // Crea (infla) la vista para cada ítem de la lista usando el layout 'item_vencimiento.xml'
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vencimiento, parent, false)
        return ViewHolder(view)
    }

    // Asocia los datos con las vistas del ViewHolder correspondiente a la posición actual
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val materia = materias[position]                         // Se obtiene la materia actual
        holder.textNombre.text = materia.nombre                  // Se muestra el nombre de la materia

        // Busca el vencimiento más próximo (por fecha). minByOrNull elige el más temprano.
        val proximo = materia.vencimientos.minByOrNull { it.fecha }

        // Si se encuentra al menos un vencimiento, muestra el tipo y la fecha. Si no, muestra un mensaje por defecto.
        holder.textProximoVencimiento.text = proximo?.let {
            "${it.tipo} - ${it.fecha}"                           // Ejemplo: "TP Final - 25/06/2025"
        } ?: "Sin vencimientos"                                  // En caso de lista vacía
    }

    // Devuelve la cantidad total de materias en la lista
    override fun getItemCount() = materias.size
}