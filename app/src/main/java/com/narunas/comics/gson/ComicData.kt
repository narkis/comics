package com.narunas.comics.gson

import com.google.gson.annotations.SerializedName

data class ComicData (var et: String){

    @SerializedName("offset")
    var offset: Int = -1

    @SerializedName("limit")
    var limit: Int =-1

    @SerializedName("total")
    val total: Int = -1

    @SerializedName("count")
    val count: Int = -1

    @SerializedName("results")
    var comics = arrayListOf(Comic(et))



}