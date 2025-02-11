package com.example.deber2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.deber2.database.ServicioDAO
import com.example.deber2.models.Servicio

class FormServicioActivity : AppCompatActivity() {
    private lateinit var txtNombreServicio: EditText
    private lateinit var txtCantidadSuscriptores: EditText
    private lateinit var txtPlataformaServicio: EditText
    private lateinit var btnGuardarServicio: Button
    private lateinit var servicioDAO: ServicioDAO
    private var servicioId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_servicio)

        servicioDAO = ServicioDAO(this)
        txtNombreServicio = findViewById(R.id.etNombreServicio)
        txtCantidadSuscriptores = findViewById(R.id.etCantidadSuscriptores)
        txtPlataformaServicio = findViewById(R.id.etPlataformaServicio)
        btnGuardarServicio = findViewById(R.id.btnGuardarServicio)

        servicioId = intent.getIntExtra("servicioId", -1)

        if (servicioId != -1) {
            val servicio = servicioDAO.obtenerServicioPorId(servicioId)
            servicio?.let {
                txtNombreServicio.setText(it.nombre)
                txtCantidadSuscriptores.setText(it.cantidadSuscripciones)
                txtPlataformaServicio.setText(it.descripcion)
            }
        }

        btnGuardarServicio.setOnClickListener {
            val nombre = txtNombreServicio.text.toString()
            val cantidadSuscriptores = txtCantidadSuscriptores.text.toString().toIntOrNull() ?: 0
            val plataforma = txtPlataformaServicio.text.toString()

            if (servicioId == -1) {
                // Nuevo servicio (ID será autoincremental en la BD)
                val nuevoServicio = Servicio(id = 0.toString(), nombre = nombre, cantidadSuscripciones = cantidadSuscriptores.toString(), descripcion = plataforma)
                servicioDAO.insertarServicio(nuevoServicio)
            } else {
                // Editar servicio existente
                val servicioEditado = Servicio(id = servicioId.toString(), nombre = nombre, cantidadSuscripciones = cantidadSuscriptores.toString(), descripcion = plataforma)
                servicioDAO.actualizarServicio(servicioId, servicioEditado)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
