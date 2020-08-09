package com.zwy.kalimba.util

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack

/**
 * @author Afauria
 * @date 2020/8/5
 */
object SinWave {
    /** 正弦波的高度 **/
    val HEIGHT = 127

    /** 2PI **/
    val TWOPI = 2 * 3.1415

    /**
     * 生成正弦波
     * @param wave
     * @param waveLen 每段正弦波的长度
     * @param length 总长度
     * @return
     */
    fun sin(wave: ByteArray, waveLen: Int, length: Int): ByteArray {
        for (i in 0 until length) {
            wave[i] = (HEIGHT * (1 - Math.sin(
                TWOPI * ((i % waveLen) * 1.00 / waveLen)))).toByte()
        }
        return wave
    }

    fun start(rate: Int) {
        if (rate > 0) {
            val Hz = rate
            val waveLen = 44100 / Hz
            val length = waveLen * Hz
            val audioTrack = AudioTrack(
                AudioManager.STREAM_MUSIC,
                44100,
                AudioFormat.CHANNEL_OUT_STEREO, // CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_8BIT,
                length,
                AudioTrack.MODE_STREAM
            )
            //生成正弦波
            val wave = sin(
                ByteArray(length),
                waveLen,
                length
            )
            audioTrack.play()
            audioTrack.write(wave, 0, length)
        } else {
            return
        }
    }
}