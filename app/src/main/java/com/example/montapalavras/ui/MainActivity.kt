package com.example.montapalavras.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.example.montapalavras.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by lazy {
        MainActivityViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupActivity()
    }

    private fun setupObservers() {
        viewModel.mostValuableWord.observe(this, Observer { word ->
            with(binding) {
                plMain.rvWord.run {
                    if (adapter == null) {
//                        layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = LettersAdapter().apply { updateData(word) }
                        visibility = View.VISIBLE
                    } else
                        (adapter as LettersAdapter).updateData(word)
                }
                txtInstruction.visibility = View.GONE
                plMain.rlMain.visibility = View.VISIBLE
            }
        })

        viewModel.restOfMostValuableWord.observe(this, Observer { word ->
            with(binding) {
                plMain.rvRestLetters.run {
                    if (adapter == null) {
                        adapter = LettersAdapter().apply { updateData(word) }
                        visibility = View.VISIBLE
                    } else
                        (adapter as LettersAdapter).updateData(word)
                }
                txtInstruction.visibility = View.GONE
            }
        })
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