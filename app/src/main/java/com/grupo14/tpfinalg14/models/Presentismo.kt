// Este archivo pertenece al paquete de modelos y define la estructura de un registro de asistencia
package com.grupo14.tpfinalg14.models

// Clase de datos que representa un evento de asistencia del alumno a una materia en una fecha puntual
data class Presentismo(
    val fecha: String,         // Fecha del evento de asistencia (ej: "2025-06-20")
    val materia: String,       // Nombre de la materia correspondiente (ej: "Técnicas de programación")
    val estado: String         // Estado del alumno en esa fecha (ej: "Presente", "Ausente")
)

