package com.enricopapi.app

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null
    private lateinit var menuLayout: LinearLayout
    private lateinit var playerView: PlayerView
    private lateinit var tvCredits: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuLayout = findViewById(R.id.menu_layout)
        playerView = findViewById(R.id.player_view)
        tvCredits = findViewById(R.id.tv_credits)
        val btnCar = findViewById<Button>(R.id.btn_car)
        val btnVerity = findViewById<Button>(R.id.btn_verity)

        btnCar.setOnClickListener {
            tvCredits.visibility = View.GONE
            playVideo("video_car.mp4")
        }

        btnVerity.setOnClickListener {
            tvCredits.visibility = View.VISIBLE
            playVideo("video_verity.mp4")
        }
    }

    private fun playVideo(fileName: String) {
        menuLayout.visibility = View.GONE
        playerView.visibility = View.VISIBLE

        player?.release()
        val context = this
        player = ExoPlayer.Builder(context).build().also { exoPlayer ->
            playerView.player = exoPlayer
            val dataSourceFactory = DefaultDataSource.Factory(context)
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse("asset:///$fileName")))

            exoPlayer.setMediaSource(mediaSource)
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }

    override fun onBackPressed() {
        if (playerView.visibility == View.VISIBLE) {
            player?.stop()
            player?.release()
            player = null
            playerView.visibility = View.GONE
            tvCredits.visibility = View.GONE
            menuLayout.visibility = View.VISIBLE
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }
}
