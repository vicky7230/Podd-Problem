package com.vicky7230.poddproblem.ui.main

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.vicky7230.poddproblem.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideItemListAdapter(): ItemListAdapter {
        return ItemListAdapter(arrayListOf())
    }

    @Provides
    fun provideLayoutManager(
        @ApplicationContext context: Context
    ): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}