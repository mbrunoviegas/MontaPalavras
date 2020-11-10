package com.example.montapalavras.ui

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.montapalavras.R
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
        viewModel.mostValuableNode.observe(this, { mostValuableNode ->
            with(binding) {
                partionMain.run {
                    with(rvWord) {
                        if (adapter == null) {
                            layoutManager = GridLayoutManager(this@MainActivity, 7)
                            adapter =
                                LettersAdapter().apply { updateData(mostValuableNode.completeWord!!) }
                            visibility = View.VISIBLE
                        } else
                            (adapter as LettersAdapter).updateData(mostValuableNode.completeWord!!)
                    }
                    val textScore = "Palavra de ${mostValuableNode.weight.toString()} pontos"
                    txtWordScore.text = textScore
                }

                txtInstruction.visibility = View.GONE
                partionMain.rlPartion.visibility = View.VISIBLE
                btnOk.isEnabled = true
            }
        })

        viewModel.restOfMostValuableWord.observe(this, { word ->
            with(binding) {
                partionMain.run {
                    with(rvRestLetters) {
                        if (adapter == null) {
                            layoutManager = GridLayoutManager(this@MainActivity, 7)
                            adapter =
                                LettersAdapter().apply { updateData(word) }
                            visibility = View.VISIBLE
                        } else
                            (adapter as LettersAdapter).updateData(word)
                    }
                }
            }
        })

        viewModel.resetLayout.observe(this, { reset ->
            with(binding) {
                if (reset) {
                    partionMain.rlPartion.visibility = View.GONE
                    txtInstruction.text = getString(R.string.tente_novamente)
                    txtInstruction.visibility = View.VISIBLE
                }
                btnOk.isEnabled = true
            }
        })
    }

    private fun setupActivity() {
        with(binding) {
            btnOk.setOnClickListener(this@MainActivity)
            txtInstruction.typeface = Typeface.createFromAsset(assets, "font/Roboto-Regular.ttf")
        }
    }

    override fun onClick(v: View?) {
        with(binding) {
            when (v) {
                btnOk -> {
                    val letters: String
                    if (edtLetters.text.toString().isNotEmpty()) {
                        binding.btnOk.isEnabled = false
                        letters = edtLetters.text.toString()
                        viewModel.getWord(letters)
                    }
                }
            }
        }
    }
}