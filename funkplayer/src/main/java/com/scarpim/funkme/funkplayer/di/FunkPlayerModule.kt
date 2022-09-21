/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkplayer.di

import androidx.lifecycle.ViewModel
import com.scarpim.funkme.domain.player.FunkAudioPlayer
import com.scarpim.funkme.domain.repository.AudioRepository
import com.scarpim.funkme.funkplayer.FunkAudioPlayerImpl
import com.scarpim.funkme.funkplayer.FunkAudioRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ActivityComponent::class, ViewModelComponent::class)
abstract class FunkPlayerModule {

    @Binds
    abstract fun bindFunkPlayer(
        funkAudioPlayerImpl: FunkAudioPlayerImpl
    ): FunkAudioPlayer

    @Binds
    abstract fun bindAudioRepository(
        funkAudioRepository: FunkAudioRepository
    ): AudioRepository
}