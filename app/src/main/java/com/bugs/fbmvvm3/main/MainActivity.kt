package com.bugs.fbmvvm3.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bugs.fbmvvm3.R
import com.bugs.fbmvvm3.State
import com.bugs.fbmvvm3.databinding.ActivityMainBinding
import com.bugs.fbmvvm3.model.Sampel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this, MainViewModelFactory())[MainViewModel::class.java]

        binding.btnSimpan.setOnClickListener(this)

        binding.rvSampel.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun getAllSampel(){
        viewModel.getAllSampel().collect { state ->
            when (state) {
                is State.Loading -> {
                    showToast("Loading")
                }
                is State.Success -> {
                    val adapter = SampelAdapter(this, state.data)
                    binding.rvSampel.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                is State.Failed -> {
                    showToast("Failed ${state.message}")
                }
            }
        }
    }

    private suspend fun addSampel(sampel: Sampel) {
        viewModel.addSampel(sampel).collect { state ->
            when (state) {
                is State.Loading -> {
                    showToast("Loading")
                    binding.btnSimpan.isEnabled = false
                }
                is State.Success -> {
                    showToast("Posted")
                    binding.btnSimpan.isEnabled = true
                    getAllSampel()
                }
                is State.Failed -> {
                    showToast("Failed ${state.message}")
                    binding.btnSimpan.isEnabled = true
                }
            }
        }
    }

    private suspend fun deleteSampel(sampel: Sampel){
        viewModel.deleteSampel(sampel).collect { state ->
            when (state) {
                is State.Loading -> {
                    showToast("Loading")
                }
                is State.Success -> {
                    showToast("Success")
                    getAllSampel()
                }

                is State.Failed -> {
                    showToast(state.message)
                }
            }

        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            binding.btnSimpan.id -> {
                uiScope.launch {
                    addSampel(
                        putSampel()
                    )
                }
            }
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun putSampel() : Sampel {
        val label = binding.etLabel.text.toString()
        val ap1 = binding.etAp1.text.toString().toInt()
        val ap2 = binding.etAp2.text.toString().toInt()
        val ap3 = binding.etAp3.text.toString().toInt()
        val creation = System.currentTimeMillis()

        return Sampel(label, ap1, ap2, ap3, creation)
    }

    fun delSampel(sampel: Sampel) {
        //showToast("Under construction!!")
        uiScope.launch {
            deleteSampel(sampel)
        }
    }

    init {
        uiScope.launch {
            getAllSampel()
        }
    }
}