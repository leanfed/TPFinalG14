package com.grupo14.tpfinalg14.viewmodel

// Importaciones necesarias para MVVM: LiveData, MutableLiveData y ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grupo14.tpfinalg14.repository.MateriaConVencimientos
import com.grupo14.tpfinalg14.repository.VencimientosRepository

// ViewModel para el módulo de Vencimientos.
// Administra el estado de selección de materia y provee acceso a los datos del repositorio.
class VencimientosViewModel : ViewModel() {

    // Lista de materias con sus respectivos vencimientos, obtenida una sola vez del repositorio
    private val materias = VencimientosRepository.listaMaterias

    // LiveData que contiene la materia actualmente seleccionada (o null si se muestra el resumen)
    private val _materiaSeleccionada = MutableLiveData<MateriaConVencimientos?>()

    // LiveData pública para que los fragmentos observen la selección actual de materia
    val materiaSeleccionada: LiveData<MateriaConVencimientos?> get() = _materiaSeleccionada

    // Devuelve la lista completa de materias con vencimientos para construir el resumen
    fun obtenerMaterias(): List<MateriaConVencimientos> = materias

    // Marca como seleccionada una materia según su posición en la lista
    // Esto activa la vista de detalle en el fragmento correspondiente
    fun seleccionarMateria(index: Int) {
        _materiaSeleccionada.value = materias[index]
    }

    // Deselecciona cualquier materia activa y vuelve al modo resumen
    fun deseleccionarMateria() {
        _materiaSeleccionada.value = null
    }
}