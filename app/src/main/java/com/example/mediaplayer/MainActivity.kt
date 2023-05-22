package com.example.mediaplayer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.core.view.isVisible
import com.example.mediaplayer.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {
    // https://www.soundhelix.com/audio-examples
    private var musicUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    private var musicUrl2 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"
    private var mediaPlayer: MediaPlayer? = null
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.next.setOnClickListener { startActivity(Intent(this, VideoActivity::class.java)) }

        handler = Handler(mainLooper)

        binding.playBtn.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this,R.raw.whoopoty)
//                mediaPlayer?.setDataSource(musicUrl2)
//                mediaPlayer?.setOnPreparedListener(this)
//                mediaPlayer?.prepareAsync()
                handler.postDelayed(runnable, 100)
                mediaPlayer?.start()
                binding.seekBar.max = mediaPlayer?.duration!!   // seekbarga musicni vaqtini tenglash
                binding.textMax.text = setCurPlayTimeToTextView(mediaPlayer?.duration!!.toLong())  // textviewni musicni minutiga tenglash
            }
        }
        binding.resBtn.setOnClickListener {
            if (!mediaPlayer?.isPlaying!!) {  // toxtab turgan bolsa
                mediaPlayer?.start()  //  davom etsin
            }
        }
        binding.pauseBtn.setOnClickListener {
            if (mediaPlayer?.isPlaying!!) {  // qoshiq aytayotgan bolsa
                mediaPlayer?.pause()   //  music toxtasin
            }
        }
        binding.stopBtn.setOnClickListener {
            mediaPlayer?.stop()  //  music toxtatish
            mediaPlayer = null   // musicni boshiga qaytarish
            binding.seekBar.progress = 0  // seekbarni ham boshiga qaytarish
            binding.textCurrent.text = "00:00"  //  sanoqni ham 00 ga qaytarish
        }
        binding.forward.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition?.plus(3000)!!)   // 3 sekund oldiga otkazish
        }
        binding.backward.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition?.minus(3000)!!)  // 3 sekund ortga qaytarish
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.textCurrent.text = setCurPlayTimeToTextView(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    mediaPlayer?.seekTo(it.progress)
                }
            }
        })
    }

    private var runnable = object : Runnable {
        override fun run() {
            handler.postDelayed(this, 100)
            try {
                binding.seekBar.progress = mediaPlayer?.currentPosition!!
                binding.textCurrent.text =
                    setCurPlayTimeToTextView(mediaPlayer?.currentPosition!!.toLong())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    override fun onPrepared(mp: MediaPlayer?) {
        binding.progressbar.isVisible = false
        mp?.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        try {
            if (mediaPlayer != null) {
                mediaPlayer?.release()
                mediaPlayer = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setCurPlayTimeToTextView(ms: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(ms)
    }
}