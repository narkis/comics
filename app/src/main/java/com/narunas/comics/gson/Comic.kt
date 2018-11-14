package com.narunas.comics.gson

import com.google.gson.annotations.SerializedName

data class Comic(var et: String) {

    @SerializedName("id")
    var id: String = ""

    @SerializedName("title")
    var title: String = ""

    @SerializedName("thumbnail")
    var thumb = ComicThumb(id)

    @SerializedName("images")
    var images = arrayListOf(ComicImage(id))






}