import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gemipost.R
import com.example.gemipost.ui.post.create.component.CreatePostTopBar
import com.example.gemipost.ui.post.create.component.FilesRow
import com.example.gemipost.ui.post.create.component.MyTextFieldBody
import com.example.gemipost.ui.post.create.component.MyTextFieldTitle
import com.example.gemipost.ui.post.create.component.NewTagAlertDialog
import com.example.gemipost.ui.post.create.component.TagsRow
import com.example.gemipost.ui.post.edit.EditPostAction
import com.example.gemipost.ui.post.edit.EditPostUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditPostContent(
    action: (EditPostAction) -> Unit,
    state: EditPostUIState,

    ) {
    var newTagDialogState by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CreatePostTopBar(
                onBackClick = { action(EditPostAction.NavigateBack) },
                stringResource(R.string.edit_post)
            )
        }
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {


            Spacer(modifier = Modifier.height(8.dp))
            FilesRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                state.postAttachments,
                onFileDelete = { file ->
                    action(EditPostAction.OnFileRemoved(file))
                },
                onAddFile = {
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            MyTextFieldTitle(
                value = state.title,
                label = "Title",
                onValueChange = { newTitle ->
                    action(EditPostAction.OnTitleChanged(newTitle))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            MyTextFieldBody(
                value = state.body,
                label = "Body",
                onValueChange = { newBody ->
                    action(EditPostAction.OnContentChanged(newBody))
                },
                tags = state.postTags.toList(),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            TagsRow(
                selectedTags = state.postTags.toList(),
                onTagClick = { tag ->
                    action(EditPostAction.OnTagAdded(tag))
                },
                onAddNewTagClick = {
                    newTagDialogState = true
                }
            )
            Button(
                onClick = {
                    action(EditPostAction.OnApplyEditClicked)
                },
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                enabled = state.title.isNotBlank() && state.body.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(
                    text = "+ Post"
                )
            }
        }
        if (newTagDialogState) {
            NewTagAlertDialog(
                newTagDialogState = { value ->
                    newTagDialogState = value
                },
                confirmNewTags = {
                    action(EditPostAction.OnTagAdded(it))
                },
            )

        }
    }
}







