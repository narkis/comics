package com.narunas.comics.viemodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.narunas.comics.components.ComicsHttpComponent
import com.narunas.comics.components.DaggerComicsHttpComponent
import com.narunas.comics.components.modules.ComicsGsonModule
import com.narunas.comics.http.HttpCode
import com.narunas.comics.components.modules.ComicsHttpModule
import com.narunas.comics.gson.Comic
import com.narunas.comics.gson.GsonCode
import javax.inject.Inject
import kotlin.concurrent.thread

class CommonViewModel : ViewModel() {

    @Inject
    lateinit var httpHandler: HttpCode
    @Inject
    lateinit var gsonParser: GsonCode

    private var eTag: String = ""

    companion object {
        val Comics: MutableLiveData<ArrayList<Comic>> = MutableLiveData()
    }

    init {

        createHttpComponent().inject(this)
    }

    fun fetchHttpData() {

        thread {

            val response = httpHandler.fetchJson(eTag)
            val comics = gsonParser.parseResponse(response)
            eTag = comics.etag
            Comics.postValue(comics.data.comics)

        }

    }

    /** dagger http component **/
    private fun createHttpComponent(): ComicsHttpComponent {
        return DaggerComicsHttpComponent.builder()
            .comicsHttpModule(ComicsHttpModule())
            .comicsGsonModule(ComicsGsonModule())
            .build()
    }


}