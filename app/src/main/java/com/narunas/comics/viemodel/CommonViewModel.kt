package com.narunas.comics.viemodel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.common.hash.Hashing
import com.narunas.comics.App
import com.narunas.comics.components.ComicsHttpComponent
import com.narunas.comics.components.DaggerComicsHttpComponent
import com.narunas.comics.components.code.HttpCode
import com.narunas.comics.components.modules.ComicsHttpModule
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject
import kotlin.concurrent.thread

class CommonViewModel : ViewModel(){

    @Inject lateinit var httpHandler: HttpCode

    fun buildFunctionality() {

        createHttpComponent().inject(this)

    }


    fun fetchHttpData() {

        val baseUrl = "https://gateway.marvel.com:443/v1/public/comics"
        val devKey = URLEncoder.encode("274049d15c01f3897fe1f51f96761092", "UTF-8")
        val devPrKey = URLEncoder.encode("274049d15c01f3897fe1f51f96761092", "UTF-8")
        val devTs = URLEncoder.encode(System.currentTimeMillis().toString(), "UTF-8")

        val charSet = charset("UTF-8")
        val devHash = Hashing.md5().hashString(devTs + devKey + devPrKey, charSet)


        val mUrl = URL(baseUrl+ "?ts=" + devTs + "&apikey=" + devKey + "&hash=" + devHash)

        thread {
            Log.d("TEST", httpHandler.fetchJson().toString())
        }
    }


    private fun createHttpComponent() : ComicsHttpComponent =
        DaggerComicsHttpComponent.builder()
            .comicsHttpModule(ComicsHttpModule())
            .build()
}