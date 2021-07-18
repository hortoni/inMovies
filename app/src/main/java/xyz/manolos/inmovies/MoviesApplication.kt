package xyz.manolos.inmovies

import android.app.Activity
import android.app.Application
import xyz.manolos.inmovies.di.DaggerApplicationComponent

class MoviesApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationContext(applicationContext)
            .build()
    }
}

val Activity.injector get() = (application as MoviesApplication).component