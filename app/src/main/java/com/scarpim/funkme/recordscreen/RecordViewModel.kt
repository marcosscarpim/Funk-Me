package com.scarpim.funkme.recordscreen

import androidx.lifecycle.ViewModel
import com.scarpim.funkme.domain.recorder.RecorderProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//data class RecordScreenState(
//
//)

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recorderProvider: RecorderProvider,
): ViewModel() {
}