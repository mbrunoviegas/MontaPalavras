package com.example.montapalavras.extras

import java.text.Normalizer
import java.util.*

class TrieTree {
    data class Node(
        var completeWord: String? = null,
        var numberOfSelections: Int = 0,
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

    init {
        words.forEach { word ->
            insert(word)
        }
    }

    fun CharSequence.unaccent(): String {
        val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return REGEX_UNACCENT.replace(temp, "")
    }

    fun getRoot(): Node {
        return root
    }

    private fun insert(word: String) {
        var currentNode = root
        word.toLowerCase(Locale.getDefault()).unaccent().forEach { letter ->
            if (currentNode.letters[letter] == null)
                currentNode.letters[letter] = Node()
            currentNode = currentNode.letters[letter]!!
        }
        currentNode.completeWord = word
    }

    fun search(word: String): Node? {
        var currentNode = root
        word.forEach { letter ->
            if (currentNode.letters[letter] == null)
                return null
            currentNode = currentNode.letters[letter]!!
        }
        if(currentNode.completeWord != null)
            currentNode.numberOfSelections++
        return currentNode
    }
}