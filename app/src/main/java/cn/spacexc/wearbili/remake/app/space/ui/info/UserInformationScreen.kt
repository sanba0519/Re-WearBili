package cn.spacexc.wearbili.remake.app.space.ui.info

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cn.spacexc.wearbili.remake.app.space.ui.UserSpaceViewModel
import cn.spacexc.wearbili.remake.common.ui.TitleBackgroundScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TitleBackgroundScope.UserInformationScreen(
    navController: NavController,
    viewModel: UserSpaceViewModel = hiltViewModel()
) {
    var isShowingDetail by remember {
        mutableStateOf(false)
    }

    SharedTransitionLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnimatedContent(targetState = isShowingDetail, label = "") { isDetailShowing ->
            if (isDetailShowing) {
                DetailInformationScreen(
                    viewModel = viewModel,
                    animatedContentScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    navController = navController
                ) {
                    isShowingDetail = false
                }
            } else {
                BasicInformationScreen(
                    viewModel = viewModel,
                    animatedContentScope = this,
                    titleBackgroundScope = this@UserInformationScreen
                ) {
                    isShowingDetail = true
                }
            }
        }
    }
}