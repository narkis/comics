package com.narunas.comics.components.code


import com.google.common.hash.Hashing
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import java.net.URLEncoder

class HttpCode : HttpCodeInterface{


    override fun fetchJson() :StringBuffer? {

        val baseUrl = "https://gateway.marvel.com:443/v1/public/comics"
        val devKey = URLEncoder.encode("274049d15c01f3897fe1f51f96761092", "UTF-8")
        val devPrKey = URLEncoder.encode("274049d15c01f3897fe1f51f96761092", "UTF-8")
        val devTs = URLEncoder.encode(System.currentTimeMillis().toString(), "UTF-8")

        val charSet = charset("UTF-8")
        val devHash = Hashing.md5().hashString(devTs + devKey + devPrKey, charSet)


        val mUrl = URL(baseUrl+ "?ts=" + devTs + "&apikey=" + devKey + "&hash=" + devHash)
        val response: StringBuffer? = null

        with(mUrl.openConnection() as HttpURLConnection) {

            connectTimeout = 6000

            when (responseCode) {
                HTTP_OK -> {

                }
                else -> {

                    return null
                }
            }


            BufferedReader(InputStreamReader(inputStream)).use {

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response?.append(inputLine)
                    inputLine = it.readLine()
                }

                it.close()

            }

            disconnect()
            return response
        }
    }
}