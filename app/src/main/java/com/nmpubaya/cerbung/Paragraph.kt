package com.nmpubaya.cerbung

import java.util.Date

data class Paragraph(var id:Int,
                     var isi:String,
                     var num_like:Int,
                     var waktu_buat:Date,
                     var cerbung_id:Int,
                     var author_id:Int)
