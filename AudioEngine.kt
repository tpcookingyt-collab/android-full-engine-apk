package com.freavity.androidengine.engine.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool

class AudioEngine(context: Context) {
    private val appContext = context.applicationContext
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(8)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()

    private val sounds = mutableMapOf<String, Int>()
    private var musicPlayer: MediaPlayer? = null

    fun loadSound(name: String, resId: Int) {
        sounds[name] = soundPool.load(appContext, resId, 1)
    }

    fun playSound(name: String, volume: Float = 1f) {
        sounds[name]?.let { soundPool.play(it, volume, volume, 1, 0, 1f) }
    }

    fun playMusic(resId: Int, loop: Boolean = true) {
        stopMusic()
        musicPlayer = MediaPlayer.create(appContext, resId)?.apply {
            isLooping = loop
            start()
        }
    }

    fun stopMusic() {
        musicPlayer?.stop()
        musicPlayer?.release()
        musicPlayer = null
    }

    fun release() {
        stopMusic()
        soundPool.release()
    }
}
