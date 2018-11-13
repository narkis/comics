package com.narunas.comics

import android.arch.lifecycle.ViewModelProviders
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.narunas.comics.viemodel.CommonViewModel
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


            val response = commonModel.httpHandler.fetchJson(eTag)
            assertNotNull(" the http request returned null", response)

    }


    @Test
    fun checkParsing() {

        val response = commonModel.httpHandler.fetchJson(eTag)
        if(response != null) {

            val comics = commonModel.gsonParser.parseResponse(response)
            eTag = comics.etag

            assertNotNull(" Comics parsing ERROR ", comics)
            assertNotSame("", eTag)
            assertEquals(" Incorrect number of Comics returned", 20, comics.data.comics.size)

        }

    }

    @Test
    fun checkEtagResponse() {

        var _eT = ""

        /** eTag is set now to a correct value **/
        val response = commonModel.httpHandler.fetchJson(_eT)
        if(response != null) {
            val comics = commonModel.gsonParser.parseResponse(response)
            assertNotNull(" Comics parsing ERROR ", comics)
            _eT = comics.etag

        }


        val second = commonModel.httpHandler.fetchJson(_eT)
        if(second != null) {

            val comics = commonModel.gsonParser.parseResponse(response)
            assertEquals(" etags should be equal ${comics.etag} ", _eT, comics.etag)
        }

    }
}
