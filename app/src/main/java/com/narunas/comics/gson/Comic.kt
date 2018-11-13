package com.narunas.comics.gson

import com.google.gson.annotations.SerializedName

data class Comic(var et: String) {

    @SerializedName("title")
    var title: String = ""

}