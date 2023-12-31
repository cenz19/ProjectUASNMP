package com.nmpubaya.cerbung

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nmpubaya.cerbung.databinding.CardFollowingBinding
import com.squareup.picasso.Picasso

class FollowCerbungAdapter(val cerbungs: ArrayList<Cerbung>): RecyclerView.Adapter<FollowCerbungAdapter.FollowCerbungViewHolder>() {
    class FollowCerbungViewHolder(val binding: CardFollowingBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowCerbungViewHolder {
        val binding = CardFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowCerbungViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cerbungs.size
    }

    override fun onBindViewHolder(holder: FollowCerbungViewHolder, position: Int) {
        with (holder.binding) {
            val url = cerbungs[position].url_gambar
            val builder = Picasso.Builder(holder.itemView.context)
            builder.listener {picasso, uri, exception -> exception.printStackTrace()}
            Picasso.get().load(url).into(imgCerbung)

            txtTitle.text = cerbungs[position].title
            txtAuthor.text = "by " + cerbungs[position].username
            txtLastUpdate.text = "Last update: " + cerbungs[position].waktu_dibuat

            cardViewFollow.setOnClickListener {
                val id = cerbungs[position].id
                val intent = Intent(it.context, ReadCerbungActivity::class.java)
                intent.putExtra(CerbungAdapter.KEY_CERBUNG_ID, id)
                it.context.startActivity(intent)
            }
        }
    }
}