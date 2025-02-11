package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddSuscripcionActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var editTextUsuarioId: EditText
    private lateinit var editTextFechaInicio: EditText
    private lateinit var editTextMetodoPago: EditText
    private lateinit var buttonGuardarSuscripcion: Button
    private var servicioId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_suscripcion)

        dbHelper = DatabaseHelper(this)

        // Obtener servicioId de la actividad anterior
        servicioId = intent.getLongExtra("SERVICIO_ID", 0)

        // Verificar que el servicioId se haya recibido correctamente
        if (servicioId == 0L) {
            Toast.makeText(this, "Error: servicioId no disponible.", Toast.LENGTH_SHORT).show()
        }

        // Referencias a los elementos de la UI
        editTextUsuarioId = findViewById(R.id.editTextUsuarioId)
        editTextFechaInicio = findViewById(R.id.editTextFechaInicio)
        editTextMetodoPago = findViewById(R.id.editTextMetodoPago)
        buttonGuardarSuscripcion = findViewById(R.id.buttonGuardarSuscripcion)

        buttonGuardarSuscripcion.setOnClickListener {
            val usuarioId = editTextUsuarioId.text.toString().toLongOrNull()
            val fechaInicio = editTextFechaInicio.text.toString()
            val metodoPago = editTextMetodoPago.text.toString()

            if (usuarioId != null && fechaInicio.isNotEmpty() && metodoPago.isNotEmpty()) {
                // Crear la suscripción con servicioId
                val suscripcion = Suscripcion(
                    id = 0, // El id es autoincremental, no necesitas pasarlo
                    usuarioId = usuarioId,
                    fechaInicio = fechaInicio,
                    metodoPago = metodoPago,
                    servicioId = servicioId // Aquí pasamos el servicioId
                )

                // Insertar la suscripción
                val result = dbHelper.insertSuscripcion(suscripcion)

                // Verificar si se insertó correctamente
                if (result > 0) {
                    Toast.makeText(this, "Suscripción guardada correctamente", Toast.LENGTH_SHORT).show()
                    // Volver a la actividad de detalle del servicio
                    val intent = Intent(this, ServicioDetailActivity::class.java)
                    intent.putExtra("SERVICIO_ID", servicioId)
                    startActivity(intent)
                    finish() // Regresar a la pantalla anterior
                } else {
                    Toast.makeText(this, "Error al guardar la suscripción", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Mostrar mensaje de error si algún campo está vacío o inválido
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
