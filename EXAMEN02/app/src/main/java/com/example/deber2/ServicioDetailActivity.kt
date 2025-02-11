package com.example.deber2

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deber2.database.ServicioDAO
import com.example.deber2.models.Servicio

class ServicioDetailActivity : AppCompatActivity() {
    private lateinit var txtServicioNombre: TextView
    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnVerSuscripciones: Button
    private lateinit var btnSalir: Button
    private lateinit var servicioDAO: ServicioDAO
    private var servicioId: Int = -1
    private lateinit var servicio: Servicio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicio_detail)

        servicioDAO = ServicioDAO(this)
        txtServicioNombre = findViewById(R.id.txtServicioNombre)
        btnEditar = findViewById(R.id.btnEditarServicio)
        btnEliminar = findViewById(R.id.btnEliminarServicio)
        btnVerSuscripciones = findViewById(R.id.btnVerSuscripciones)
        btnSalir = findViewById(R.id.btnSalirServicio)

        Log.d("ServicioDetailActivity", "servicioId recibido: $servicioId")
        servicioId = intent.getStringExtra("servicioId")?.toIntOrNull() ?: -1
        servicio = servicioDAO.obtenerServicioPorId(servicioId) ?: return

        if (servicioId == -1) {
            Toast.makeText(this, "Error al obtener servicio", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        txtServicioNombre.text = servicio.nombre

        btnEditar.setOnClickListener {
            val intent = Intent(this, FormServicioActivity::class.java)
            intent.putExtra("servicioId", servicio.id.toInt())  // Asegurar que el ID se pase correctamente
            startActivity(intent)
        }

        btnEliminar.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Eliminar Servicio")
                .setMessage("¿Estás seguro de eliminar ${servicio.nombre}?")
                .setPositiveButton("Sí") { _, _ ->
                    servicioDAO.eliminarServicio(servicioId)
                    setResult(RESULT_OK)  // Indicar que hubo cambios
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }

        btnVerSuscripciones.setOnClickListener {
            val intent = Intent(this, SuscripcionesActivity::class.java)
            intent.putExtra("servicioId", servicio.id.toInt())  // Convertir correctamente
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }
}