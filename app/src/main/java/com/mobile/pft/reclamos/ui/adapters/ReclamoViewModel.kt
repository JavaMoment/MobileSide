package com.mobile.pft.reclamos.ui.adapters

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mobile.pft.UtecApplication
import com.mobile.pft.model.EventoDTO
import com.mobile.pft.model.PatchDTO
import com.mobile.pft.model.ReclamoDTO
import com.mobile.pft.model.StatusReclamoDTO
import com.mobile.pft.reclamos.data.ReclamosRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ReclamoUiState {
    data class Success(
        val reclamo: ReclamoDTO
    ) : ReclamoUiState
    data object Loading : ReclamoUiState
    data object Error : ReclamoUiState
}

class ReclamoViewModel(
    private val reclamosRepository: ReclamosRepository
) : ViewModel() {
    var uiState: ReclamoUiState by mutableStateOf(ReclamoUiState.Loading)
        private set

    var statusReclamo: List<StatusReclamoDTO> = emptyList<StatusReclamoDTO>()
        private set

    var eventos: List<EventoDTO> = emptyList<EventoDTO>()
        private set

    init {
        getStatusReclamo()
        getEventos()
    }

    fun getReclamo(idReclamo: Long) {
        viewModelScope.launch {
            Log.i("ReclamoViewModel", "Consultando por el reclamo con ID: $idReclamo")
            uiState = try {
                ReclamoUiState.Success(reclamosRepository.getReclamoBy(idReclamo))
            } catch (e: IOException) {
                ReclamoUiState.Error
            } catch (e: HttpException) {
                ReclamoUiState.Error
            }
        }
    }

    fun getStatusReclamo() {
        viewModelScope.launch {
            statusReclamo = try {
                Log.i("ReclamoView","Consultando api por los posibles status de un reclamo.")
                reclamosRepository.getStatusReclamo()
            } catch (e: IOException) {
                emptyList<StatusReclamoDTO>()
            } catch (e: HttpException) {
                emptyList<StatusReclamoDTO>()
            }
            Log.i("ReclamoView","Status conseguidos: $statusReclamo")
        }
    }

    fun getEventos() {
        viewModelScope.launch {
            eventos = try {
                Log.i("ReclamoView","Consultando api por los eventos disponibles.")
                reclamosRepository.getEventos()
            } catch (e: IOException) {
                emptyList<EventoDTO>()
            } catch (e: HttpException) {
                emptyList<EventoDTO>()
            }
            Log.i("ReclamoView","Eventos conseguidos: $eventos")
        }
    }

    fun partialUpdate(reclamoId: Long, op: PatchDTO) {

        viewModelScope.launch {
            Log.i("ReclamoView","Intentando actualizar el atributo: ${op.path}, del reclamo #$reclamoId")
            val reclamo = reclamosRepository.partialUpdate(reclamoId, op)
            Log.i("ReclamoView","Reclamo actualizado: $reclamo")
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as UtecApplication)
                val reclamosRepository = application.container.reclamosRepository
                ReclamoViewModel(reclamosRepository = reclamosRepository)
            }
        }
    }
}
