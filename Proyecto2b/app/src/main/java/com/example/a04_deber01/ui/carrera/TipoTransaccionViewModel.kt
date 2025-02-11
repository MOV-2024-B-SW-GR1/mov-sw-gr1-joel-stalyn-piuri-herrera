package com.example.a04_deber01.ui.tipotransaccion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a04_deber01.data.model.TipoTransaccion
import com.example.a04_deber01.repository.BancoRepository
import kotlinx.coroutines.launch

class TipoTransaccionViewModel(
    private val repository: BancoRepository,
    private val cuentaBancoId: Int
) : ViewModel() {

    suspend fun getTiposTransaccion() = repository.getTiposTransaccionByCuentaBanco(cuentaBancoId)

    fun insertTipoTransaccion(tipoTransaccion: TipoTransaccion, cuentaBancoId: Int) = viewModelScope.launch {
        repository.insertTipoTransaccion(tipoTransaccion, cuentaBancoId)
    }

    fun updateTipoTransaccion(tipoTransaccion: TipoTransaccion, cuentaBancoId: Int) = viewModelScope.launch {
        repository.updateTipoTransaccion(tipoTransaccion, cuentaBancoId)
    }

    fun deleteTipoTransaccion(tipoTransaccionId: Int, cuentaBancoId: Int) = viewModelScope.launch {
        repository.deleteTipoTransaccion(tipoTransaccionId, cuentaBancoId)
    }
}
