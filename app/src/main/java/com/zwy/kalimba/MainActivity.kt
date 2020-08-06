package com.zwy.kalimba

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.ToneGenerator
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Afauria
 * @date 2020/8/5
 */
class MainActivity : AppCompatActivity() {

   lateinit var tonePlayer:ToneGenerator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        tonePlayer = ToneGenerator(AudioManager.STREAM_MUSIC, 70)
        val b = assets.open("a1.pcm").readBytes()
        val c = "63627".toByteArray()
        Log.e("tttttt",""+b.size)
        val audioTrack = AudioTrack(
            AudioManager.STREAM_MUSIC,
            44100,
            AudioFormat.CHANNEL_OUT_STEREO, // CHANNEL_CONFIGURATION_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            44100,
            AudioTrack.MODE_STREAM
        )
        var i = 0
        val t= listOf<Int>(262,277,293,311,330,349,370,392,415)
//        findViewById<TextView>(R.id.text).setOnClickListener {
//            audioTrack.play()
//            audioTrack.write(c,0,c.size)
//            val a =AudioTrackManager.getInstance().startPlay(assets.open("a1.pcm"))
//            SinWave.start(440)
//            i=(i+1)%9
//            tonePlayer.startTone(30,100)
//            tonePlayer.startTone(ToneGenerator.TONE_DTMF_0,100)
//        }
    }

}