package com.mscarpim.funkme.recorder.di

import com.mscarpim.funkme.recorder.AudioRecorderImpl
import com.scarpim.funkme.domain.recorder.AudioRecorder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ActivityComponent::class, ViewModelComponent::class)
abstract class RecorderModule {

    @Binds
    abstract fun bindAudioRecorder(
        audioRecorderImpl: AudioRecorderImpl
    ): AudioRecorder
}