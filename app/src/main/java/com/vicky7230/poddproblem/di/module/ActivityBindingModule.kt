package com.vicky7230.poddproblem.di.module

import com.vicky7230.poddproblem.ui.main.MainActivity
import com.vicky7230.poddproblem.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity

}