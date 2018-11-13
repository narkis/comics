package com.narunas.comics

import android.app.Application
import com.narunas.comics.components.ComicsHttpComponent
import com.narunas.comics.components.DaggerComicsHttpComponent
import com.narunas.comics.components.modules.ComicsHttpModule

class App : Application() {


    lateinit var basicComicsHttpComponent: ComicsHttpComponent

    override fun onCreate() {
        super.onCreate()

//        basicComicsHttpComponent = createHttpComponent()
    }

    fun getComicsHttpComponent() : ComicsHttpComponent {
        return basicComicsHttpComponent
    }

    private fun createHttpComponent() : ComicsHttpComponent =
            DaggerComicsHttpComponent.builder()
                .comicsHttpModule(ComicsHttpModule())
                .build()
}
