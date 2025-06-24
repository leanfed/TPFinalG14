// Paquete que contiene las clases fuente de datos simuladas de la app
package com.grupo14.tpfinalg14.repository

// Importa el modelo Cronograma, que representa una materia con día y horario
import com.grupo14.tpfinalg14.models.Cronograma

// Objeto singleton que simula un repositorio con horarios asignados para cada materia
object CronogramaRepository {

    // Lista generada dinámicamente a partir de las materias del repositorio de calificaciones
    // Para cada materia asigna un día (según su posición) y un horario fijo
    val listaMaterias = CalificacionesRepository.listaMaterias.mapIndexed { index, materia ->
        // Crea un objeto Cronograma por cada materia
        Cronograma(
            materia.nombre,                  // Nombre de la materia original
            obtenerDiaPorIndice(index),     // Día asignado en función del índice
            "18.00 - 22.00"                  // Horario fijo para todas las materias
        )
    }

    // Función privada que convierte un índice en día de la semana
    // Ayuda a distribuir las materias entre lunes y viernes
    private fun obtenerDiaPorIndice(index: Int): String {
        return when (index % 5) {            // Rota los días según el resto de dividir el índice por 5
            0 -> "Lunes"
            1 -> "Martes"
            2 -> "Miércoles"
            3 -> "Jueves"
            4 -> "Viernes"
            else -> "Sin día"                // Por seguridad, aunque nunca debería alcanzarse
        }
    }
}