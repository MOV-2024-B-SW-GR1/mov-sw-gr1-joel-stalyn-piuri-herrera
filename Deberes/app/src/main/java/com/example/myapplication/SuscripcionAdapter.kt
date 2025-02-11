package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SuscripcionAdapter(
    private val suscripciones: List<Suscripcion>
) : RecyclerView.Adapter<SuscripcionAdapter.SuscripcionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuscripcionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_suscripcion, parent, false)
        return SuscripcionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SuscripcionViewHolder, position: Int) {
        val suscripcion = suscripciones[position]
        holder.fechaInicioTextView.text = suscripcion.fechaInicio
        holder.metodoPagoTextView.text = suscripcion.metodoPago
    }

    override fun getItemCount(): Int = suscripciones.size

    class SuscripcionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fechaInicioTextView: TextView = itemView.findViewById(R.id.textViewFechaInicio)
        val metodoPagoTextView: TextView = itemView.findViewById(R.id.textViewMetodoPago)
    }
}
