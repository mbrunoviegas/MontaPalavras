package com.example.montapalavras.extras

import java.text.Normalizer
import java.util.*

class TrieTree {
    data class Node(
        var completeWord: String? = null,
        var weight: Int? = null,
        val letters: MutableMap<Char, Node> = mutableMapOf()
    )

    private val root = Node()
    private val words: List<String> = listOf(
        "Abacaxi",
        "Manada",
        "mandar",
        "porta",
        "mesa",
        "Dado",
        "Mangas",
        "Já",
        "coisas",
        "radiografia",
        "matemática",
        "Drogas",
        "prédios",
        "implementação",
        "computador",
        "balão",
        "Xícara",
        "Tédio",
        "faixa",
        "Livro",
        "deixar",
        "superior",
        "Profissão",
        "Reunião",
        "Prédios",
        "Montanha",
        "Botânica",
        "Banheiro",
        "Caixas",
        "Xingamento",
        "Infestação",
        "Cupim",
        "Premiada",
        "empanada",
        "Ratos",
        "Ruído",
        "Antecedente",
        "Empresa",
        "Emissário",
        "Folga",
        "Fratura",
        "Goiaba",
        "Gratuito",
        "Hídrico",
        "Homem",
        "Jantar",
        "Jogos",
        "Montagem",
        "Manual",
        "Nuvem",
        "Neve",
        "Operação",
        "Ontem",
        "Pato",
        "Pé",
        "viagem",
        "Queijo",
        "Quarto",
        "Quintal",
        "Solto",
        "rota",
        "Selva",
        "Tatuagem",
        "Tigre",
        "Uva",
        "Último",
        "Vitupério",
        "Voltagem",
        "Zangado",
        "Zombaria",
        "Dor"
    )
    private val weights: MutableMap<Char, Int> =
        mutableMapOf(
            Pair('a', 1),
            Pair('e', 1),
            Pair('i', 1),
            Pair('o', 1),
            Pair('u', 1),
            Pair('n', 1),
            Pair('r', 1),
            Pair('t', 1),
            Pair('l', 1),
            Pair('s', 1),
            Pair('w', 2),
            Pair('d', 2),
            Pair('g', 2),
            Pair('b', 3),
            Pair('c', 3),
            Pair('m', 3),
            Pair('p', 3),
            Pair('f', 4),
            Pair('h', 4),
            Pair('v', 4),
            Pair('j', 8),
            Pair('x', 8),
            Pair('q', 10),
            Pair('z', 10),
        )
    private val alreadySelected = mutableListOf<String>()

    init {
        words.forEach { word ->
            insert(word)
        }
    }

    private fun CharSequence.unaccent(): String {
        val regexUnnaccent = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return regexUnnaccent.replace(temp, "")
    }

    private fun insert(word: String) {
        var currentNode = root
        var weight = 0
        word.toLowerCase(Locale.getDefault()).unaccent().forEach { letter ->
            if (currentNode.letters[letter] == null)
                currentNode.letters[letter] = Node()
            weight += getWeight(letter)!!
            currentNode = currentNode.letters[letter]!!
        }
        currentNode.completeWord = word.toUpperCase(Locale.getDefault())
        currentNode.weight = weight
    }

    fun search(word: String): Node? {
        var currentNode = root
        word.forEach { letter ->
            if (currentNode.letters[letter] == null)
                return null
            currentNode = currentNode.letters[letter]!!
        }
        if (currentNode.completeWord != null)
            return if (!alreadySelected.contains(currentNode.completeWord)) {
                alreadySelected.add(currentNode.completeWord!!)
                currentNode
            } else null
        return currentNode
    }

    private fun getWeight(letter: Char) = weights[letter]

    fun clearSelectedWord() {
        alreadySelected.clear()
    }
}