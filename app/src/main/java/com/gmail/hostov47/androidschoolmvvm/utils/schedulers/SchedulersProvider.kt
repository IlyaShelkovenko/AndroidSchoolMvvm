package com.gmail.hostov47.androidschoolmvvm.utils.schedulers

import io.reactivex.Scheduler


interface SchedulersProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
}