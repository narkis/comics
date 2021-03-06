package com.narunas.comics.components.modules

import com.narunas.comics.http.HttpCode
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ComicsHttpModule {

    @Provides
    @Singleton

    fun providesComicsHttpFunctionality() : HttpCode {
        return HttpCode()
    }

}