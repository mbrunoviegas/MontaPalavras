package com.example.montapalavras.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.montapalavras.R
import com.example.montapalavras.databinding.ActivityMainBinding
import com.example.montapalavras.extras.TrieTree

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by lazy {
        MainActivityViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActivity()
    }

    private fun setupActivity() {
        with(binding) {
            btnOk.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View?) {
        with(binding) {
            when (v) {
                btnOk -> {
                    var letters = ""
                    if (edtLetters.text.toString().isNotEmpty()) {
                        letters = edtLetters.text.toString()
                        viewModel.getWord(letters)
                        Log.i("PALAVRA", letters)
                    }
                }
                else -> {

                }
            }
        }
    }
}