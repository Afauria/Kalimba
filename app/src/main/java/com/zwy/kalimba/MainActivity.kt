package com.zwy.kalimba

import android.media.*
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.socks.library.KLog.A
import kotlinx.android.synthetic.main.main_act.*

/**
 * @author Afauria
 * @date 2020/8/5
 */
//1.ToneGenerator：播放提示音
//2.AudioTrack：播放pcm
//3.SoundPool：播放
class MainActivity : AppCompatActivity() {

    private lateinit var tonePlayer: ToneGenerator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
//        tonePlayer = ToneGenerator(AudioManager.STREAM_MUSIC, 70)
//        findViewById<TextView>(R.id.text).setOnClickListener {
//            val a4 =AudioTrackManager.getInstance().startPlay(assets.open("a1.pcm"))
//            SinWave.start(440)
//            tonePlayer.startTone(ToneGenerator.TONE_DTMF_0,100)
//        }
    }

}