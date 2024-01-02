package com.nmpubaya.cerbung

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmpubaya.cerbung.databinding.CardCerbungBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class CerbungAdapter(val cerbungs: ArrayList<Cerbung>, val users_id: Int): RecyclerView.Adapter<CerbungAdapter.CerbungViewHolder>() {
    class CerbungViewHolder(val binding: CardCerbungBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CerbungViewHolder {
        val binding = CardCerbungBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CerbungViewHolder(binding)
    }

    companion object {
        val KEY_CERBUNG_ID = "cerbung_id"
        val KEY_USERS_ID = "users_id"
    }

    override fun getItemCount(): Int {
        return cerbungs.size
    }

    override fun onBindViewHolder(holder: CerbungViewHolder, position: Int) {
        with (holder.binding) {
            var url = cerbungs[position].url_gambar
            var builder = Picasso.Builder(holder.itemView.context)
            builder.listener {picasso, uri, exception -> exception.printStackTrace()}
            Picasso.get().load(url).into(imgCerbung)

            txtTitleCerbung.text = cerbungs[position].title
            txtAuthorCerbung.text = "by " + cerbungs[position].username
            txtNumParagraph.text = cerbungs[position].num_paragraph.toString()
            txtNumLike.text = cerbungs[position].num_likes.toString()
            txtDeskripsi.text = cerbungs[position].description
            btnRead.setOnClickListener {
                val id = cerbungs[position].id
                val intent = Intent(it.context, ReadCerbungActivity::class.java)
                intent.putExtra(KEY_CERBUNG_ID, id)
                intent.putExtra(KEY_USERS_ID, users_id)
                it.context.startActivity(intent)
            }
        }
    }


}