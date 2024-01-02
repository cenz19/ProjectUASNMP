package com.nmpubaya.cerbung

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Paragraph(var id:Int,
                     var isi:String,
                     var waktu_buat:Date,
                     var cerbung_id:Int,
                     var author_id:Int) : Parcelable
