package com.narunas.comics

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.narunas.comics.viemodel.CommonViewModel

class StartActivity : AppCompatActivity() {

    private lateinit var model: CommonViewModel
    private lateinit var comicsApp: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        comicsApp = application as App
        model = ViewModelProviders.of(this).get(CommonViewModel::class.java)
        model.fetchHttpData()


    }
}
