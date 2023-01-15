package io.howto.howtocompose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.howto.howtocompose.ui.theme.HowToComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HowToComposeTheme {
                val nc = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        TopAppBar(
                            elevation = 4.dp,
                            title = { Text("智能宠物项圈APP") },
                            backgroundColor = MaterialTheme.colors.primarySurface,
                            navigationIcon = {
                                IconButton(onClick = { nc.navigateUp() }) {
                                    Icon(Icons.Filled.ArrowBack, null)
                                }
                            }
                        )
                        NavHost(
                            navController = nc,
                            startDestination = Pages.Home.name,
                        ) {
                            composable(Pages.FpvStream.name) {
                                FpvStream()
                            }
                            composable(Pages.Stats.name) {
                                Stats()
                            }
                            composable(Pages.Home.name) {
                                Home(nc)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Home(
    navController: NavController = rememberNavController()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            onClick = {
                navController.navigate(Pages.Stats.name)
            }) {
            Text("健康检测")
        }
        Button(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            onClick = {
                navController.navigate(Pages.FpvStream.name)
            }) {
            Text("摄相头")
        }
    }
}

@Composable
fun ReadingMonitor(reading: Reading) {
    Text("${reading.label}： ${reading.value} ${reading.unit}")
}

data class Reading(
    val label: String,
    val value: Number,
    val unit: String
)

@Preview(showBackground = true)
@Composable
fun Stats(
    navController: NavController = rememberNavController()
) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .padding(4.dp)
    ) {
        Column {
            ReadingMonitor(Reading("体温", 36.3, "摄氏度"))
            ReadingMonitor(Reading("心率", 83, "下每分钟"))
            ReadingMonitor(Reading("血氧", 97.5, "%"))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FpvStream(
    navController: NavController = rememberNavController()
) {
    Text(Pages.FpvStream.name)
}

enum class Pages {
    Home,
    Stats,
    FpvStream
}
