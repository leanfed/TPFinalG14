package com.grupo14.tpfinalg14.viewmodel

// Importaciones necesarias para arquitectura MVVM, corutinas y repositorio de datos
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grupo14.tpfinalg14.repository.CalificacionesRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

// ViewModel que administra la materia actualmente visible en el HomeFragment.
// Rota automáticamente cada 10 segundos entre las materias disponibles.
// También implementa CoroutineScope para controlar ciclos de actualización en segundo plano.
class HomeViewModel : ViewModel(), CoroutineScope {

    // Job que controla el ciclo de vida de la corutina
    private val job = Job()

    // El contexto que usará la corutina: hilo principal + job asociado al ViewModel
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    // Lista de nombres de materias, extraída del repositorio de calificaciones
    private val materias = CalificacionesRepository.listaMaterias.map { it.nombre }

    // Índice que indica cuál materia se está mostrando actualmente
    private var index = 0

    // LiveData que expone el nombre de la materia actual para ser observado desde la UI
    private val _materiaActual = MutableLiveData<String>()
    val materiaActual: LiveData<String> get() = _materiaActual

    // Cuando se crea el ViewModel, inicia el ciclo automático de rotación de materias
    init {
        rotarMateriaCada10Segundos()
    }

    // Lanza una corutina que actualiza la materia visible cada 10 segundos
    private fun rotarMateriaCada10Segundos() {
        launch {
            while (isActive) { // Se ejecuta siempre que el ViewModel esté vivo
                _materiaActual.value = materias.getOrNull(index) // Publica la materia actual
                index = (index + 1) % materias.size               // Avanza al siguiente índice circularmente
                delay(10000)                                      // Espera 10 segundos antes de actualizar de nuevo
            }
        }
    }

    // Se ejecuta automáticamente cuando el ViewModel es destruido (por ejemplo al cambiar de pantalla)
    // Aquí cancelamos el job para evitar fugas de memoria y detener la corutina activa
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}