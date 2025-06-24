// Paquete que agrupa los modelos de datos usados por la app
package com.grupo14.tpfinalg14.models

// Clase de datos que representa una entrega pendiente o vencimiento asociado a una materia
data class Vencimiento(
    val tipo: String,     // Tipo de entrega o evaluación (Ej: "TP 2", "Tarea", "TP final")
    val fecha: String     // Fecha límite de entrega (Ej: "15/08/2025")
)