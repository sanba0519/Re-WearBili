package cn.spacexc.wearbili.remake.app.player.videoplayer.defaultplayerv2.player

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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

    override fun play() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }
}