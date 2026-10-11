package com.sliverzfx.gothicquest

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

/** Pause synchronously on ON_PAUSE, even when Compose is no longer drawing frames. */
internal class LifecycleMusicPlayer(
    private val lifecycle: Lifecycle,
    private val player: MediaPlayer
) : LifecycleEventObserver {
    private var foreground = lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
    private var playbackAllowed = false
    private var disposed = false

    init { lifecycle.addObserver(this) }

    fun setPlaybackAllowed(allowed: Boolean) {
        if (disposed) return
        playbackAllowed = allowed
        updatePlayback()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> foreground = true
            Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP, Lifecycle.Event.ON_DESTROY -> foreground = false
            else -> Unit
        }
        updatePlayback()
    }

    private fun updatePlayback() {
        if (disposed) return
        if (playbackAllowed && foreground) {
            if (!player.isPlaying) player.start()
        } else if (player.isPlaying) {
            player.pause()
        }
    }

    fun dispose() {
        if (disposed) return
        disposed = true
        lifecycle.removeObserver(this)
        if (player.isPlaying) player.pause()
        player.release()
    }
}

@Composable
internal fun AppMusic(enabled: Boolean, introFinished: Boolean) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val controller = remember(context, lifecycle) {
        MediaPlayer.create(context, R.raw.gothic_old_camp)?.let { player ->
            player.isLooping = true
            player.setVolume(0.45f, 0.45f)
            LifecycleMusicPlayer(lifecycle, player)
        }
    }
    DisposableEffect(controller) {
        onDispose { controller?.dispose() }
    }
    SideEffect { controller?.setPlaybackAllowed(enabled && introFinished) }
}
