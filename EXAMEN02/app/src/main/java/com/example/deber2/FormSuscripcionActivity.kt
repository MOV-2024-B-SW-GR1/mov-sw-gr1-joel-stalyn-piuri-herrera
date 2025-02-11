package com.example.deber2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.deber2.database.SuscripcionDAO
import com.example.deber2.models.Suscripcion

class FormSuscripcionActivity : AppCompatActivity() {
    private lateinit var txtNombre: EditText
    private lateinit var txtLugar: EditText
    private lateinit var txtTiempo: EditText
    private lateinit var txtCosto: EditText
    private lateinit var txtTotal: EditText
    private lateinit var btnGuardar: Button
    private lateinit var suscripcionDAO: SuscripcionDAO
    private var servicioId: Int = -1
    private var suscripcionId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_suscripcion)

        suscripcionDAO = SuscripcionDAO(this)
        txtNombre = findViewById(R.id.etNombreSuscripcion)
        txtLugar = findViewById(R.id.etLugarSuscripcion)
        txtTiempo = findViewById(R.id.etTiempoSuscripcion)
        txtCosto = findViewById(R.id.etCostoSuscripcion)
        txtTotal = findViewById(R.id.etTotalSuscripcion)
        btnGuardar = findViewById(R.id.btnGuardarSuscripcion)

        servicioId = intent.getIntExtra("servicioId", -1)
        suscripcionId = intent.getIntExtra("suscripcionId", -1)

        // Si se recibió un suscripcionId, cargar datos
        if (suscripcionId != -1) {
            val suscripcion = suscripcionDAO.obtenerSuscripcionPorId(suscripcionId)
            suscripcion?.let {
                txtNombre.setText(it.nombre)
                txtLugar.setText(it.nombre) // Asumiendo que Lugar es el nombre
                txtTiempo.setText(it.duracion.toString())
                txtCosto.setText(it.costo.toString())
                txtTotal.setText(it.costo.toString()) // Asumiendo que Total es igual a Costo
            }
        }

        btnGuardar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val lugar = txtLugar.text.toString()
            val tiempo = txtTiempo.text.toString().toIntOrNull() ?: 0
            val costo = txtCosto.text.toString().toDoubleOrNull() ?: 0.0
            val total = txtTotal.text.toString().toDoubleOrNull() ?: 0.0

            if (suscripcionId == -1) {
                // Nueva suscripción
                val nuevaSuscripcion = Suscripcion(
                    id = 0, // ID autoincremental
                    nombre = nombre,
                    duracion = tiempo,
                    costo = costo,
                    servicioId = servicioId
                )
                suscripcionDAO.insertarSuscripcion(nuevaSuscripcion)
            } else {
                // Editar suscripción existente
                val suscripcionEditada = Suscripcion(
                    id = suscripcionId,
                    nombre = nombre,
                    duracion = tiempo,
                    costo = costo,
                    servicioId = servicioId
                )
                suscripcionDAO.actualizarSuscripcion(suscripcionId, suscripcionEditada)
            }
            // Redirigir a SuscripcionesActivity después de guardar
            val intent = Intent(this, SuscripcionesActivity::class.java)
            intent.putExtra("servicioId", servicioId) // Para que SuscripcionesActivity cargue las suscripciones del servicio
            startActivity(intent)
            finish()
        }
    }
}