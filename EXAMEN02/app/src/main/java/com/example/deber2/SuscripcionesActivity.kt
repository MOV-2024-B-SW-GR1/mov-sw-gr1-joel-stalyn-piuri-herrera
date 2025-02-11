package com.example.deber2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.deber2.database.SuscripcionDAO
import com.example.deber2.models.Suscripcion

class SuscripcionesActivity : AppCompatActivity() {
    private lateinit var listViewSuscripciones: ListView
    private lateinit var btnAgregarSuscripcion: Button
    private lateinit var btnSalir: Button
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var suscripcionDAO: SuscripcionDAO
    private var suscripciones = mutableListOf<Suscripcion>()
    private var servicioId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suscripciones)

        servicioId = intent.getIntExtra("servicioId", -1)
        suscripcionDAO = SuscripcionDAO(this)
        listViewSuscripciones = findViewById(R.id.listViewSuscripciones)
        btnAgregarSuscripcion = findViewById(R.id.btnAgregarSuscripcion)
        btnSalir = findViewById(R.id.btnSalir)

        cargarSuscripciones()

        btnAgregarSuscripcion.setOnClickListener {
            val intent = Intent(this, FormSuscripcionActivity::class.java)
            intent.putExtra("servicioId", servicioId)
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // Cierra actividades previas
            startActivity(intent)
            finish() // Cierra SuscripcionesActivity
        }

        listViewSuscripciones.setOnItemClickListener { _, _, position, _ ->
            val suscripcionSeleccionada = suscripciones[position]
            val intent = Intent(this, SuscripcionDetailActivity::class.java)
            intent.putExtra("suscripcionId", suscripcionSeleccionada.id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        cargarSuscripciones()
    }

    private fun cargarSuscripciones() {
        suscripciones = suscripcionDAO.obtenerSuscripcionesPorServicio(servicioId).toMutableList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, suscripciones.map { "${it.nombre}" })
        listViewSuscripciones.adapter = adapter
    }
}
