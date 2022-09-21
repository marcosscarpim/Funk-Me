/**
 * Copyright ....
 */

package com.scarpim.funkme.domain.model

/**
 * Type of Funk audios available on device.
 */
enum class FunkType {
    /**
     * Type of audio that should be played repeatedly.
     */
    REPEAT,

    /**
     * Type of audio with no voice.
     */
    SOUND,

    /**
     * Type of audio with voice.
     */
    VOICE
}