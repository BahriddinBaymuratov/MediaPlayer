package com.example.mediaplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import androidx.core.view.isVisible
import com.example.mediaplayer.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {
    private val binding by lazy { ActivityVideoBinding.inflate(layoutInflater) }
    private val videoUrl =
        "https://firebasestorage.googleapis.com/v0/b/instagramclone-b53a9.appspot.com/o/video_2022-10-01_13-39-55.mp4?alt=media&token=44ae9041-c845-4c86-9c32-a02b837726e9"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mediaController = MediaController(this)
        binding.videoView.setVideoPath(videoUrl)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.setMediaController(mediaController)
        binding.videoView.setOnPreparedListener(this)
        binding.videoView.start()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        binding.progressbar.isVisible = false
    }
}