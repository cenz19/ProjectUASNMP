package com.nmpubaya.cerbung

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class User(var id: Int,
                var username: String,
                var password: String,
                var url_profile: String,
                var waktu_gabung: Date) : Parcelable
