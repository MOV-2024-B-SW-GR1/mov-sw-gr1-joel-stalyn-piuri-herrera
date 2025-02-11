package com.example.a04_deber01.ui.cuentabanco

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a04_deber01.data.model.CuentaBanco
import com.example.a04_deber01.databinding.ActivityCuentaBancoListBinding
import com.example.a04_deber01.repository.BancoRepository
import com.example.a04_deber01.ui.tipotransaccion.TipoTransaccionListActivity
import com.example.a04_deber01.ui.cuentabanco.adapter.CuentaBancoAdapter
import com.example.a04_deber01.utils.showCuentaBancoDialog
import com.example.a04_deber01.utils.showUpdateCuentaBancoDialog
import kotlinx.coroutines.launch

class CuentaBancoListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCuentaBancoListBinding
    private val repository = BancoRepository()
    private val viewModel = CuentaBancoViewModel(repository)
    private lateinit var adapter: CuentaBancoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCuentaBancoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = CuentaBancoAdapter(
            onItemClick = { cuentaBanco ->
                startActivity(
                    TipoTransaccionListActivity.newIntent(this, cuentaBanco.id)
                )
            },
            onDeleteClick = { cuentaBancoId ->
                viewModel.deleteCuentaBanco(cuentaBancoId)
                refreshData()
            },
            onEditClick = { cuentaBanco ->
                showUpdateCuentaBancoDialog(
                    context = this,
                    cuentaBanco = cuentaBanco
                ) { nombre, fechaCreacion, activa, numeroDepartamentos, presupuestoAnual ->
                    viewModel.updateCuentaBanco(
                        cuentaBanco.copy(
                            nombre = nombre,
                            fechaCreacion = fechaCreacion,
                            activa = activa,
                            numeroDepartamentos = numeroDepartamentos,
                            presupuestoAnual = presupuestoAnual
                        )
                    )
                    refreshData()
                }
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CuentaBancoListActivity)
            adapter = this@CuentaBancoListActivity.adapter
        }
        refreshData()
    }

    private fun refreshData() {
        lifecycleScope.launch {
            val cuentasBanco = repository.getAllCuentasBanco()
            adapter.submitList(cuentasBanco)
        }
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            showCuentaBancoDialog(
                context = this
            ) { nombre, fechaCreacion, activa, numeroDepartamentos, presupuestoAnual ->
                viewModel.insertCuentaBanco(
                    CuentaBanco(
                        id = 0,
                        nombre = nombre,
                        fechaCreacion = fechaCreacion,
                        activa = activa,
                        numeroDepartamentos = numeroDepartamentos,
                        presupuestoAnual = presupuestoAnual
                    )
                )
                refreshData()
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CuentaBancoListActivity::class.java)
        }
    }
}