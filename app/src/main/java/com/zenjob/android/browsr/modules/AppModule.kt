package com.zenjob.android.browsr.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenjob.android.browsr.adapters.MoviesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAdapter(): MoviesAdapter = MoviesAdapter()


}