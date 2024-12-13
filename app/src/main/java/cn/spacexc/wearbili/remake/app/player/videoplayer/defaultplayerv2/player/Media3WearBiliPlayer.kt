package cn.spacexc.wearbili.remake.app.player.videoplayer.defaultplayerv2.player

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import cn.spacexc.wearbili.remake.app.player.videoplayer.defaultplayerv2.player.WearBiliMediaPlayer.PlayerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class Media3WearBiliPlayer(context: Context) : WearBiliMediaPlayer {
    private val player = ExoPlayer.Builder(context)
        .build()

    override val currentProgress: Flow<Long>
        get() = flow {
            while (true) {
                emit(player.currentPosition)
                delay(10)
            }
        }

    override val currentState
        get() = MutableStateFlow(PlayerState.Idle)

    init {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                currentState.value = if (isPlaying) PlayerState.Playing else PlayerState.Paused
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                currentState.value = when (playbackState) {
                    Player.STATE_IDLE -> PlayerState.Idle
                    Player.STATE_BUFFERING -> PlayerState.Buffering
                    Player.STATE_ENDED -> PlayerState.Completed
                    else -> currentState.value
                }
            }
        })
    }

    override fun play() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }
}