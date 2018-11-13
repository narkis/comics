package com.narunas.comics

import android.arch.lifecycle.ViewModelProviders
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.narunas.comics.viemodel.CommonViewModel
import com.narunas.simpledetailtest.base.BaseApplicationTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoreAppInstrumentedTests: BaseApplicationTest<StartActivity>(StartActivity::class.java) {


    private val appContext = ApplicationProvider.getApplicationContext<App>()
    lateinit var mainActivity: StartActivity
    lateinit var commonModel: CommonViewModel


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


            val response = commonModel.httpHandler.fetchJson()
            assertNotNull(" the http request returned null", response)


    }

}
