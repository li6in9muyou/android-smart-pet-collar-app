package io.howto.howtocompose


import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.howto.howtocompose.ui.theme.HowToComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HowToComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PreviewThreePages()
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(message: Message) {
    var needsExpansion by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor by animateColorAsState(
        targetValue = if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )

    Column(modifier = Modifier.padding(all = 8.dp)) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message.author,
                style = MaterialTheme.typography.subtitle2,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            color = surfaceColor,
            modifier = Modifier
                .clickable {
                    if (needsExpansion) {
                        isExpanded = !isExpanded
                    }
                }
                .animateContentSize()
                .padding(2.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = 2.dp
        ) {
            Text(
                text = "\"${message.body}\"",
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.body2,
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                onTextLayout = {
                    if (!isExpanded) {
                        needsExpansion = it.didOverflowHeight
                    }
                }
            )
        }
    }
}

enum class Pages {
    Conversation,
    Night,
    Day,
}

@Preview(
    name = "Three Pages",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewThreePages(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Pages.Day.name
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Pages.Day.name) {
            HowToComposeTheme {
                Surface {
                    MessageCard(
                        Message(
                            "开发者",
                            "This page is Day"
                        )
                    )
                }
            }
        }
        composable(route = Pages.Night.name) {
            HowToComposeTheme {
                Surface {
                    MessageCard(
                        Message(
                            "开发者",
                            "This page is Night"
                        )
                    )
                }
            }
        }
        composable(route = Pages.Conversation.name) {
            HowToComposeTheme {
                Conversation(SampleData.conversationSample)
            }
        }
    }
    Row {
        Button(onClick = { navController.navigate(Pages.Night.name) }) {
            Text("Night")
        }
        Button(onClick = { navController.navigate(Pages.Day.name) }) {
            Text("Day")
        }
        Button(onClick = { navController.navigate(Pages.Conversation.name) }) {
            Text("Conversation")
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewMessageCard() {
    HowToComposeTheme {
        Surface {
            MessageCard(
                Message(
                    "开发者",
                    "Compose is built to support Material Design principles." +
                            " Many of its UI elements implement Material " +
                            "Design out of the box. In this lesson, you'll style" +
                            " your app with Material Design widgets."
                )
            )
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Composable
fun PreviewConversation() {
    HowToComposeTheme {
        Conversation(SampleData.conversationSample)
    }
}

