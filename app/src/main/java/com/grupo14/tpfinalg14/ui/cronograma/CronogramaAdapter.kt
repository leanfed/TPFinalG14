// Paquete que contiene los componentes visuales de la sección de cronograma
package com.grupo14.tpfinalg14.ui.cronograma

// Importaciones necesarias para inflar layouts y trabajar con RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.models.Cronograma

// Adaptador del RecyclerView que muestra una lista de materias con su día y horario
class CronogramaAdapter(private val listaCronograma: List<Cronograma>) :
    RecyclerView.Adapter<CronogramaAdapter.ViewHolder>() {

    // ViewHolder que representa una fila del RecyclerView (ítem visual)
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreMateria: TextView = view.findViewById(R.id.textNombreMateria) // Muestra el nombre de la materia
        val dias: TextView = view.findViewById(R.id.textDias)                   // Muestra los días de cursada
        val horario: TextView = view.findViewById(R.id.textHorario)             // Muestra el horario asignado
    }

    // Crea un nuevo ViewHolder inflando el layout item_cronograma
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cronograma, parent, false)
        return ViewHolder(view)
    }

    // Asocia los datos del modelo Cronograma a cada vista en la posición correspondiente
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cronograma = listaCronograma[position]

        holder.nombreMateria.text = cronograma.nombreMateria                // Establece el nombre de la materia
        holder.dias.text = "Día : ${cronograma.dias}"                       // Muestra los días (ej: "Lunes y Jueves")
        holder.horario.text = "Horario: ${cronograma.horario}"             // Muestra el rango horario
    }

    // Devuelve la cantidad total de elementos en la lista
    override fun getItemCount(): Int = listaCronograma.size
}