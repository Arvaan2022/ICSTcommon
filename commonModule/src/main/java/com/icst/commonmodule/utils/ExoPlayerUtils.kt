package com.icst.commonmodule.utils

import android.content.Context
import android.widget.LinearLayout
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector
import com.google.android.exoplayer2.util.MimeTypes
import com.potyvideo.library.AndExoPlayerView

class ExoPlayerUtils(val context: Context) {

    private lateinit var simpleExoplayer: ExoPlayer
    private var playbackPosition = 0L
    private var currVolume: Float = 0f

    fun getSimpleExoPlayer(videoUrl: String): ExoPlayer {
        val rf = DefaultRenderersFactory(context)
            .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER)
            .setEnableDecoderFallback(true)
            .setMediaCodecSelector { mimeType, requiresSecureDecoder, requiresTunnelingDecoder ->
                var decoderInfo = MediaCodecSelector.DEFAULT
                    .getDecoderInfos(mimeType, requiresSecureDecoder, requiresTunnelingDecoder)
                if (MimeTypes.VIDEO_H264 == mimeType) {
                    // copy the list because MediaCodecSelector.DEFAULT returns an unmodifiable list
                    decoderInfo = ArrayList(decoderInfo)
                    decoderInfo.reverse()
                }
                decoderInfo
            }

        simpleExoplayer = ExoPlayer.Builder(context).build().also {
            it.setMediaItem(MediaItem.fromUri(videoUrl))
            it.prepare()
            it.play()
            //video_view.player = it
            it.seekTo(playbackPosition)
            it.playWhenReady = true
            //it.addListener(this)
        }

        return simpleExoplayer
    }

    fun unMutePlayer(player: AndExoPlayerView?) {
        simpleExoplayer.volume = currVolume
        showUnMuteButton(player)
    }

    fun mutePlayer(player: AndExoPlayerView?) {
        currVolume = simpleExoplayer.volume
        simpleExoplayer.volume = 0f
        showMuteButton(player)
    }

    fun seekBackward(backwardValue: Int = 10000) {
        var seekValue = simpleExoplayer.currentPosition - backwardValue
        if (seekValue < 0) seekValue = 0
        simpleExoplayer.seekTo(seekValue)
    }

    fun seekForward(ForwardValue: Int = 10000) {
        var seekValue = simpleExoplayer.currentPosition + ForwardValue
        if (seekValue > simpleExoplayer.duration) seekValue = simpleExoplayer.duration
        simpleExoplayer.seekTo(seekValue)
    }

    private fun showUnMuteButton(player: AndExoPlayerView?) {
        player!!.mute.visibility = LinearLayout.GONE
        player.unMute.visibility = LinearLayout.VISIBLE
    }

    private fun showMuteButton(player: AndExoPlayerView?) {
        player!!.mute.visibility = LinearLayout.VISIBLE
        player.unMute.visibility = LinearLayout.GONE
    }

    fun releasePlayer() {
        if (this::simpleExoplayer.isInitialized) {
            simpleExoplayer.let {
                playbackPosition = it.currentPosition
                it.release()
                it.playWhenReady = false
                it.stop()
                it.seekTo(0)
            }
        }
    }

    fun destroyExoPlayer() {
        simpleExoplayer.let {
            if (it.mediaItemCount > 0) {
                it.clearMediaItems()
                it.stop()
                it.release()
            }
        }
        playbackPosition = 0L
    }

    fun resumePlayer() {
        if (this::simpleExoplayer.isInitialized) {
            simpleExoplayer.playWhenReady = true
        }
    }
}