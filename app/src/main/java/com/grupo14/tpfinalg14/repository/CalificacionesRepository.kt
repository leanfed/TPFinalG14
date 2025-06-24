// Paquete que contiene las fuentes de datos simuladas de la app
package com.grupo14.tpfinalg14.repository

// Importa el modelo Calificacion para construir listas por materia
import com.grupo14.tpfinalg14.models.Calificacion

// Clase que agrupa una materia con su lista completa de calificaciones
data class MateriaConCalificaciones(
    val nombre: String,                           // Nombre de la materia (ej: "Lógica computacional")
    val calificaciones: List<Calificacion>        // Lista de objetos Calificacion asociadas a la materia
)

// Objeto singleton que simula una fuente de datos fija de calificaciones
object CalificacionesRepository {

    // Lista de materias con sus respectivas calificaciones cargadas manualmente
    val listaMaterias = listOf(
        MateriaConCalificaciones("Administración de bases de datos", listOf(
            Calificacion("Parcial 1", "Parcial", 8),
            Calificacion("TP 1", "TP", 6),
            Calificacion("TP 2", "TP", 5),
            Calificacion("Parcial 2", "Parcial", 7)
        )),
        MateriaConCalificaciones("Lógica computacional", listOf(
            Calificacion("Parcial 1", "Parcial", 6),
            Calificacion("Parcial 2", "Parcial", 5),
            Calificacion("TP 1", "TP", 7),
            Calificacion("TP 2", "TP", 4)
        )),
        MateriaConCalificaciones("Técnicas de programación", listOf(
            Calificacion("Parcial 1", "Parcial", 9),
            Calificacion("Parcial 2", "Parcial", 8),
            Calificacion("TP 1", "TP", 7),
            Calificacion("TP 2", "TP", 6)
        )),
        MateriaConCalificaciones("Metodología de pruebas de sistemas", listOf(
            Calificacion("Parcial 1", "Parcial", 4),
            Calificacion("TP 1", "TP", 5),
            Calificacion("TP 2", "TP", 4),
            Calificacion("Parcial 2", "Parcial", 3)
        ))
    )

    // Lista resumen simulada: toma solo la última calificación de cada materia
    // y le asigna un "estado" calculado en base al promedio de la materia
    val mockCalificaciones = listaMaterias.map { materia ->

        // Se toma la última calificación registrada
        val ultimaCalificacion = materia.calificaciones.last()

        // Calculamos el promedio entre todas las calificaciones de la materia
        val promedio = materia.calificaciones.map { it.nota }.average()

        // Se crea un objeto Calificacion con resumen de la última nota + estado general
        Calificacion(
            materia = materia.nombre,
            tipo = ultimaCalificacion.tipo,
            nota = ultimaCalificacion.nota,
            estado = obtenerEstado(promedio) // El estado se calcula según el promedio total
        )
    }

    // Función que devuelve un estado académico según el valor del promedio
    fun obtenerEstado(promedio: Double): String {
        return when {
            promedio >= 7.0        -> "Promocionado"          // Promoción directa
            promedio in 6.0..6.9   -> "Zona de Promoción"     // En condiciones de promoción
            promedio in 4.0..5.9   -> "Regular"               // Aprobado con examen final
            else                   -> "Libre"                 // Desaprobado
        }
    }
}