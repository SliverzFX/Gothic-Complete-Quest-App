package com.sliverzfx.gothicquest

import android.content.Context
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.view.Surface
import android.view.TextureView
import android.view.View

/** TextureView supports opacity, allowing the movie to crossfade over its matching still. */
internal class HomeVideoView(context: Context, private val onFrameReady: (Boolean) -> Unit) :
    TextureView(context), TextureView.SurfaceTextureListener {
    private var player: MediaPlayer? = null
    private var videoSurface: Surface? = null
    private var prepared = false
    private var playRequested = false
    private var disposed = false
    private var videoWidth = 0
    private var videoHeight = 0

    init {
        isOpaque = false
        keepScreenOn = false
        surfaceTextureListener = this
    }

    fun requestPlayback(requested: Boolean) {
        playRequested = requested
        updatePlayback()
    }

    private fun updatePlayback() {
        val activePlayer = player ?: return
        if (!prepared || disposed) return
        if (playRequested && windowVisibility == View.VISIBLE) {
            if (!activePlayer.isPlaying) activePlayer.start()
        } else if (activePlayer.isPlaying) {
            activePlayer.pause()
        }
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        updatePlayback()
    }

    override fun onSurfaceTextureAvailable(texture: SurfaceTexture, width: Int, height: Int) {
        if (disposed) return
        releasePlayer()
        val surface = Surface(texture)
        videoSurface = surface
        val newPlayer = MediaPlayer()
        player = newPlayer
        newPlayer.setSurface(surface)
        newPlayer.isLooping = true
        newPlayer.setVolume(0f, 0f)
        newPlayer.setScreenOnWhilePlaying(false)
        newPlayer.setOnPreparedListener {
            prepared = true
            videoWidth = it.videoWidth
            videoHeight = it.videoHeight
            updateCrop()
            updatePlayback()
        }
        newPlayer.setOnInfoListener { _, what, _ ->
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) onFrameReady(true)
            false
        }
        newPlayer.setOnErrorListener { _, _, _ ->
            // Leave the matching still visible if this device cannot play the movie.
            prepared = false
            onFrameReady(false)
            true
        }
        try {
            newPlayer.setDataSource(context,
                Uri.parse("android.resource://${context.packageName}/${R.raw.home_menu_loop}"))
            newPlayer.prepareAsync()
        } catch (_: java.io.IOException) {
            releasePlayer()
        } catch (_: IllegalArgumentException) {
            releasePlayer()
        } catch (_: SecurityException) {
            releasePlayer()
        }
    }

    private fun updateCrop() {
        if (width <= 0 || height <= 0 || videoWidth <= 0 || videoHeight <= 0) return
        val scale = maxOf(width.toFloat() / videoWidth, height.toFloat() / videoHeight)
        val matrix = Matrix()
        matrix.setScale(videoWidth * scale / width, videoHeight * scale / height,
            width / 2f, height / 2f)
        setTransform(matrix)
    }

    override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture, width: Int, height: Int) = updateCrop()
    override fun onSurfaceTextureUpdated(texture: SurfaceTexture) = Unit
    override fun onSurfaceTextureDestroyed(texture: SurfaceTexture): Boolean {
        releasePlayer()
        return true
    }

    fun dispose() {
        disposed = true
        releasePlayer()
    }

    private fun releasePlayer() {
        onFrameReady(false)
        prepared = false
        player?.release()
        player = null
        videoSurface?.release()
        videoSurface = null
    }
}
