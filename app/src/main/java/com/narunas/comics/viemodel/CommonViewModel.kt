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
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap
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
            lastWeek,
            thisMonth

        }

        val ComicsSetInReview: MutableLiveData<ArrayList<Comic>> = MutableLiveData()
        val TopComicsSections: MutableLiveData<HashMap<Int, TopSection>> = MutableLiveData()
        val TAG: String = CommonViewModel::class.java.simpleName

    }

    init {

        createHttpComponent().inject(this)


    }


    fun fetchHttpData() {

        /** dagger injection should provide versions of this functionality
         * a slight delay in between the loads here
         */
            var cnt = 0
            val data = buildSections()
            val next = HashMap<Int, TopSection>(data.size)

            val mTimer = Timer("", false)
            mTimer.scheduleAtFixedRate(timerTask {
                let {
                    while(cnt <= data.size) {

                        val sec = data.get(cnt)
                        if(sec != null) {
                            val new = sec
                            val response = httpHandler.fetchJson(new)
                            val set = gsonParser.parseResponse(response)
                            new.comicsSet = set
                            new.eTag = new.comicsSet?.etag!!
                            next.put(cnt, new)
                        }
                        cnt ++
                    }
                }

                TopComicsSections.postValue(next)
                cancel()


            }, 0L, 10L)

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
        section2.forDate = FOR_DATE.lastWeek.name
        section2.sectionIndex = 1
        section2.requestCount = 20
        section2.title = "Last week"

        sections.put(1, section2)

        val section3 = TopSection(System.currentTimeMillis())
        section3.forDate = FOR_DATE.thisMonth.name
        section3.sectionIndex = 2
        section3.requestCount = 20
        section3.title = "This Month"

        sections.put(2, section3)

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

