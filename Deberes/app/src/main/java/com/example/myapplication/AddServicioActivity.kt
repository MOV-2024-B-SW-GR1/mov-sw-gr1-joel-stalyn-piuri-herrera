package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddServicioActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var editTextNombre: EditText
    private lateinit var editTextPlataforma: EditText
    private lateinit var editTextDuracion: EditText
    private lateinit var buttonGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_servicio)

        // Inicializar DatabaseHelper
        dbHelper = DatabaseHelper(this)

        // Referencias a los elementos de la UI
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextPlataforma = findViewById(R.id.editTextPlataforma)
        editTextDuracion = findViewById(R.id.editTextDuracion)
        buttonGuardar = findViewById(R.id.buttonGuardar)

        buttonGuardar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val plataforma = editTextPlataforma.text.toString()
            val duracion = editTextDuracion.text.toString()

            if (nombre.isNotEmpty() && plataforma.isNotEmpty() && duracion.isNotEmpty()) {
                // Insertar el nuevo servicio en la base de datos
                val servicio = Servicio(
                    nombre = nombre,
                    plataforma = plataforma,
                    duracion = duracion
                )
                dbHelper.insertServicio(servicio)

                // Notificar a MainActivity para recargar los servicios
                (applicationContext as MainActivity).refreshServicios()

                // Mostrar mensaje de éxito
                Toast.makeText(this, "Servicio guardado correctamente", Toast.LENGTH_SHORT).show()

                // Volver a MainActivity
                finish()
            } else {
                // Mostrar mensaje de error si los campos están vacíos
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
