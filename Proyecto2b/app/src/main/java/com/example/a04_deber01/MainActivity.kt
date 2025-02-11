package com.example.a04_deber01

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a04_deber01.databinding.ActivityMainBinding
import com.example.a04_deber01.ui.cuentabanco.CuentaBancoListActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usamos ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ejemplo simple: Ir a la lista de cuentas de banco
        binding.btnIrCuentaBanco.setOnClickListener {
            startActivity(Intent(this, CuentaBancoListActivity::class.java))
        }
    }
}
