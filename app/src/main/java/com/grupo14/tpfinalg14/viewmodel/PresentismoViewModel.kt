package com.grupo14.tpfinalg14.viewmodel

// Importaciones necesarias para aplicar arquitectura MVVM con LiveData y acceder al repositorio de datos
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grupo14.tpfinalg14.models.Presentismo
import com.grupo14.tpfinalg14.repository.PresentismoRepository

// ViewModel encargado de manejar la lógica de UI y estado de selección para el módulo de presentismo.
// Gestiona qué materia está activa, entrega listas de presentismo y calcula resúmenes por materia.
class PresentismoViewModel : ViewModel() {

    // LiveData que representa la materia actualmente seleccionada (null si se está mostrando el resumen general)
    private val _materiaSeleccionada = MutableLiveData<String?>()
    val materiaSeleccionada: LiveData<String?> = _materiaSeleccionada

    // Selecciona una materia concreta y activa la vista de detalle
    fun seleccionarMateria(nombre: String) {
        _materiaSeleccionada.value = nombre
    }

    // Deselecciona la materia actual y vuelve a la vista de resumen general
    fun deseleccionarMateria() {
        _materiaSeleccionada.value = null
    }

    // Devuelve la lista de nombres de materias disponibles.
    // En este caso, está codificado manualmente, pero podría extraerse desde un repositorio si fuese dinámico.
    fun obtenerMaterias(): List<String> = listOf(
        "Administración de bases de datos",
        "Técnicas de programación",
        "Lógica computacional",
        "Metodología de pruebas de sistemas"
    )

    // Entrega un resumen de presentismo por materia.
    // Solo incluye el último registro de cada materia, obtenido desde el repositorio.
    fun obtenerResumen(): List<Presentismo> = PresentismoRepository.getUltimaFechaPorMateria()

    // Entrega la lista completa de asistencias para una materia específica,
    // agregando primero un resumen con el porcentaje de asistencia (como si fuera una card cabecera)
    fun obtenerDetalle(materia: String): List<Presentismo> {
        val lista = PresentismoRepository.getPresentismoPorMateria(materia)         // Trae todos los registros
        val porcentaje = PresentismoRepository.calcularPorcentajePresentismo(materia) // Calcula asistencia total
        val resumen = Presentismo(materia, "", "$porcentaje%")                        // Se genera un item resumen
        return listOf(resumen) + lista                                                // Se concatena al principio
    }
}