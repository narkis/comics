package com.narunas.comics.gson

import com.google.gson.annotations.SerializedName

data class ComicImage(var comicId: String) {

    @SerializedName("path")
    var path: String = ""

    @SerializedName("extension")
    var extention: String = ""
}