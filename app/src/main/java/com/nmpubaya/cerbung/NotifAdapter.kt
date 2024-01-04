package com.nmpubaya.cerbung

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nmpubaya.cerbung.databinding.CardNotifBinding

class NotifAdapter(val notifications: ArrayList<Notification>): RecyclerView.Adapter<NotifAdapter.NotifViewHolder>() {
    class NotifViewHolder(val binding: CardNotifBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val binding = CardNotifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotifViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        with (holder.binding) {
            txtDateNotif.text = notifications[position].waktu_notif.toString()
            if (notifications[position].notifications_id == 1) {
                txtRequest.text = "${notifications[position].username} request access cerbung"
            }
            btnAction.setOnClickListener {

            }
        }

    }
}