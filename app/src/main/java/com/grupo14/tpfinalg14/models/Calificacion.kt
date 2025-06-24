// Paquete donde se encuentra definida esta clase, forma parte del módulo de modelos de datos
package com.grupo14.tpfinalg14.models

// Declaración de una data class llamada Calificacion
// Esta clase representa una evaluación realizada en una materia específica
data class Calificacion(
    val materia: String,         // Nombre de la materia (Ej: "Programación I")
    val tipo: String,            // Tipo de evaluación (Ej: "TP" o "Parcial")
    val nota: Int,               // Calificación numérica (por ejemplo: 6, 8, 10)
    val estado: String = ""      // Estado opcional (Ej: "Aprobado", "Ausente", "Recupera"), por defecto vacío
)