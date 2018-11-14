package com.narunas.comics.viemodel

import com.narunas.comics.gson.ComicsSet

data class TopSection(var timeStramp: Long) {

    var sectionIndex: Int = -1
    var eTag: String = ""
    var title: String = " --- DEFAULT TITLE --- "
    var forDate: String = ""
    var requestCount: Int = -1
    var comicsSet: ComicsSet? = null


}