package cn.spacexc.wearbili.remake.app.player.videoplayer.defaultplayerv2.presentation

import androidx.lifecycle.ViewModel
import cn.spacexc.wearbili.remake.app.Application
import cn.spacexc.wearbili.remake.app.player.videoplayer.defaultplayerv2.player.Media3WearBiliPlayer
import cn.spacexc.wearbili.remake.app.player.videoplayer.defaultplayerv2.player.WearBiliMediaPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WearBiliVideoPlayerViewModel @Inject constructor(application: Application) : ViewModel() {
    val player: WearBiliMediaPlayer = Media3WearBiliPlayer(application)
}