package com.narunas.comics.http


interface HttpCodeInterface {

    fun fetchJson(eTag: String) :StringBuffer?
}