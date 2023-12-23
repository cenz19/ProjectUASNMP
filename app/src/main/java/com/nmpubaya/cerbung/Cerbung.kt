package com.nmpubaya.cerbung

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cerbung(var id:Int,
                   var title:String,
                   var description:String,
                   var num_likes:Int,
                   var access: Int,
                   var num_paragraph: Int,
                   var genre: String,
                   var url_gambar:String,
                   var waktu_dibuat: String,
                   var username: String
                   ) : Parcelable
{
    override fun toString(): String {
        return title
    }
}
