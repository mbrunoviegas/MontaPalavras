package com.example.montapalavras.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.montapalavras.extras.TrieTree

class MainActivityViewModel : ViewModel() {
    val word = MutableLiveData<String>()
    private val nodes = mutableListOf<TrieTree.Node>()
    private val restSting = mutableListOf<String>()
    private val indicies = mutableListOf<Int>()
    private lateinit var trie: TrieTree

    fun getWord(word: String) {
        trie = TrieTree()
        word.forEachIndexed { index, letter ->
            getWord(word.removeRange(index, index + 1), letter.toString())
        }
    }

    private fun getWord(word: String, sequence: String) {
        val node = trie.search(sequence)
        when {
            node == null -> return
            node.completeWord != null && node.numberOfSelections == 1 -> {
                nodes.add(node)
                restSting.add(word)
            }
            else -> {
                word.forEachIndexed { j, _ ->
                    getWord(word.removeRange(j, j + 1), "$sequence${word[j]}")
                }
            }
        }
    }

}