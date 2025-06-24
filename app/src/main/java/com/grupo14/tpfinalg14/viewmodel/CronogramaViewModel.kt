package com.grupo14.tpfinalg14.viewmodel

// Importaciones necesarias para trabajar con arquitectura MVVM: LiveData y ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grupo14.tpfinalg14.models.Cronograma
import com.grupo14.tpfinalg14.repository.CronogramaRepository

// ViewModel que expone los datos del cronograma semanal de cursadas.
// Sirve como puente entre el repositorio de datos (modelo) y la interfaz de usuario (fragmentos).
class CronogramaViewModel : ViewModel() {

    // MutableLiveData privada que contiene la lista completa de cronogramas (días y horarios por materia)
    private val _cronograma = MutableLiveData<List<Cronograma>>()

    // LiveData pública, inmutable, que los fragmentos pueden observar para recibir actualizaciones
    val cronograma: LiveData<List<Cronograma>> get() = _cronograma

    // Bloque que se ejecuta al crear la instancia del ViewModel.
    // Carga los datos desde el repositorio e inicializa la LiveData.
    init {
        _cronograma.value = CronogramaRepository.listaMaterias
    }
}