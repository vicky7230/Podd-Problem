package com.vicky7230.poddproblem.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vicky7230.poddproblem.ui.main.MainViewModel
import com.vicky7230.poddproblem.di.ViewModelFactory
import com.vicky7230.poddproblem.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun postMainViewModel(mainViewModel: MainViewModel): ViewModel

}