package com.example.deber2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.deber2.database.ServicioDAO
import com.example.deber2.models.Servicio

class MainActivity : AppCompatActivity() {
    private lateinit var listViewServicios: ListView
    private lateinit var btnAgregarServicio: Button
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var servicioDAO: ServicioDAO
    private var servicios = mutableListOf<Servicio>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        servicioDAO = ServicioDAO(this)
        listViewServicios = findViewById(R.id.listViewServicios)
        btnAgregarServicio = findViewById(R.id.btnAgregarServicio)

        cargarServicios()

        btnAgregarServicio.setOnClickListener {
            startActivity(Intent(this, FormServicioActivity::class.java))
        }

        listViewServicios.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ServicioDetailActivity::class.java)
            intent.putExtra("servicioId", servicios[position].id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        cargarServicios()
    }

    private fun cargarServicios() {
        servicios = servicioDAO.obtenerServicios().toMutableList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, servicios.map { it.nombre })
        listViewServicios.adapter = adapter
    }
}
