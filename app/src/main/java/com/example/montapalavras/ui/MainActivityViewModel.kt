package com.example.montapalavras.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.montapalavras.extras.TrieTree
import java.util.*

class MainActivityViewModel : ViewModel() {
    val mostValuableNode = MutableLiveData<TrieTree.Node>()
    val restOfMostValuableWord = MutableLiveData<String>()
    val resetLayout = MutableLiveData<Boolean>()
    private val nodes = mutableListOf<TrieTree.Node>()
    private val restSting = mutableMapOf<String, String>()
    private val trie = TrieTree()

    fun getWord(word: String) {
        word.forEachIndexed { index, letter ->
            getWord(word.removeRange(index, index + 1), letter.toString())
        }

        if (nodes.isNotEmpty()) {
            mostValuableNode.value = getMostValuabletNode()
            restOfMostValuableWord.value = restSting[mostValuableNode.value!!.completeWord]
            nodes.clear()
            restSting.clear()
            trie.clearSelectedWord()
        } else {
            resetLayout.value = true
        }
    }

    private fun getWord(word: String, sequence: String) {
        val node = trie.search(sequence)
        when {
            node == null -> return
            node.completeWord != null -> {
                nodes.add(node)
                restSting[node.completeWord!!] = word.toUpperCase(Locale.getDefault())
            }
            else -> {
                word.forEachIndexed { j, _ ->
                    getWord(word.removeRange(j, j + 1), "$sequence${word[j]}")
                }
            }
        }
    }

    private fun getMostValuabletNode(): TrieTree.Node {
        var mostValuableNode = nodes[0]
        for (i in 1 until nodes.size)
            if (mostValuableNode.weight!! <= nodes[i].weight!!)
                mostValuableNode = if (mostValuableNode.weight == nodes[i].weight!!)
                    getSmaller(mostValuableNode, nodes[i])
                else
                    nodes[i]
        return mostValuableNode
    }

    private fun getSmaller(node1: TrieTree.Node, node2: TrieTree.Node) =
        if (node1.completeWord!!.length <= node2.completeWord!!.length) node1 else node2
}