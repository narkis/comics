package com.narunas.comics.gson

import com.google.gson.Gson

class GsonCode : GsonInterface {

    override fun parseResponse(data: StringBuffer?): ComicsSet? {

        val gson = Gson()
        val comics: ComicsSet? = gson.fromJson(data.toString(), ComicsSet::class.java)
        return comics

    }
}