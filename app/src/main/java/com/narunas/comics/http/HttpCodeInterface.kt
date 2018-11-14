package com.narunas.comics.http

import com.narunas.comics.viemodel.TopSection


interface HttpCodeInterface {

    fun fetchJson(section: TopSection) :StringBuffer?
}