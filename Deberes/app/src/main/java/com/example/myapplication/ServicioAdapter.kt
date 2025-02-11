package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ServicioAdapter(
    private val servicios: List<Servicio>,
    private val onItemClick: (Servicio) -> Unit
) : RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_servicio, parent, false)
        return ServicioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {
        val servicio = servicios[position]
        holder.nombreTextView.text = servicio.nombre
        holder.plataformaTextView.text = servicio.plataforma
        holder.duracionTextView.text = servicio.duracion

        holder.itemView.setOnClickListener {
            // Enviar el servicioId cuando se hace clic en un servicio
            onItemClick(servicio)
        }
    }

    override fun getItemCount(): Int = servicios.size

    class ServicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.textViewServicioNombre)
        val plataformaTextView: TextView = itemView.findViewById(R.id.textViewPlataforma)
        val duracionTextView: TextView = itemView.findViewById(R.id.textViewDuracion)
    }
}
