package com.example.gaenari.util

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

object TTSUtil : TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var isInitialized = false

    // Initialize the TTS engine
    fun initialize(context: Context) {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.KOREAN)
            isInitialized = !(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
        }
    }

    fun speak(text: String) {
        if (isInitialized) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    fun stop() {
        if (isInitialized) {
            tts.stop()
        }
    }

    fun shutdown() {
        if (isInitialized) {
            tts.shutdown()
        }
    }
}