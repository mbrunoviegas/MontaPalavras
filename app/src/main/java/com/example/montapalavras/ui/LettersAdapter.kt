package com.example.montapalavras.ui

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.montapalavras.R
import com.example.montapalavras.databinding.ItemLetterBinding

class LettersAdapter :
    RecyclerView.Adapter<LettersAdapter.LetterViewHolder>() {
    private var word: String = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_letter, parent, false)
        return LetterViewHolder(view)
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        holder.bind(word[position])
    }

    override fun getItemCount() = word.length

    fun updateData(word: String) {
        this.word = word
        notifyDataSetChanged()
    }

    class LetterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLetterBinding.bind(itemView)

        fun bind(letter: Char) {
            with(binding) {
                txtLetter.typeface =
                    Typeface.createFromAsset(itemView.context.assets, "font/Roboto-Medium.ttf")
                txtLetter.text = letter.toString()
            }
        }
    }
}