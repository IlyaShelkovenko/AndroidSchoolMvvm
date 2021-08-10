package com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider

import io.reactivex.Scheduler


interface SchedulersProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
}