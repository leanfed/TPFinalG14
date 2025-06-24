package com.grupo14.tpfinalg14.viewmodel

// Importaciones necesarias para usar LiveData, ViewModel y acceder al repositorio de datos
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grupo14.tpfinalg14.repository.CalificacionesRepository
import com.grupo14.tpfinalg14.repository.MateriaConCalificaciones

// ViewModel asociado al módulo de Calificaciones. Contiene la lógica y el estado para controlar
// si se debe mostrar el resumen general de materias o el detalle de una materia específica.
class CalificacionesViewModel : ViewModel() {

    // Accede a la lista completa de materias con sus calificaciones desde el repositorio
    private val materias = CalificacionesRepository.listaMaterias

    // LiveData que indica si debe mostrarse el resumen general de calificaciones (por defecto: true)
    private val _mostrarResumen = MutableLiveData(true)
    val mostrarResumen: LiveData<Boolean> get() = _mostrarResumen

    // LiveData que mantiene cuál es la materia actualmente seleccionada (si existe)
    private val _materiaSeleccionada = MutableLiveData<MateriaConCalificaciones?>()
    val materiaSeleccionada: LiveData<MateriaConCalificaciones?> get() = _materiaSeleccionada

    // Método que devuelve la lista de todas las materias disponibles para el adaptador
    fun obtenerMaterias(): List<MateriaConCalificaciones> = materias

    // Selecciona una materia según su posición en la lista y activa el modo detalle
    fun seleccionarMateria(index: Int) {
        _materiaSeleccionada.value = materias[index] // Actualiza la materia actual
        _mostrarResumen.value = false                // Oculta el resumen general
    }

    // Regresa al modo resumen, limpiando la materia seleccionada
    fun volverAlResumen() {
        _materiaSeleccionada.value = null   // Quita la selección actual
        _mostrarResumen.value = true        // Vuelve a mostrar la lista general
    }
}