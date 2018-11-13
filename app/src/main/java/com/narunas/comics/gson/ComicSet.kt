package com.narunas.comics.gson

import com.google.gson.annotations.SerializedName


data class ComicsSet(var ts: Long) {

    @SerializedName("code")
    var code: String =""

    @SerializedName("status")
    var status : String = ""

    @SerializedName("copyright")
    var copyright: String = ""

    @SerializedName("attributionText")
    var attributionText: String = ""

    @SerializedName("attributionHTML")
    var attributionHTML: String = ""

    @SerializedName("etag")
    var etag : String = ""

    @SerializedName("data")
    var data = ComicData(etag)


}