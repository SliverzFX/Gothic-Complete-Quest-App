package com.sliverzfx.gothicquest

import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MusicLifecycleTest {
    private class Owner : LifecycleOwner {
        val registry = LifecycleRegistry(this)
        override val lifecycle: Lifecycle get() = registry
    }

    private fun withPlayer(block: (LifecycleRegistry, MediaPlayer, LifecycleMusicPlayer) -> Unit) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.runOnMainSync {
            val owner = Owner()
            val player = checkNotNull(MediaPlayer.create(instrumentation.targetContext, R.raw.gothic_old_camp))
            player.isLooping = true
            player.setVolume(0f, 0f)
            val controller = LifecycleMusicPlayer(owner.lifecycle, player)
            try { block(owner.registry, player, controller) }
            finally { controller.dispose() }
        }
    }

    @Test fun musicPausesOnLeavingAppAndResumesOnlyOnResume() = withPlayer { lifecycle, player, controller ->
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        controller.setPlaybackAllowed(true)
        assertTrue(player.isPlaying)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        assertFalse(player.isPlaying)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        controller.setPlaybackAllowed(true)
        assertFalse(player.isPlaying)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
        assertFalse(player.isPlaying)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        assertTrue(player.isPlaying)
    }

    @Test fun changingMusicWhileMinimizedDoesNotStartPlayback() = withPlayer { lifecycle, player, controller ->
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        controller.setPlaybackAllowed(true)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        controller.setPlaybackAllowed(false)
        controller.setPlaybackAllowed(true)
        assertFalse(player.isPlaying)
        controller.setPlaybackAllowed(false)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        assertFalse(player.isPlaying)
    }

    @Test fun introAndDisabledMusicStaySilentWhileForegrounded() = withPlayer { lifecycle, player, controller ->
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        controller.setPlaybackAllowed(false)
        assertFalse(player.isPlaying)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        assertFalse(player.isPlaying)
        controller.setPlaybackAllowed(true)
        assertTrue(player.isPlaying)
        controller.setPlaybackAllowed(false)
        assertFalse(player.isPlaying)
    }

    @Test fun disposalRemovesObserverBeforeReleasingPlayer() = withPlayer { lifecycle, _, controller ->
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        controller.setPlaybackAllowed(true)
        controller.dispose()
        assertEquals(0, lifecycle.observerCount)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        controller.setPlaybackAllowed(true)
    }
}
