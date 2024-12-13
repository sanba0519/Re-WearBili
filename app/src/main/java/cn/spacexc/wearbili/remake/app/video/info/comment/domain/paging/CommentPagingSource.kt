package cn.spacexc.bilibilisdk.sdk.video.info.remote.comment.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cn.spacexc.bilibilisdk.sdk.video.info.VideoInfo
import cn.spacexc.bilibilisdk.sdk.video.info.remote.comment.CommentContentData

/**
 * Created by XC-Qan on 2023/4/28.
 * I'm very cute so please be nice to my code!
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 */

class CommentPagingSource(
    private val oid: String
) : PagingSource<Int, CommentContentData>() {
    override fun getRefreshKey(state: PagingState<Int, CommentContentData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommentContentData> {
        try {
            val currentPage = params.key ?: 1
            val response =
                VideoInfo.getVideoComments(oid, currentPage)
            if (response.code != 0) return LoadResult.Error(
                cn.spacexc.wearbili.common.exception.PagingDataLoadFailedException(
                    apiUrl = response.apiUrl,
                    code = response.code
                )
            )
            val commentList = response.data?.data?.replies ?: emptyList()
            val topComment = response.data?.data?.top?.upper?.apply { is_top = true }
            val topCommentList = buildList {
                if (topComment != null) {
                    add(topComment)
                }
            }
            val isEnd = response.data?.data?.cursor?.is_end ?: false
            return LoadResult.Page(
                data = if (currentPage == 1) topCommentList + commentList else commentList,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (isEnd) null else currentPage + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}