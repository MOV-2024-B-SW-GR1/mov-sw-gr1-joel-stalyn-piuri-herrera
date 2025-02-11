package com.example.a04_deber01.ui.tipotransaccion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a04_deber01.data.model.TipoTransaccion
import com.example.a04_deber01.databinding.ActivityTipoTransaccionListBinding
import com.example.a04_deber01.repository.BancoRepository
import com.example.a04_deber01.ui.tipotransaccion.adapter.TipoTransaccionAdapter
import com.example.a04_deber01.utils.showTipoTransaccionDialog
import com.example.a04_deber01.utils.showUpdateTipoTransaccionDialog
import kotlinx.coroutines.launch

class TipoTransaccionListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTipoTransaccionListBinding
    private lateinit var adapter: TipoTransaccionAdapter
    private lateinit var viewModel: TipoTransaccionViewModel

    private var cuentaBancoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipoTransaccionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cuentaBancoId = intent.getIntExtra(EXTRA_CUENTA_BANCO_ID, 0)
        viewModel = TipoTransaccionViewModel(BancoRepository(), cuentaBancoId)

        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = TipoTransaccionAdapter(
            onDeleteClick = { tipoTransaccionId ->
                viewModel.deleteTipoTransaccion(tipoTransaccionId, cuentaBancoId)
                refreshData()
            },
            onEditClick = { tipoTransaccion ->
                showUpdateTipoTransaccionDialog(
                    context = this,
                    tipoTransaccion = tipoTransaccion
                ) { nombre, anioInicio, acreditada, creditosTotales, mensualidad ->
                    viewModel.updateTipoTransaccion(
                        tipoTransaccion.copy(
                            nombre = nombre,
                            anioInicio = anioInicio,
                            acreditada = acreditada,
                            creditosTotales = creditosTotales,
                            mensualidad = mensualidad
                        ),
                        cuentaBancoId
                    )
                    refreshData()
                }
            }
        )

        binding.recyclerViewTipoTransaccion.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTipoTransaccion.adapter = adapter

        refreshData()
    }

    private fun setupFab() {
        binding.fabTipoTransaccion.setOnClickListener {
            showTipoTransaccionDialog(
                context = this
            ) { nombre, anioInicio, acreditada, creditosTotales, mensualidad ->
                viewModel.insertTipoTransaccion(
                    TipoTransaccion(
                        id = 0,
                        nombre = nombre,
                        anioInicio = anioInicio,
                        acreditada = acreditada,
                        creditosTotales = creditosTotales,
                        mensualidad = mensualidad
                    ),
                    cuentaBancoId
                )
                refreshData()
            }
        }
    }

    private fun refreshData() {
        lifecycleScope.launch {
            val tiposTransaccion = viewModel.getTiposTransaccion()
            adapter.submitList(tiposTransaccion)
        }
    }

    companion object {
        private const val EXTRA_CUENTA_BANCO_ID = "extra_cuenta_banco_id"

        fun newIntent(context: Context, cuentaBancoId: Int): Intent {
            return Intent(context, TipoTransaccionListActivity::class.java).apply {
                putExtra(EXTRA_CUENTA_BANCO_ID, cuentaBancoId)
            }
        }
    }
}
