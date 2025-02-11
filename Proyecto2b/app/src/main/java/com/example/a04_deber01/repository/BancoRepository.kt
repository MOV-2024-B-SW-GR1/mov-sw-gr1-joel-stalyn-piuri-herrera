package com.example.a04_deber01.repository

import com.example.a04_deber01.data.local.BancoDataStore
import com.example.a04_deber01.data.model.TipoTransaccion
import com.example.a04_deber01.data.model.CuentaBanco
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BancoRepository {
    suspend fun getAllCuentasBanco() = withContext(Dispatchers.IO) {
        BancoDataStore.cuentasBanco.toList()
    }

    suspend fun getTiposTransaccionByCuentaBanco(cuentaBancoId: Int) = withContext(Dispatchers.IO) {
        BancoDataStore.cuentasBanco.find { it.id == cuentaBancoId }?.tiposTransaccion ?: emptyList()
    }

    suspend fun insertCuentaBanco(cuentaBanco: CuentaBanco) = withContext(Dispatchers.IO) {
        BancoDataStore.addCuentaBanco(cuentaBanco)
    }

    suspend fun updateCuentaBanco(cuentaBanco: CuentaBanco) = withContext(Dispatchers.IO) {
        BancoDataStore.updateCuentaBanco(cuentaBanco)
    }

    suspend fun deleteCuentaBanco(cuentaBancoId: Int) = withContext(Dispatchers.IO) {
        BancoDataStore.deleteCuentaBanco(cuentaBancoId)
    }

    suspend fun insertTipoTransaccion(tipoTransaccion: TipoTransaccion, cuentaBancoId: Int) = withContext(Dispatchers.IO) {
        BancoDataStore.addTipoTransaccion(tipoTransaccion, cuentaBancoId)
    }

    suspend fun updateTipoTransaccion(tipoTransaccion: TipoTransaccion, cuentaBancoId: Int) = withContext(Dispatchers.IO) {
        BancoDataStore.updateTipoTransaccion(tipoTransaccion, cuentaBancoId)
    }

    suspend fun deleteTipoTransaccion(tipoTransaccionId: Int, cuentaBancoId: Int) = withContext(Dispatchers.IO) {
        BancoDataStore.deleteTipoTransaccion(tipoTransaccionId, cuentaBancoId)
    }
}
