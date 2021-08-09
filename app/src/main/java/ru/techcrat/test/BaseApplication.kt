package ru.techcrat.test

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ru.techcrat.test.di.components.DaggerAppComponent

class BaseApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       return DaggerAppComponent.builder().application(this).build()
    }


}