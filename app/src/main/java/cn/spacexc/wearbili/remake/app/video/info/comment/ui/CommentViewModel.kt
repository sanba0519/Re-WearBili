package cn.spacexc.wearbili.remake.app.video.info.comment.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import cn.spacexc.bilibilisdk.sdk.video.info.remote.comment.paging.CommentPagingSource
import javax.inject.Inject

/**
 * Created by XC-Qan on 2023/4/28.
 * I'm very cute so please be nice to my code!
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 */

class CommentViewModel @Inject constructor(
    videoAid: String
) : ViewModel() {
    /*
        val commentPagingSources = HashMap<String, Flow<PagingData<CommentContentData>>>()
    */
    private val commentLazyColumnScrollState = HashMap<String, LazyListState>()

    val flow = Pager(config = PagingConfig(pageSize = 1)) {
        CommentPagingSource(
            oid = videoAid
        )
    }.flow.cachedIn(viewModelScope)
    /*

        @Composable
        fun commentListFlow(oid: String?): Flow<PagingData<CommentContentData>>? {
            if (oid.isNullOrEmpty()) return null
            if (commentPagingSources[oid] != null) commentPagingSources[oid]
            else
                commentPagingSources[oid] = Pager(config = PagingConfig(pageSize = 1)) {
                    CommentPagingSource(
                        networkUtils = networkUtils,
                        oid = oid
                    )
                }.flow.cachedIn(viewModelScope)

            val currentPagingSource = commentPagingSources[oid]
            Log.d(TAG, "commentListFlow: $currentPagingSource")
            return currentPagingSource
        }
    */

    fun getScrollState(oid: String?): LazyListState? {
        if (oid == null) return null
        if(commentLazyColumnScrollState[oid] == null) {
            commentLazyColumnScrollState[oid] = LazyListState()
        }
        return commentLazyColumnScrollState[oid]
    }
}