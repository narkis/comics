package com.narunas.comics

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.narunas.comics.ui.TopSectionsFragment
import com.narunas.comics.viemodel.CommonViewModel
import com.narunas.comics.viemodel.CommonViewModel.Companion.TopComicsSections
import com.narunas.comics.viemodel.TopSection

class StartActivity : AppCompatActivity() {

    private lateinit var model: CommonViewModel
    private lateinit var splash: LinearLayout
    private var observer =  Observer<HashMap<Int, TopSection>> {

        it?.let {
            dismissSpinner()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        splash = findViewById(R.id.splash)

        model = ViewModelProviders.of(this).get(CommonViewModel::class.java)

        if(savedInstanceState == null) {
            splash.visibility = View.VISIBLE
            TopComicsSections.observe(this, observer)
            model.fetchHttpData()
        }

    }

    override fun onResume() {
        super.onResume()

        supportFragmentManager.beginTransaction()
            .replace(R.id.content_container, TopSectionsFragment.getInstance())
            .commit()
    }


    fun dismissSpinner() {
        splash.visibility = View.GONE
        TopComicsSections.removeObserver(observer)
    }
}
