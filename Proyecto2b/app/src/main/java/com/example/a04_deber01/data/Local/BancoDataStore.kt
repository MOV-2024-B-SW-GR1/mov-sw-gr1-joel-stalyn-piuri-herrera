package com.example.a04_deber01.data.local

import com.example.a04_deber01.data.model.TipoTransaccion
import com.example.a04_deber01.data.model.CuentaBanco

object BancoDataStore {
    private var cuentaBancoIdCounter = 1
    private var tipoTransaccionIdCounter = 1

    val cuentasBanco = mutableListOf<CuentaBanco>()

    // Cuentas de Banco
    fun addCuentaBanco(cuentaBanco: CuentaBanco): Boolean {
        val newCuentaBanco = cuentaBanco.copy(id = cuentaBancoIdCounter++)
        cuentasBanco.add(newCuentaBanco)
        return true
    }

    fun updateCuentaBanco(cuentaBanco: CuentaBanco): Boolean {
        val index = cuentasBanco.indexOfFirst { it.id == cuentaBanco.id }
        return if (index != -1) {
            cuentasBanco[index] = cuentaBanco
            true
        } else false
    }

    fun deleteCuentaBanco(cuentaBancoId: Int): Boolean {
        val index = cuentasBanco.indexOfFirst { it.id == cuentaBancoId }
        return if (index != -1) {
            cuentasBanco.removeAt(index)
            true
        } else false
    }

    // Tipos de Transacción
    fun addTipoTransaccion(tipoTransaccion: TipoTransaccion, cuentaBancoId: Int): Boolean {
        val cuentaBancoIndex = cuentasBanco.indexOfFirst { it.id == cuentaBancoId }
        if (cuentaBancoIndex != -1) {
            val newTipoTransaccion = tipoTransaccion.copy(id = tipoTransaccionIdCounter++)
            cuentasBanco[cuentaBancoIndex] = cuentasBanco[cuentaBancoIndex].copy(
                tiposTransaccion = cuentasBanco[cuentaBancoIndex].tiposTransaccion + newTipoTransaccion
            )
            return true
        }
        return false
    }

    fun updateTipoTransaccion(tipoTransaccion: TipoTransaccion, cuentaBancoId: Int): Boolean {
        val cuentaBancoIndex = cuentasBanco.indexOfFirst { it.id == cuentaBancoId }
        if (cuentaBancoIndex != -1) {
            val tiposTransaccionActualizados = cuentasBanco[cuentaBancoIndex].tiposTransaccion.map {
                if (it.id == tipoTransaccion.id) tipoTransaccion else it
            }
            cuentasBanco[cuentaBancoIndex] = cuentasBanco[cuentaBancoIndex].copy(
                tiposTransaccion = tiposTransaccionActualizados
            )
            return true
        }
        return false
    }

    fun deleteTipoTransaccion(tipoTransaccionId: Int, cuentaBancoId: Int): Boolean {
        val cuentaBancoIndex = cuentasBanco.indexOfFirst { it.id == cuentaBancoId }
        if (cuentaBancoIndex != -1) {
            val tiposTransaccionActualizados = cuentasBanco[cuentaBancoIndex].tiposTransaccion.filter {
                it.id != tipoTransaccionId
            }
            cuentasBanco[cuentaBancoIndex] = cuentasBanco[cuentaBancoIndex].copy(
                tiposTransaccion = tiposTransaccionActualizados
            )
            return true
        }
        return false
    }
}
