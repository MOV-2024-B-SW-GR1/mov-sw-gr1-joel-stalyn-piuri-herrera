package com.example.deber2

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.deber2.database.SuscripcionDAO
import com.example.deber2.models.Suscripcion

class SuscripcionDetailActivity : AppCompatActivity() {
    private lateinit var txtSuscripcionNombre: TextView
    private lateinit var btnEditarSuscripcion: Button
    private lateinit var btnEliminarSuscripcion: Button
    private lateinit var suscripcionDAO: SuscripcionDAO

    private var suscripcionId: Int = -1
    private lateinit var suscripcion: Suscripcion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suscripcion_detail)

        txtSuscripcionNombre = findViewById(R.id.txtSuscripcionNombre)
        btnEditarSuscripcion = findViewById(R.id.btnEditarSuscripcion)
        btnEliminarSuscripcion = findViewById(R.id.btnEliminarSuscripcion)

        suscripcionDAO = SuscripcionDAO(this)
        suscripcionId = intent.getIntExtra("suscripcionId", -1)

        if (suscripcionId != -1) {
            suscripcion = suscripcionDAO.obtenerSuscripcionPorId(suscripcionId) ?: return
            txtSuscripcionNombre.text = "${suscripcion.nombre}"
        }

        // Editar suscripción
        btnEditarSuscripcion.setOnClickListener {
            val intent = Intent(this, FormSuscripcionActivity::class.java)
            intent.putExtra("suscripcionId", suscripcionId)
            intent.putExtra("servicioId", suscripcion.servicioId)
            startActivity(intent)
        }

        // Eliminar suscripción
        btnEliminarSuscripcion.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Eliminar Suscripción")
                .setMessage("¿Estás seguro de eliminar la suscripción a ${suscripcion.nombre}?")
                .setPositiveButton("Sí") { _, _ ->
                    suscripcionDAO.eliminarSuscripcion(suscripcionId)
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}
