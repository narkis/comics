package com.narunas.comics.gson

interface GsonInterface {

    fun parseResponse(data: StringBuffer?) : ComicsSet
}