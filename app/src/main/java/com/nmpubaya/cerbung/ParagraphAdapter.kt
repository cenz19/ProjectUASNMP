package com.nmpubaya.cerbung

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nmpubaya.cerbung.databinding.CardParagraphBinding

class ParagraphAdapter(val paragraphs: ArrayList<Paragraph>): RecyclerView.Adapter<ParagraphAdapter.ParagraphViewHolder>() {
    class ParagraphViewHolder(val binding: CardParagraphBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParagraphViewHolder {
        val binding = CardParagraphBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParagraphViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return paragraphs.size
    }

    override fun onBindViewHolder(holder: ParagraphViewHolder, position: Int) {
        
    }
}