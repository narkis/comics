package com.narunas.comics.viemodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.extensions.R.id.async
import android.provider.Settings
import com.narunas.comics.components.ComicsHttpComponent
import com.narunas.comics.components.DaggerComicsHttpComponent
import com.narunas.comics.components.modules.ComicsGsonModule
import com.narunas.comics.http.HttpCode
import com.narunas.comics.components.modules.ComicsHttpModule
import com.narunas.comics.gson.GsonCode
import java.lang.Thread.sleep
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.thread
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class CommonViewModel : ViewModel() {



    @Inject
    lateinit var httpHandler: HttpCode

    @Inject
    lateinit var gsonParser: GsonCode

    companion object {

        enum class FOR_DATE {
            thisWeek,
            nextWeek,
            lastWeek

        }

        val TopComicsSections: MutableLiveData<HashMap<Int, TopSection>> = MutableLiveData()
    }

    init {


        createHttpComponent().inject(this)

    }


    fun fetchHttpData() {








            val data = buildSections()
            val iterator = data.iterator()
            iterator.forEach {


                val key = it.key
                val response = httpHandler.fetchJson(it.value)
                val comics = gsonParser.parseResponse(response)

                val section = it.value
                section.timeStramp = System.currentTimeMillis()
                if (comics != null) {
                    section.comicsSet = comics
                    section.eTag = comics.etag
                }

                data.put(key, section)


            }

            TopComicsSections.postValue(data)
        }





    }

    private fun buildSections(): HashMap<Int, TopSection> {

        val sections = HashMap<Int, TopSection>()

        /** hardcoded - could be from remote config **/
        val section1 = TopSection(System.currentTimeMillis())
        section1.forDate = FOR_DATE.thisWeek.name
        section1.sectionIndex = 0
        section1.requestCount = 20
        section1.title = "This week"

        sections.put(0, section1)

        val section2 = TopSection(System.currentTimeMillis())
        section1.forDate = FOR_DATE.lastWeek.name
        section1.sectionIndex = 0
        section1.requestCount = 20
        section1.title = "Last week"

        sections.put(1, section2)

        return sections
    }

    /** dagger http component **/
    private fun createHttpComponent(): ComicsHttpComponent {
        return DaggerComicsHttpComponent.builder()
            .comicsHttpModule(ComicsHttpModule())
            .comicsGsonModule(ComicsGsonModule())
            .build()
    }


}