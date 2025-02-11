package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ServicioDetailActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerViewSuscripciones: RecyclerView
    private lateinit var buttonAddSuscripcion: Button
    private lateinit var suscripcionAdapter: SuscripcionAdapter
    private var servicioId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicio_detail)

        dbHelper = DatabaseHelper(this)

        // Obtener servicioId de la actividad anterior
        servicioId = intent.getLongExtra("SERVICIO_ID", 0)

        // Configuración del RecyclerView
        recyclerViewSuscripciones = findViewById(R.id.recyclerViewSuscripciones)
        recyclerViewSuscripciones.layoutManager = LinearLayoutManager(this)

        // Cargar las suscripciones del servicio
        loadSuscripciones()

        // Configurar el botón para agregar suscripción
        buttonAddSuscripcion = findViewById(R.id.buttonAddSuscripcion)
        buttonAddSuscripcion.setOnClickListener {
            // Pasar el servicioId a la actividad de agregar suscripción
            val intent = Intent(this, AddSuscripcionActivity::class.java)
            intent.putExtra("SERVICIO_ID", servicioId) // Asegúrate de pasar el servicioId
            startActivity(intent)
        }
    }

    // Método para cargar las suscripciones de este servicio
    private fun loadSuscripciones() {
        val suscripciones = dbHelper.getSuscripcionesByServicio(servicioId) // Obtener las suscripciones del servicio
        suscripcionAdapter = SuscripcionAdapter(suscripciones) // Crear el adaptador con las suscripciones
        recyclerViewSuscripciones.adapter = suscripcionAdapter // Asignar el adaptador al RecyclerView
        suscripcionAdapter.notifyDataSetChanged() // Notificar que los datos han cambiado
    }
}



