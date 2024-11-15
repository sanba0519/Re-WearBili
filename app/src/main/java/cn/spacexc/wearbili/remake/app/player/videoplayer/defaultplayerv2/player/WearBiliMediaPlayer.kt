package cn.spacexc.wearbili.remake.app.player.videoplayer.defaultplayerv2.player

import kotlinx.coroutines.flow.Flow

interface WearBiliMediaPlayer {
    fun play()
    fun pause()

    /**
     * in milli second
     */
    val currentProgress: Flow<Long>
    val currentState: Flow<>

    enum class PlayerState {
        Idle, Preparing, Buffering, Playing, Paused, Completed
    }
}

