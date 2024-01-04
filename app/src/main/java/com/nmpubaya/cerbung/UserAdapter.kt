package com.nmpubaya.cerbung

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nmpubaya.cerbung.databinding.CardUserBinding
import com.squareup.picasso.Picasso

class UserAdapter(val users: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(val binding: CardUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = CardUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with (holder.binding) {
            val url = users[position].url_profile
            val builder = Picasso.Builder(holder.itemView.context)
            builder.listener { picasso, uri, exception ->  exception.printStackTrace()}
            Picasso.get().load(url).into(imgAvatar)

            txtUser.text = users[position].username
            txtLikes.text = users[position].num_likes.toString()
            txtDate.text = users[position].waktu_gabung.toString()

        }
    }
}