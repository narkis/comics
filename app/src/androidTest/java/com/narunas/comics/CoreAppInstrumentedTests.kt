package com.narunas.comics

import android.arch.lifecycle.ViewModelProviders
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.narunas.comics.viemodel.CommonViewModel
import com.narunas.comics.viemodel.TopSection
import com.narunas.simpledetailtest.base.BaseApplicationTest
import junit.framework.TestCase.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoreAppInstrumentedTests: BaseApplicationTest<StartActivity>(StartActivity::class.java) {


    private val appContext = ApplicationProvider.getApplicationContext<App>()
    lateinit var mainActivity: StartActivity
    lateinit var commonModel: CommonViewModel
    private var eTag: String = ""


    @Before
    fun setUp() {

        mainActivity = testRule.activity
        commonModel = ViewModelProviders.of(mainActivity).get(CommonViewModel::class.java)

    }

    @After
    fun cleanUp() {

    }

    @Test
    fun useAppContext() {

        assertEquals(appContext.resources.getString(R.string.app_domain), appContext.packageName)
    }

    @Test
    fun basicHttpRequest() {

        val section1 = TopSection(System.currentTimeMillis())
        section1.forDate = CommonViewModel.Companion.FOR_DATE.thisWeek.name
        section1.sectionIndex = 0
        section1.requestCount = 20
        section1.title = "This week"

        val response = commonModel.httpHandler.fetchJson(section1)
        assertNotNull(" the http request returned null", response)

    }


    @Test
    fun checkParsing() {

        val section1 = TopSection(System.currentTimeMillis())
        section1.forDate = CommonViewModel.Companion.FOR_DATE.thisWeek.name
        section1.sectionIndex = 0
        section1.requestCount = 20
        section1.title = "This week"

        val response = commonModel.httpHandler.fetchJson(section1)
        if(response != null) {

            val comics = commonModel.gsonParser.parseResponse(response)

            assertNotNull(" ComicsThisWeek parsing ERROR ", comics)
            assertEquals(" Incorrect number of ComicsThisWeek returned", 20, comics?.data?.comics?.size)

        }

    }

    @Test
    fun checkEtagResponse() {

        val section1 = TopSection(System.currentTimeMillis())
        section1.forDate = CommonViewModel.Companion.FOR_DATE.thisWeek.name
        section1.sectionIndex = 0
        section1.requestCount = 20
        section1.title = "This week"

        val response = commonModel.httpHandler.fetchJson(section1)
        if(response != null) {
            val comics = commonModel.gsonParser.parseResponse(response)
            assertNotNull(" ComicsThisWeek parsing ERROR ", comics)
            section1.eTag = comics?.etag!!

        }


        val second = commonModel.httpHandler.fetchJson(section1)
        if(second != null) {

            val comics = commonModel.gsonParser.parseResponse(response)
            assertEquals(" etags should be equal ${comics?.etag} ", section1.eTag, comics?.etag)
        }

    }

    @Test
    fun checkThumbAvailability () {

        val section1 = TopSection(System.currentTimeMillis())
        section1.forDate = CommonViewModel.Companion.FOR_DATE.thisWeek.name
        section1.sectionIndex = 0
        section1.requestCount = 20
        section1.title = "This week"

        val response = commonModel.httpHandler.fetchJson(section1)
        if(response != null) {

            val comics = commonModel.gsonParser.parseResponse(response)

            assertNotNull(" ComicsThisWeek ERROR ", comics)
            assertNotNull( " no image available ", comics?.data?.comics?.get(0)?.thumb?.path)
        }


    }

    @Test
    fun checkLastWeek () {

        val section1 = TopSection(System.currentTimeMillis())
        section1.forDate = CommonViewModel.Companion.FOR_DATE.lastWeek.name
        section1.sectionIndex = 0
        section1.requestCount = 20
        section1.title = "Last Week"

        val response = commonModel.httpHandler.fetchJson(section1)
        assertNotNull(" the http request returned null", response)


    }

    @Test
    fun checkNextWeek () {

        val section1 = TopSection(System.currentTimeMillis())
        section1.forDate = CommonViewModel.Companion.FOR_DATE.nextWeek.name
        section1.sectionIndex = 0
        section1.requestCount = 20
        section1.title = "Next Week"

        val response = commonModel.httpHandler.fetchJson(section1)
        assertNotNull(" the http request returned null", response)


    }
}
