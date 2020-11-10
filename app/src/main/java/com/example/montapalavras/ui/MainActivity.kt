package com.example.montapalavras.ui

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.montapalavras.R
import com.example.montapalavras.databinding.ActivityMainBinding

/**
 * A lógica do programa se baseia em receber uma sequência de carácteres
 * e utilizando essa sequência, percorrer uma árvore Trie utilizando
 * uma recursividade indireta para encontrar todas as palavras possíveis
 * de serem formadas. Durante a execução, eu sempre removo da palavra origial,
 * o caráctere que está sendo testado para compor a nova sequência, para evitar
 * que ele seja repetido. Sempre que achar uma nova palavra, eu adiciono
 * o nó dessa palavra em uma lista de nós e o resto da sequência em uma lista
 * que contem o resto das sequências.
 * @see MainActivityViewModel.getWord
 *
 * Após encontrar todas as palavras possíveis, o programa irá verificar
 * qual palavra tem o maior peso. Caso ocorra empate ele dará como
 * selecionada a menor palavra.
 * @see MainActivityViewModel.getMostValuabletNode
 * @see MainActivityViewModel.getSmaller
 *
 * Eu todos os métodos citados acima, eu optei por trabalhar diretamente
 * com o nó da árvore. Uma vez que ele já contém as palavras completas
 * e o peso de cada palavra. Então eu simplesmente retorno um nó,
 * e com base no peso e no tamanho da palavra que está nesse nó,
 * eu concluo qual é o "nó mais valioso". Caso a sequência informada
 * não seja suficiente para encontrar nenhuma palavra no banco de palavras,
 * eu peço ao usuário para digitar novamente.
 *
 * A contagem dos pontos de cada palavra, é feita na hora que eu instancio
 * minha viewModel. Uma vez instanciada, minha viewModel instancia uma árvore
 * Trie, que em seu bloco 'init', irá inserir todas as palavras do meu
 * banco de palavras na árvore trie. Nesse momento, para cada caráctere
 * de cada palavra, ele verificará seu peso e somar em uma variável. Ao
 * final da inserção de cada palavra, o *nó folha* receberá tanto o peso
 * quanto a palavra completa.
 * @see MainActivityViewModel.trie
 *
 * Utilizei 2 recylclerView para fazer a apresentação de cada
 * caráctere, tanto da palavra formada quanto das letras restante.
 * Eu usei o GridLayoutManager para limitir o número de carácteres
 * que eu gostaria de mostrar em cada linha. Utilizei um máximo de 7
 * para cada RecyclerView. Além disso optei por usar LiveData e
 * a ViewModel para separar as responsabilidade de lógica do dos dados da view.
 *
 * @author Marcelo Bruno Viegas
 *
 */

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