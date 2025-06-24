// Paquete que agrupa los repositorios que simulan o gestionan datos de materias
package com.grupo14.tpfinalg14.repository

// Importa el modelo que representa una entrega o vencimiento con tipo y fecha
import com.grupo14.tpfinalg14.models.Vencimiento

// Clase de datos que asocia una materia con su lista de entregas pendientes
data class MateriaConVencimientos(
    val nombre: String,                      // Nombre de la materia (Ej: "Lógica computacional")
    val vencimientos: List<Vencimiento>     // Lista de trabajos o entregas con sus fechas
)

// Objeto singleton que actúa como fuente de datos de entregas por materia
object VencimientosRepository {

    // Lista de materias generada automáticamente en base al repositorio de calificaciones
    // Para cada materia, se crean sus vencimientos correspondientes con la función de abajo
    val listaMaterias = CalificacionesRepository.listaMaterias.map { materia ->
        MateriaConVencimientos(
            nombre = materia.nombre,                                    // Usa el nombre de la materia original
            vencimientos = generarVencimientosParaMateria(materia.nombre) // Genera una lista fija por materia
        )
    }

    // Función que devuelve una lista de entregas específicas para cada materia
    private fun generarVencimientosParaMateria(nombreMateria: String): List<Vencimiento> {
        return when (nombreMateria) {

            // Vencimientos específicos para "Administración de bases de datos"
            "Administración de bases de datos" -> listOf(
                Vencimiento("Entrega TP N° 1", "10/06/2025"),
                Vencimiento("Entrega TP N° 2", "02/07/2025"),
                Vencimiento("Entrega tarea N° 1", "15/06/2025"),
                Vencimiento("Entrega TP final", "20/06/2025")
            )

            // Vencimientos específicos para "Técnicas de programación"
            "Técnicas de programación" -> listOf(
                Vencimiento("Entrega tarea N° 1", "12/06/2025"),
                Vencimiento("Entrega tarea N° 2", "20/06/2025"),
                Vencimiento("Entrega tarea N° 3", "30/06/2025"),
                Vencimiento("Entrega tarea N° 4", "05/07/2025")
            )

            // Vencimientos específicos para "Lógica computacional"
            "Lógica computacional" -> listOf(
                Vencimiento("Entrega proyecto N° 1", "08/06/2025"),
                Vencimiento("Entrega TP N° 1", "22/06/2025"),
                Vencimiento("Entrega informe final", "28/06/2025"),
                Vencimiento("Entrega TP final", "03/07/2025")
            )

            // Vencimientos específicos para "Metodología de pruebas de sistemas"
            "Metodología de pruebas de sistemas" -> listOf(
                Vencimiento("Entrega TP N° 1", "18/06/2025"),
                Vencimiento("Entrega TP N° 2", "26/06/2025"),
                Vencimiento("Entrega domiciliario N° 1", "22/06/2025"),
                Vencimiento("Entrega TP N° 3", "01/07/2025")
            )

            // Si la materia no coincide con ninguna conocida, retorna una lista vacía
            else -> emptyList()
        }
    }
}