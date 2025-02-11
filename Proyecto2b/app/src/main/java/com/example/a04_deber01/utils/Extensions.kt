package com.example.a04_deber01.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.a04_deber01.databinding.DialogAddTipoTransaccionBinding
import com.example.a04_deber01.databinding.DialogAddCuentaBancoBinding
import com.example.a04_deber01.data.model.TipoTransaccion
import com.example.a04_deber01.data.model.CuentaBanco

/**
 * Diálogo para crear nueva Cuenta de Banco
 */
fun showCuentaBancoDialog(
    context: Context,
    onSave: (String, String, Boolean, Int, Double) -> Unit
) {
    val binding = DialogAddCuentaBancoBinding.inflate(LayoutInflater.from(context))

    AlertDialog.Builder(context)
        .setTitle("Nueva Cuenta de Banco")
        .setView(binding.root)
        .setPositiveButton("Guardar") { _, _ ->
            val nombre = binding.etNombre.text.toString()
            val fechaCreacion = binding.etFechaCreacion.text.toString()
            val activa = binding.cbActiva.isChecked
            val numeroDepartamentos = binding.etNumeroDepartamentos.text.toString().toIntOrNull() ?: 0
            val presupuestoAnual = binding.etPresupuestoAnual.text.toString().toDoubleOrNull() ?: 0.0

            if (nombre.isNotEmpty() && fechaCreacion.isNotEmpty() && numeroDepartamentos > 0 && presupuestoAnual > 0.0) {
                onSave(nombre, fechaCreacion, activa, numeroDepartamentos, presupuestoAnual)
            }
        }
        .setNegativeButton("Cancelar", null)
        .show()
}

/**
 * Diálogo para actualizar Cuenta de Banco
 */
fun showUpdateCuentaBancoDialog(
    context: Context,
    cuentaBanco: CuentaBanco,
    onUpdate: (String, String, Boolean, Int, Double) -> Unit
) {
    val binding = DialogAddCuentaBancoBinding.inflate(LayoutInflater.from(context))

    // Rellenamos los campos con los valores actuales
    binding.etNombre.setText(cuentaBanco.nombre)
    binding.etFechaCreacion.setText(cuentaBanco.fechaCreacion)
    binding.cbActiva.isChecked = cuentaBanco.activa
    binding.etNumeroDepartamentos.setText(cuentaBanco.numeroDepartamentos.toString())
    binding.etPresupuestoAnual.setText(cuentaBanco.presupuestoAnual.toString())

    AlertDialog.Builder(context)
        .setTitle("Editar Cuenta de Banco")
        .setView(binding.root)
        .setPositiveButton("Actualizar") { _, _ ->
            val nombre = binding.etNombre.text.toString()
            val fechaCreacion = binding.etFechaCreacion.text.toString()
            val activa = binding.cbActiva.isChecked
            val numeroDepartamentos = binding.etNumeroDepartamentos.text.toString().toIntOrNull() ?: 0
            val presupuestoAnual = binding.etPresupuestoAnual.text.toString().toDoubleOrNull() ?: 0.0

            if (nombre.isNotEmpty() && fechaCreacion.isNotEmpty() && numeroDepartamentos > 0 && presupuestoAnual > 0.0) {
                onUpdate(nombre, fechaCreacion, activa, numeroDepartamentos, presupuestoAnual)
            }
        }
        .setNegativeButton("Cancelar", null)
        .show()
}

/**
 * Diálogo para crear nuevo Tipo de Transacción
 */
fun showTipoTransaccionDialog(
    context: Context,
    onSave: (String, Int, Boolean, Int, Double) -> Unit
) {
    val binding = DialogAddTipoTransaccionBinding.inflate(LayoutInflater.from(context))

    AlertDialog.Builder(context)
        .setTitle("Nuevo Tipo de Transacción")
        .setView(binding.root)
        .setPositiveButton("Guardar") { _, _ ->
            val nombre = binding.etNombreTipoTransaccion.text.toString()
            val anioInicio = binding.etAnioInicio.text.toString().toIntOrNull() ?: 0
            val acreditada = binding.cbAcreditada.isChecked
            val creditosTotales = binding.etCreditosTotales.text.toString().toIntOrNull() ?: 0
            val mensualidad = binding.etMensualidad.text.toString().toDoubleOrNull() ?: 0.0

            if (nombre.isNotEmpty() && anioInicio > 0 && creditosTotales > 0 && mensualidad > 0.0) {
                onSave(nombre, anioInicio, acreditada, creditosTotales, mensualidad)
            }
        }
        .setNegativeButton("Cancelar", null)
        .show()
}

/**
 * Diálogo para actualizar Tipo de Transacción
 */
fun showUpdateTipoTransaccionDialog(
    context: Context,
    tipoTransaccion: TipoTransaccion,
    onUpdate: (String, Int, Boolean, Int, Double) -> Unit
) {
    val binding = DialogAddTipoTransaccionBinding.inflate(LayoutInflater.from(context))

    // Rellenamos los campos con los valores actuales
    binding.etNombreTipoTransaccion.setText(tipoTransaccion.nombre)
    binding.etAnioInicio.setText(tipoTransaccion.anioInicio.toString())
    binding.cbAcreditada.isChecked = tipoTransaccion.acreditada
    binding.etCreditosTotales.setText(tipoTransaccion.creditosTotales.toString())
    binding.etMensualidad.setText(tipoTransaccion.mensualidad.toString())

    AlertDialog.Builder(context)
        .setTitle("Editar Tipo de Transacción")
        .setView(binding.root)
        .setPositiveButton("Actualizar") { _, _ ->
            val nombre = binding.etNombreTipoTransaccion.text.toString()
            val anioInicio = binding.etAnioInicio.text.toString().toIntOrNull() ?: 0
            val acreditada = binding.cbAcreditada.isChecked
            val creditosTotales = binding.etCreditosTotales.text.toString().toIntOrNull() ?: 0
            val mensualidad = binding.etMensualidad.text.toString().toDoubleOrNull() ?: 0.0

            if (nombre.isNotEmpty() && anioInicio > 0 && creditosTotales > 0 && mensualidad > 0.0) {
                onUpdate(nombre, anioInicio, acreditada, creditosTotales, mensualidad)
            }
        }
        .setNegativeButton("Cancelar", null)
        .show()
}
