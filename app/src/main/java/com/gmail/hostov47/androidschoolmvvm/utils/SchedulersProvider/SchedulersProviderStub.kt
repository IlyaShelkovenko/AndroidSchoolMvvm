package com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class SchedulersProviderImplStub : SchedulersProvider {
    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }
}