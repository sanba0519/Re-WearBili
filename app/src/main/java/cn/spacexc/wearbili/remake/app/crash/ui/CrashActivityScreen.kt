package cn.spacexc.wearbili.remake.app.crash.ui

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Process
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.spacexc.wearbili.common.domain.qrcode.ERROR_CORRECTION_L
import cn.spacexc.wearbili.common.domain.qrcode.QRCodeUtil
import cn.spacexc.wearbili.remake.app.MainActivity
import cn.spacexc.wearbili.remake.common.UIState
import cn.spacexc.wearbili.remake.common.ui.BilibiliPink
import cn.spacexc.wearbili.remake.common.ui.rememberMutableInteractionSource
import cn.spacexc.wearbili.remake.common.ui.theme.wearbiliFontFamily
import cn.spacexc.wearbili.remake.common.ui.titleBackgroundHorizontalPadding


/**
 * Created by XC-Qan on 2023/7/13.
 * I'm very cute so please be nice to my code!
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 */


/*//TODO implement crash screen buttons
@Composable
fun Activity.CrashActivityScreen(
    stacktrace: String,
    description: String,
    viewModel: CrashViewModel,
    context: Context
) {
    TitleBackground(
        navController = null,
        title = "",
        onBack = ::finish,
        onRetry = {},
        ambientAlpha = 0f,
        uiState = viewModel.uiState
    ) {
        var currentHighlightedButton by remember {
            mutableStateOf("")
        }
        val copyButtonColor by wearBiliAnimateColorAsState(
            targetValue = if (currentHighlightedButton == "copy") BilibiliPink else Color(
                54,
                54,
                54,
                255
            )
        )
        val qrcodeButtonColor by wearBiliAnimateColorAsState(
            targetValue = if (currentHighlightedButton == "qrcode") BilibiliPink else Color(
                54,
                54,
                54,
                255
            )
        )
        val restartButtonColor by wearBiliAnimateColorAsState(
            targetValue = if (currentHighlightedButton == "restart") BilibiliPink else Color(
                54,
                54,
                54,
                255
            )
        )

        LaunchedEffect(key1 = currentHighlightedButton, block = {
            delay(1000)
            currentHighlightedButton = ""
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(titleBackgroundHorizontalPadding())
                .padding(top = titleHeight),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "呜啊＞︿＜!", fontSize = 18.sp, style = AppTheme.typography.h1)
            Text(text = "Sorry...  ごめんなさい...", style = AppTheme.typography.h1)
            val annotatedString = buildAnnotatedString {
                append("对不起！Re:WearBili刚刚出错了！\n")
                append("你可以使用下面的按钮来")
                withStyle(style = SpanStyle(color = BilibiliPink)) {
                    pushStringAnnotation(tag = "copy", annotation = "")
                    append("复制下面的报错日志")
                    pop()
                }
                append("，也可以")
                withStyle(style = SpanStyle(color = BilibiliPink)) {
                    pushStringAnnotation(tag = "qrcode", annotation = "")
                    append("生成带有报错日志信息的二维码")
                    pop()
                }
                append("，并将他们通过Github issue/QQ群等方式发送给开发者。完成这些之后，就可以")
                withStyle(style = SpanStyle(color = BilibiliPink)) {
                    pushStringAnnotation(tag = "restart", annotation = "")
                    append("重启应用")
                    pop()
                }
                append("啦！")
                append("\n")
                append("生成二维码需要网络连接。")
            }
            ClickableText(
                text = annotatedString,
                style = AppTheme.typography.h3,
                modifier = Modifier.alpha(0.7f),
            ) { index ->
                annotatedString.getStringAnnotations(tag = "copy", start = index, end = index)
                    .firstOrNull()?.let {
                        currentHighlightedButton = "copy"
                    }
                annotatedString.getStringAnnotations(tag = "qrcode", start = index, end = index)
                    .firstOrNull()?.let {
                        currentHighlightedButton = "qrcode"
                    }
                annotatedString.getStringAnnotations(tag = "restart", start = index, end = index)
                    .firstOrNull()?.let {
                        currentHighlightedButton = "restart"
                    }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    innerPaddingValues = PaddingValues(14.dp),
                    borderColor = copyButtonColor,
                    onClick = {
                        stacktrace.copyToClipboard(this@CrashActivityScreen)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = "复制日志到剪贴板",
                        tint = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    innerPaddingValues = PaddingValues(14.dp),
                    borderColor = qrcodeButtonColor,
                    onClick = {
                        viewModel.uploadLog(description, stacktrace, this@CrashActivityScreen)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.QrCode,
                        contentDescription = "生成日志二维码",
                        tint = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    innerPaddingValues = PaddingValues(14.dp),
                    borderColor = restartButtonColor,
                    onClick = {
                        *//*startActivity(
                            Intent(
                                this@CrashActivityScreen,
                                SplashScreenActivity::class.java
                            )
                        )*//*
                        //finish()
                        //exitProcess(0)
                        finish()
                        val intent = Intent(context, MainActivity::class.java)
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                        Process.killProcess(Process.myPid())
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.RestartAlt,
                        contentDescription = "重启应用",
                        tint = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
            Text(
                text = stacktrace,
                style = AppTheme.typography.h3,
                fontSize = 9.5.sp,
                modifier = Modifier.alpha(0.6f)
            )
        }
    }
}*/

@Composable
fun Activity.CrashActivityScreen(
    viewModel: CrashViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(titleBackgroundHorizontalPadding())
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "哎呀，一不小心崩溃啦",
            fontFamily = wearbiliFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = Color.White
        )
        Text(
            text = "!!!戳我重启!!!",
            fontFamily = wearbiliFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = BilibiliPink,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(rememberMutableInteractionSource(), null, onClick = {
                finish()
                val intent = Intent(this@CrashActivityScreen, MainActivity::class.java)
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                Process.killProcess(Process.myPid())
            })
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(Color.White, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            when (viewModel.uiState) {
                is UIState.Loading -> {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "有一只小可爱正在收集并上传一些信息",
                            fontFamily = wearbiliFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )
                        LinearProgressIndicator(color = BilibiliPink)
                    }
                }

                is UIState.Success -> {
                    QRCodeUtil.createQRCodeBitmap(
                        "https://wearbili.spacexc.net/issue/${viewModel.id}",
                        512,
                        512,
                        ERROR_CORRECTION_L
                    )?.asImageBitmap()?.let {
                        Image(
                            bitmap = it,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp)
                        )
                    }
                }

                is UIState.Failed -> {
                    Column {

                    }
                }
            }
        }
        Text(
            text = "疑难解答",
            fontFamily = wearbiliFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = BilibiliPink,
            textDecoration = TextDecoration.Underline
        )
        Text(
            text = "Don't panic, c'est la vie\nwe hate bugs, but they make up features",
            fontFamily = wearbiliFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 8.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}