package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.util.AppConstants.BASE_URL
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ExclamationTriangle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(
    pageCount: Int,
    images: List<PostAttachment> = emptyList(),
    width: Dp,
    onImageClicked: (PostAttachment) -> Unit
) {
    Box(
        modifier = Modifier.size(height = 300.dp, width = width).clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
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
                val imageURL = BASE_URL + images[pagerState.currentPage].url
                AutoSizeBox(imageURL) { action ->
                    when (action) {
                        is ImageAction.Success -> {
                            Image(
                                rememberImageSuccessPainter(
                                    action = action,
                                    filterQuality = FilterQuality.High
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.Center).fillMaxSize(),
                                contentScale = ContentScale.FillWidth                            )
                        }

                        is ImageAction.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }

                        is ImageAction.Failure -> {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.ExclamationTriangle,
                                contentDescription = null,
                                modifier = Modifier.size(36.dp).align(Alignment.Center),
                            )
                        }

                        else -> Unit
                    }
                }
            }
        }
        if(images.size > 1){
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