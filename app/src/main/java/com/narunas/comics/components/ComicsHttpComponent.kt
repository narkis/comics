package com.narunas.comics.components

import com.narunas.comics.components.modules.ComicsGsonModule
import com.narunas.comics.components.modules.ComicsHttpModule
import com.narunas.comics.viemodel.CommonViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf (
    ComicsHttpModule::class,
    ComicsGsonModule::class
))

interface ComicsHttpComponent {

    fun inject(model: CommonViewModel)

}