package com.nmpubaya.cerbung

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(var id: Int,
                var username: String,
                var password: String,
                var url_profile: String) : Parcelable
