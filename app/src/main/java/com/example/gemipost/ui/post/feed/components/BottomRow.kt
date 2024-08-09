import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.alpha
import com.gp.socialapp.presentation.post.feed.components.icons.FeedIcons
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Comment
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Dislikefilled
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Dislikeoutlined
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Likefilled
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Likeoutlined
import kotlinx.coroutines.launch

@Composable
fun BottomRow(
    upVotes: List<String>,
    downVotes: List<String>,
    commentCount: Int,
    votes: Int,
    onUpVoteClicked: () -> Unit,
    onDownVoteClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    currentUserID: String,
    onShareClicked: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var animatedVotes by remember { mutableStateOf(votes) }
    var isUpvoted by remember { mutableStateOf(upVotes.contains(currentUserID)) }
    var isDownvoted by remember { mutableStateOf(downVotes.contains(currentUserID)) }


    val rotation by animateFloatAsState(if (isUpvoted || isDownvoted) 360f else 0f, label = "")
    val colorTransition by animateColorAsState(
        targetValue = when {
            isUpvoted -> MaterialTheme.colorScheme.primary
            isDownvoted -> MaterialTheme.colorScheme.error
            else -> MaterialTheme.colorScheme.onPrimaryContainer
        },
        animationSpec = tween(durationMillis = 500), label = ""
    )
    val bounceAnim = remember { Animatable(1f) }

    LaunchedEffect(animatedVotes) {
        bounceAnim.animateTo(
            targetValue = 1.2f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
        )
        bounceAnim.animateTo(
            targetValue = 1f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .sizeIn(maxHeight = 35.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        FilledTonalButton(
            onClick = {
                scope.launch {
                    isUpvoted = !isUpvoted
                    if (isUpvoted) isDownvoted = false
                    animatedVotes += if (isUpvoted) 1 else -1
                    onUpVoteClicked()
                }
            },
            contentPadding = PaddingValues(6.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = colorTransition,
                contentColor = Color.White
            ),
            modifier = Modifier.rotate(rotation)
        ) {
            Icon(
                imageVector = if (isUpvoted) FeedIcons.Likefilled else FeedIcons.Likeoutlined,
                contentDescription = "UpVote",
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = animatedVotes.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .sizeIn(minWidth = 20.dp)
                    .scale(bounceAnim.value),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = if (isDownvoted) FeedIcons.Dislikefilled else FeedIcons.Dislikeoutlined,
                contentDescription = "DownVote",
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            isDownvoted = !isDownvoted
                            if (isDownvoted) isUpvoted = false
                            animatedVotes += if (isDownvoted) -1 else 1
                            onDownVoteClicked()
                        }
                    }
                    .rotate(-rotation)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        FilledTonalButton(
            onClick = onCommentClicked,
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = FeedIcons.Comment,
                    contentDescription = "Comment",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(20.dp)
                )
                Text(
                    text = commentCount.toString(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        FilledTonalButton(
            onClick = onShareClicked,
            enabled = true,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Post",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}
