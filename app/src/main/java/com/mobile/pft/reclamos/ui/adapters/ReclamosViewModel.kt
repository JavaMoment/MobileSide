package com.mobile.pft.reclamos.ui.adapters

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mobile.pft.UtecApplication
import com.mobile.pft.model.ReclamoDTO
import com.mobile.pft.model.StatusReclamoDTO
import com.mobile.pft.reclamos.data.ReclamosRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ReclamosUiState {
    data class Success(
        val reclamos: List<ReclamoDTO>
    ) : ReclamosUiState
    data object Loading : ReclamosUiState
    data object Error : ReclamosUiState
}

class ReclamosViewModel(
    private val reclamosRepository: ReclamosRepository,
    //private val user: UsuarioDTO
) : ViewModel() {
    var uiState: ReclamosUiState by mutableStateOf(ReclamosUiState.Loading)
        private set

    var statusReclamo: List<StatusReclamoDTO> = emptyList<StatusReclamoDTO>()
        private set

    /**
     * Conseguir la info de la api de inmediato.
     */
    init {
        getReclamos()
        getStatusReclamo()
    }

    fun getReclamos() {
        viewModelScope.launch {
            uiState = ReclamosUiState.Loading
            uiState = try {
                Log.i("ReclamosView","Consultando api por los reclamos.")
                ReclamosUiState.Success(reclamosRepository.getReclamos())
            } catch (e: IOException) {
                ReclamosUiState.Error
            } catch (e: HttpException) {
                ReclamosUiState.Error
            }
        }
    }

    fun getStatusReclamo() {
        viewModelScope.launch {
            statusReclamo = try {
                Log.i("ReclamosView","Consultando api por los posibles status de un reclamo.")
                reclamosRepository.getStatusReclamo()
            } catch (e: IOException) {
                emptyList<StatusReclamoDTO>()
            } catch (e: HttpException) {
                emptyList<StatusReclamoDTO>()
            }
            Log.i("ReclamosView","Status conseguidos: $statusReclamo")
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UtecApplication)
                val reclamosRepository = application.container.reclamosRepository
                ReclamosViewModel(reclamosRepository = reclamosRepository)
            }
        }
    }
}
