/**
 * Created by Ilia Shelkovenko on 15.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm

import android.app.Application
import android.content.Context
import com.gmail.hostov47.androidschoolmvvm.di.AppComponent
import com.gmail.hostov47.androidschoolmvvm.di.AppModule
import com.gmail.hostov47.androidschoolmvvm.di.DaggerAppComponent

class ImdbApp: Application() {

    init {
        instance = this
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    companion object {
        private var instance : ImdbApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

    }
}