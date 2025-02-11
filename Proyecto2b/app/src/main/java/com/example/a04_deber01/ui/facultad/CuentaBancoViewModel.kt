package com.example.a04_deber01.ui.cuentabanco

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a04_deber01.data.model.CuentaBanco
import com.example.a04_deber01.repository.BancoRepository
import kotlinx.coroutines.launch

class CuentaBancoViewModel(
    private val repository: BancoRepository
) : ViewModel() {

    fun insertCuentaBanco(cuentaBanco: CuentaBanco) = viewModelScope.launch {
        repository.insertCuentaBanco(cuentaBanco)
    }

    fun updateCuentaBanco(cuentaBanco: CuentaBanco) = viewModelScope.launch {
        repository.updateCuentaBanco(cuentaBanco)
    }

    fun deleteCuentaBanco(cuentaBancoId: Int) = viewModelScope.launch {
        repository.deleteCuentaBanco(cuentaBancoId)
    }
}
