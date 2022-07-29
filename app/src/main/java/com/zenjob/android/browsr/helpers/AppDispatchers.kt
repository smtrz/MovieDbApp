package com.zenjob.android.browsr.helpers

import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
data class AppDispatchers(
    val IO: CoroutineDispatcher = Dispatchers.IO
)