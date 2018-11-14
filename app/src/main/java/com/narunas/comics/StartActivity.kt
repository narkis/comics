package com.narunas.comics

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.narunas.comics.ui.TopSectionsFragment
import com.narunas.comics.viemodel.CommonViewModel

class StartActivity : AppCompatActivity() {

    private lateinit var model: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        model = ViewModelProviders.of(this).get(CommonViewModel::class.java)

        if(savedInstanceState == null) {
            model.fetchHttpData()
        }

    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_container, TopSectionsFragment.getInstance())
            .commit()
    }

}
