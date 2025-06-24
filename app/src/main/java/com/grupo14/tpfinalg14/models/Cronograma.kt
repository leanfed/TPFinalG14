// Este archivo forma parte del paquete de modelos de datos
package com.grupo14.tpfinalg14.models

// Clase de datos que representa el cronograma semanal de una materia
data class Cronograma(
    val nombreMateria: String, // Nombre de la materia correspondiente (ej: "Análisis Matemático")
    val dias: String,          // Días de cursada (ej: "Jueves")
    val horario: String        // Horario asignado (ej: "18:00 a 22:00 hs")
)
