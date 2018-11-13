package com.narunas.comics.components.modules

import com.narunas.comics.gson.GsonCode
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ComicsGsonModule {

    @Provides
    @Singleton

    fun providesComicsGsonFunctionality() : GsonCode{
        return GsonCode()
    }
}