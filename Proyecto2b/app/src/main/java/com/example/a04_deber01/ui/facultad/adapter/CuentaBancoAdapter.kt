package com.example.a04_deber01.ui.cuentabanco.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a04_deber01.data.model.CuentaBanco
import com.example.a04_deber01.databinding.ItemCuentaBancoBinding

class CuentaBancoAdapter(
    private val onItemClick: (CuentaBanco) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
    private val onEditClick: (CuentaBanco) -> Unit
) : ListAdapter<CuentaBanco, CuentaBancoAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemCuentaBancoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cuentaBanco: CuentaBanco) {
            binding.tvNombre.text = cuentaBanco.nombre
            binding.tvFechaCreacion.text = "Fecha Creación: ${cuentaBanco.fechaCreacion}"
            binding.tvActiva.text = "Activa: ${if (cuentaBanco.activa) "Sí" else "No"}"
            binding.tvNumeroDepartamentos.text = "Departamentos: ${cuentaBanco.numeroDepartamentos}"
            binding.tvPresupuesto.text = "Presupuesto: $${cuentaBanco.presupuestoAnual}"

            binding.root.setOnClickListener {
                onItemClick(cuentaBanco)
            }
            binding.btnDelete.setOnClickListener {
                onDeleteClick(cuentaBanco.id)
            }
            binding.btnEdit.setOnClickListener {
                onEditClick(cuentaBanco)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCuentaBancoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<CuentaBanco>() {
        override fun areItemsTheSame(oldItem: CuentaBanco, newItem: CuentaBanco) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CuentaBanco, newItem: CuentaBanco) = oldItem == newItem
    }
}
