package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerViewServicios: RecyclerView
    private lateinit var buttonAddServicio: Button
    private lateinit var servicioAdapter: ServicioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        //dbHelper.deleteAllServicios()
        //dbHelper.deleteAllSuscripciones()

        recyclerViewServicios = findViewById(R.id.recyclerViewServicios)
        recyclerViewServicios.layoutManager = LinearLayoutManager(this)

        // Cargar los servicios al iniciar la actividad
        loadServicios()

        buttonAddServicio = findViewById(R.id.buttonAddServicio)
        buttonAddServicio.setOnClickListener {
            // Abrir la actividad para agregar un nuevo servicio
            val intent = Intent(this, AddServicioActivity::class.java)
            startActivity(intent)
        }
    }

    // Cargar los servicios de la base de datos y mostrarlos en el RecyclerView
    private fun loadServicios() {
        val servicios = dbHelper.getAllServicios() // Método para obtener todos los servicios
        servicioAdapter = ServicioAdapter(servicios) { servicio ->
            // Al hacer clic en un servicio, pasar el servicioId a la siguiente actividad
            Log.d("MainActivity", "Servicio ID: ${servicio.id}") // Verificar que el servicioId se pasa correctamente
            val intent = Intent(this, ServicioDetailActivity::class.java)
            intent.putExtra("SERVICIO_ID", servicio.id) // Pasar servicioId
            startActivity(intent)
        }
        recyclerViewServicios.adapter = servicioAdapter
        servicioAdapter.notifyDataSetChanged() // Notificar al RecyclerView que los datos han cambiado
    }

    // Método para actualizar los servicios después de agregar un nuevo servicio
    fun refreshServicios() {
        loadServicios() // Recargar los servicios desde la base de datos
    }
}
