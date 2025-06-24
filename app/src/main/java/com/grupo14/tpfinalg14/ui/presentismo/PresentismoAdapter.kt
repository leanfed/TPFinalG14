package com.grupo14.tpfinalg14.ui.presentismo

// Importaciones necesarias para construir el RecyclerView y aplicar estilos condicionales
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.models.Presentismo
import com.grupo14.tpfinalg14.repository.PresentismoRepository

// Adaptador que se encarga de mostrar una lista de registros de presentismo
// Puede adaptarse a múltiples usos según los parámetros: vista general, detalle, resumen
class PresentismoAdapter(
    private val listaPresentismo: List<Presentismo>,
    private val mostrarPorcentaje: Boolean = false,     // Si se debe mostrar el porcentaje total por materia
    private val mostrarMateria: Boolean = false,        // Si se debe mostrar el nombre de la materia en cada ítem
    private val esDetalleMateria: Boolean = false       // Si es un listado detallado dentro de una materia específica
) : RecyclerView.Adapter<PresentismoAdapter.ViewHolder>() {

    // ViewHolder que encapsula las vistas de cada ítem (pueden variar según el layout)
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val materia: TextView? = view.findViewById(R.id.textNombreMateria)                    // Nombre de la materia (solo en resumen)
        val fecha: TextView = view.findViewById(R.id.textFechaAsistencia)                     // Fecha específica del registro
        val estado: TextView = view.findViewById(R.id.textEstadoAsistencia)                   // Estado: "Asistido" o "Falta"
        val porcentaje: TextView? = view.findViewById(R.id.textPorcentajePresentismo)         // Porcentaje de asistencia (solo en resumen)
        val tituloPresentismo: TextView? = view.findViewById(R.id.textTituloPresentismoDetalle)       // Título "Presentismo" en vista de detalle
        val porcentajePresentismo: TextView? = view.findViewById(R.id.textPorcentajePresentismoDetalle) // Solo en la primera fila del detalle
    }

    // Infla el layout correspondiente a cada ítem (detalle o resumen)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = if (esDetalleMateria) R.layout.item_presentismo_detalle else R.layout.item_presentismo
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    // Asocia los datos de presentismo con las vistas de cada ítem
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val presentismo = listaPresentismo[position]

        // Si estamos en la vista de detalle y es el primer ítem, mostramos solo el resumen con porcentaje
        if (esDetalleMateria && position == 0) {
            holder.fecha.visibility = View.GONE
            holder.estado.visibility = View.GONE
            holder.tituloPresentismo?.visibility = View.VISIBLE
            holder.porcentajePresentismo?.visibility = View.VISIBLE
            holder.porcentajePresentismo?.text = presentismo.estado // Se usa el campo estado para pasar el porcentaje
        } else {
            holder.fecha.text = "Fecha: ${presentismo.fecha}"
            holder.estado.text = presentismo.estado
            holder.tituloPresentismo?.visibility = View.GONE
            holder.porcentajePresentismo?.visibility = View.GONE
        }

        // Aplica un color diferente al texto del estado según si fue asistido o falta
        val color = if (presentismo.estado == "Asistido") {
            ContextCompat.getColor(holder.itemView.context, R.color.green)
        } else {
            ContextCompat.getColor(holder.itemView.context, R.color.red)
        }
        holder.estado.setTextColor(color)

        // Si se indicó mostrar el porcentaje de la materia, se calcula y se muestra con el formato correspondiente
        if (mostrarPorcentaje) {
            val porcentaje = PresentismoRepository.calcularPorcentajePresentismo(presentismo.materia)
            holder.porcentaje?.visibility = View.VISIBLE
            holder.porcentaje?.text = "Presentismo: $porcentaje%"
        } else {
            holder.porcentaje?.visibility = View.GONE
        }

        // Si se indicó mostrar el nombre de la materia (vista general), se muestra en el ítem
        if (mostrarMateria) {
            holder.materia?.visibility = View.VISIBLE
            holder.materia?.text = presentismo.materia
        } else {
            holder.materia?.visibility = View.GONE
        }
    }

    // Devuelve la cantidad total de ítems que se deben mostrar
    override fun getItemCount(): Int = listaPresentismo.size
}