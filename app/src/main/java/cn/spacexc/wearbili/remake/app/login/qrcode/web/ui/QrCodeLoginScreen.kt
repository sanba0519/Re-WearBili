package cn.spacexc.wearbili.remake.app.login.qrcode.web.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import cn.spacexc.wearbili.remake.R
import cn.spacexc.wearbili.remake.app.splash.ui.SplashScreen
import cn.spacexc.wearbili.remake.common.ui.BilibiliPink
import cn.spacexc.wearbili.remake.common.ui.TitleBackground
import cn.spacexc.wearbili.remake.common.ui.rememberMutableInteractionSource
import cn.spacexc.wearbili.remake.common.ui.wearBiliAnimatedContentSize
import kotlinx.coroutines.launch

/**
 * Created by XC-Qan on 2023/4/2.
 * I'm very cute so please be nice to my code!
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 */

@kotlinx.serialization.Serializable
object QrCodeLoginScreen

@Composable
fun LoginScreen(
    viewModel: QrCodeLoginViewModel = hiltViewModel(),
    navController: NavController
) {
    TitleBackground(
        navController = navController,
        title = "",
        onRetry = { /*TODO*/ },
        onBack = navController::navigateUp
    ) {
        var job = remember {
            viewModel.viewModelScope.launch {
                viewModel.startLogin {
                    navController.navigate(SplashScreen) {
                        popUpTo(0)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    //.aspectRatio(1f)
                    .fillMaxSize(0.65f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(4.dp)
            ) {
                Crossfade(targetState = viewModel.screenState.currentLoginStatus, label = "") {
                    when (it) {
                        QrCodeLoginStatus.Loading, QrCodeLoginStatus.GettingKey -> {
                            Image(
                                painter = painterResource(id = R.drawable.img_loading_2233),
                                contentDescription = "加载中...",
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }

                        QrCodeLoginStatus.Failed, QrCodeLoginStatus.Timeout, QrCodeLoginStatus.FailedGettingKey -> {
                            Image(
                                painter = painterResource(id = R.drawable.img_loading_2233_error),
                                contentDescription = "加载失败",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable(
                                        interactionSource = rememberMutableInteractionSource(),
                                        indication = null
                                    ) {
                                        job.cancel()
                                        job = viewModel.viewModelScope.launch {
                                            viewModel.startLogin {
                                                navController.navigate(SplashScreen) {
                                                    popUpTo(0)
                                                }
                                            }
                                        }
                                    }
                            )
                        }

                        QrCodeLoginStatus.Pending -> {
                            viewModel.screenState.qrCodeBitmap?.let { bitmap ->
                                Image(
                                    bitmap = bitmap,
                                    contentDescription = "登录二维码",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(3.dp)
                                        .clickable(
                                            interactionSource = rememberMutableInteractionSource(),
                                            indication = null
                                        ) {
                                            job.cancel()
                                            job = viewModel.viewModelScope.launch {
                                                viewModel.startLogin {
                                                    navController.navigate(SplashScreen) {
                                                        popUpTo(0)
                                                    }
                                                }
                                            }
                                        }
                                )
                            }
                        }

                        QrCodeLoginStatus.Waiting -> {
                            viewModel.screenState.qrCodeBitmap?.let { bitmap ->
                                Image(
                                    bitmap = bitmap,
                                    contentDescription = "登录二维码",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .alpha(0.4f)
                                        .padding(3.dp)
                                        .clickable(
                                            interactionSource = rememberMutableInteractionSource(),
                                            indication = null
                                        ) {
                                            job.cancel()
                                            job = viewModel.viewModelScope.launch {
                                                viewModel.startLogin {
                                                    navController.navigate(SplashScreen) {
                                                        popUpTo(0)
                                                    }
                                                }
                                            }
                                        }
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .fillMaxWidth(0.45f)
                                        .aspectRatio(1f)
                                        .background(Color.White, CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = null,
                                        tint = BilibiliPink,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(6.dp)
                                    )
                                }
                            }
                        }

                        QrCodeLoginStatus.Success -> {
                            Text(text = "登录成功")
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Crossfade(
                modifier = Modifier.wearBiliAnimatedContentSize(),
                targetState = viewModel.screenState.currentLoginStatus,
                label = ""
            ) {
                when (it) {
                    QrCodeLoginStatus.Loading -> {
                        Text(
                            text = "二维码加载中",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    QrCodeLoginStatus.Failed -> {
                        Text(
                            text = "加载失败啦",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    QrCodeLoginStatus.Timeout -> {
                        Text(
                            text = "二维码失效啦",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    QrCodeLoginStatus.Pending -> {
                        Text(
                            text = "使用哔哩哔哩手机客户端\n扫描二维码以登录",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    QrCodeLoginStatus.Waiting -> {
                        Text(
                            text = "请在手机上轻触确认",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    QrCodeLoginStatus.Success -> {
                        Text(
                            text = "登录成功",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    QrCodeLoginStatus.FailedGettingKey -> {
                        Text(
                            text = "跳转失败, 点击重新登录",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    QrCodeLoginStatus.GettingKey -> {
                        Text(
                            text = "登录成功, 正在跳转",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            /*Card(shape = CircleShape, fillMaxSize = false, outerPaddingValues = PaddingValues(top = 8.dp), innerPaddingValues = PaddingValues(horizontal = 8.dp, vertical = 6.dp)) {
                IconText(text = "疑难解答", fontSize = 10.sp) {
                    Icon(imageVector = Icons.Default.QuestionMark, contentDescription = null, tint = Color.White, modifier = Modifier.fillMaxSize())
                }
            }*/
        }
    }
}