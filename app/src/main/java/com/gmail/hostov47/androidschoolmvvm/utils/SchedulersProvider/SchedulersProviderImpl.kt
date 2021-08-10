package com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SchedulersProviderImpl : SchedulersProvider {
    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}