package com.nmpubaya.cerbung

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nmpubaya.cerbung.databinding.CerbungItemBinding
import com.squareup.picasso.Picasso

class CerbungAdapters(): RecyclerView.Adapter<CerbungAdapters.CerbungViewHolder>() {
    class CerbungViewHolder(val binding: CerbungItemBinding): RecyclerView.ViewHolder(binding.root)
    companion object {
        val URL = "url"
        val CARD_TITLE = "cardtitle"
        val CARD_AUTHOR = "cardauthor"
        val CARD_PERMISSION = "cardpermission"
        val ICON_LIST = "iconlist"
        val ICON_LIKE = "iconlike"
        val CARD_GENRE = "cardgenre"
        val CARD_SHORT_DESCRIPTION = "cardshortdescription"
        val CARD_STORY = "cardstory"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CerbungViewHolder {
        val binding = CerbungItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CerbungViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return Global.cerbung.size
    }

    override fun onBindViewHolder(holder: CerbungViewHolder, position: Int) {
        val url = Global.cerbung[position].url
        val cardTitle= Global.cerbung[position].title
        val cardAuthor= Global.cerbung[position].author
        val cardPermission= Global.cerbung[position].permission
        val iconList= Global.cerbung[position].icon_list
        val iconLike= Global.cerbung[position].icon_like
        val cardGenre = Global.cerbung[position].genre
        val cardShortDescription= Global.cerbung[position].short_description
        val cardStory= Global.cerbung[position].story

        with(holder.binding) {
            val builder = Picasso.Builder(holder.itemView.context)
            builder.listener { picasso, uri, exception -> exception.printStackTrace() }
            builder.build().load(url).into(imgCerbung)

            txtCardTitle.text = cardTitle
            txtCardAuthor.text = cardAuthor
            txtIconList.text = iconList.toString()
            txtIconLike.text = iconLike.toString()
            txtCardGenre.text = cardGenre
            txtCardShortDescription.text = cardShortDescription

            btnRead.setOnClickListener {
                val intent = Intent(it.context, CerbungFullStoryPage::class.java)
                intent.putExtra(URL, url)
                intent.putExtra(CARD_TITLE, cardTitle)
                intent.putExtra(CARD_AUTHOR, cardAuthor)
                intent.putExtra(CARD_PERMISSION, cardPermission)
                intent.putExtra(ICON_LIST, iconList)
                intent.putExtra(ICON_LIKE, iconLike)
                intent.putExtra(CARD_GENRE, cardGenre)
                intent.putExtra(CARD_SHORT_DESCRIPTION, cardShortDescription)
                intent.putExtra(CARD_STORY, cardStory)
                it.context.startActivity(intent)
            }
        }
    }
}