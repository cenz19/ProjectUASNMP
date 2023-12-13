package com.nmpubaya.cerbung

import android.provider.MediaStore.Audio.Playlists
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nmpubaya.cerbung.databinding.CardPlaylistBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request

class PlaylistAdapter(val playlists: ArrayList<Cerbung>): RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {
    class PlaylistViewHolder(val binding: CardPlaylistBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding = CardPlaylistBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return PlaylistViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val url = playlists[position].image_url
        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener { picasso, uri, exception -> exception.printStackTrace() }
        Picasso.get().load(url).into(holder.binding.imgPlayList)
        with(holder.binding) {
            txtTitle.text = playlists[position].title
            txtSubtitle.text = playlists[position].subtitle
            txtContent.text = playlists[position].description
            btnLike.text = playlists[position].num_likes.toString() + " LIKES"
            btnLike.setOnClickListener {
                val q = Volley.newRequestQueue(holder.itemView.context)
                val url = "https://ubaya.me/native/160421072/set_likes.php"
                val stringRequest = object: StringRequest(com.android.volley.Request.Method.POST,
                    url,
                    {
                        // make sure to change num_likes to var in Playlist data class
                        playlists[holder.adapterPosition].num_likes++
                        holder.binding.btnLike.text = playlists[holder.adapterPosition].num_likes.toString() + " LIKES"

                        Log.d("cekparams", it)
                    },
                    {
                        Log.d("cekparams", it.message.toString())
                    }){
                    override fun getParams(): MutableMap<String, String>? {
                        //ini data yg dikirimkan ke server dalam bentuk hashmap
                        val params = HashMap<String,String>()
                        params["id"] = playlists[holder.adapterPosition].id.toString()
                        return params
                    }
                }
                q.add(stringRequest)
            }
        }
    }
}