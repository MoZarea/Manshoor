package com.example.gemipost.ui.post.feed.components

import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(
    pageCount: Int,
    images: List<String> = emptyList(),
    width: Dp,
    onImageClicked: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(height = 300.dp, width = width)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
    ) {
        val pagerState = rememberPagerState(
            pageCount = { pageCount },
        )
        val indicatorScrollState = rememberLazyListState()

        LaunchedEffect(key1 = pagerState.currentPage, block = {
            val currentPage = pagerState.currentPage
            val size = indicatorScrollState.layoutInfo.visibleItemsInfo.size
            val lastVisibleIndex =
                indicatorScrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val firstVisibleItemIndex = indicatorScrollState.firstVisibleItemIndex

            if (currentPage > lastVisibleIndex - 1) {
                indicatorScrollState.animateScrollToItem(currentPage - size + 2)
            } else if (currentPage <= firstVisibleItemIndex + 1) {
                indicatorScrollState.animateScrollToItem(maxOf(0, currentPage - 1))
            }
        })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onImageClicked(images[pagerState.currentPage])
                    }
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                GlideImage(
                    imageModel = {
                        Uri.parse(images[pagerState.currentPage])
                    },
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                )
            }
        }
        if (images.size > 1) {
            LazyRow(
                state = indicatorScrollState,
                modifier = Modifier
                    .offset(y = (-16).dp)
                    .height(25.dp)
                    .width(((6 + 16) * 2 + 3 * (10 + 16)).dp)
                    .background(Color.LightGray.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    item(key = "item$iteration") {
                        val currentPage = pagerState.currentPage
                        val firstVisibleIndex by remember { derivedStateOf { indicatorScrollState.firstVisibleItemIndex } }
                        val lastVisibleIndex =
                            indicatorScrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                                ?: 0
                        val size by animateDpAsState(
                            targetValue = when (iteration) {
                                currentPage -> {
                                    10.dp
                                }

                                in firstVisibleIndex + 1..<lastVisibleIndex -> {
                                    10.dp
                                }

                                else -> {
                                    6.dp
                                }
                            }, label = ""
                        )
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(color, CircleShape)
                                .size(
                                    size
                                )
                        )
                    }
                }
            }
        }
    }
}