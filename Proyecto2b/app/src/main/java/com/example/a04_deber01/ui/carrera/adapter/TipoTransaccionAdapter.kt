package com.example.a04_deber01.ui.tipotransaccion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a04_deber01.data.model.TipoTransaccion
import com.example.a04_deber01.databinding.ItemTipoTransaccionBinding

class TipoTransaccionAdapter(
    private val onDeleteClick: (Int) -> Unit,
    private val onEditClick: (TipoTransaccion) -> Unit
) : ListAdapter<TipoTransaccion, TipoTransaccionAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemTipoTransaccionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tipoTransaccion: TipoTransaccion) {
            binding.tvNombreTipoTransaccion.text = tipoTransaccion.nombre
            binding.tvAnioInicio.text = "Año Inicio: ${tipoTransaccion.anioInicio}"
            binding.tvAcreditada.text = "Acreditada: ${if (tipoTransaccion.acreditada) "Sí" else "No"}"
            binding.tvCreditosTotales.text = "Créditos: ${tipoTransaccion.creditosTotales}"
            binding.tvMensualidad.text = "Mensualidad: $${tipoTransaccion.mensualidad}"

            binding.btnDeleteTipoTransaccion.setOnClickListener {
                onDeleteClick(tipoTransaccion.id)
            }
            binding.btnEditTipoTransaccion.setOnClickListener {
                onEditClick(tipoTransaccion)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTipoTransaccionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<TipoTransaccion>() {
        override fun areItemsTheSame(oldItem: TipoTransaccion, newItem: TipoTransaccion) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: TipoTransaccion, newItem: TipoTransaccion) = oldItem == newItem
    }
}