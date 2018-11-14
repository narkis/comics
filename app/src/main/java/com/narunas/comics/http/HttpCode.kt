package com.narunas.comics.http


import com.google.common.hash.Hashing
import com.narunas.comics.viemodel.Constants
import com.narunas.comics.viemodel.TopSection
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import java.net.URLEncoder

class HttpCode : HttpCodeInterface {

    private val charSet = charset("UTF-8")
    private val ENC  = "UTF-8"

    /** hardcoded params here **/
    private val format = "?format=comic"
    private val formatType = "&formatType=comic"
    private val limit = "&limit="
    private val date = "&dateDescriptor="

    companion object {
        val TAG: String = HttpCode::class.java.simpleName
    }

    override fun fetchJson(section: TopSection) :StringBuffer? {

        var response: StringBuffer? = null
        val baseUrl = Constants.BASE
        val devPublicKey = URLEncoder.encode(Constants.PU, ENC)
        val devPrivateKey = URLEncoder.encode(Constants.PR, ENC)
        val devTs = URLEncoder.encode(System.currentTimeMillis().toString(), ENC)
        val devHash = Hashing.md5().hashString(devTs + devPrivateKey + devPublicKey, charSet)


        val mUrl = URL(
            baseUrl
                    + format
                    + formatType
                    + limit
                    + section.requestCount
                    + date
                    + section.forDate
                    + "&ts="
                    + devTs
                    + "&apikey="
                    + devPublicKey
                    + "&hash="
                    + devHash)

        with(mUrl.openConnection() as HttpURLConnection) {

            try {

                setRequestProperty("If-None-Match", section.eTag)
                requestMethod = "GET"


                when (responseCode) {

                    HTTP_OK -> {

                        BufferedReader(InputStreamReader(inputStream)).use {

                            response = StringBuffer()
                            var inputLine = it.readLine()
                            while (inputLine != null) {
                                response?.append(inputLine)
                                inputLine = it.readLine()
                            }

                            it.close()

                        }

                    } else -> {

                       /** handle error **/
                    }
                }

            } finally {

                disconnect()
            }
        }


        return response
    }

}

