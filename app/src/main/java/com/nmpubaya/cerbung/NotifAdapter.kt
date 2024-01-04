package com.nmpubaya.cerbung

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nmpubaya.cerbung.databinding.CardNotifBinding

class NotifAdapter(val notifs: ArrayList<Notif>): RecyclerView.Adapter<NotifAdapter.NotifViewHolder>() {
    class NotifViewHolder(val binding: CardNotifBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val binding = CardNotifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotifViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notifs.size
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        with (holder.binding) {
            txtDateNotif.text = notifs[position].waktu_notif.toString()
            if (notifs[position].notifications_id == 1) {
                txtRequest.text = "${notifs[position].username} is requesting access to cerbung"
                btnAction.setText("Action")
            } else {
                btnAction.setText("Other Action")
            }
        }
    }
}