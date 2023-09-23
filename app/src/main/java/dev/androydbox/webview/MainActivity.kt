package dev.androydbox.webview

import android.content.Context
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import dev.androydbox.webview.ui.theme.WebViewTheme
import org.w3c.dom.Text
import java.util.function.IntConsumer

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebViewTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.tertiary),
                        contentAlignment = Alignment.BottomCenter)

                {


                    //Greeting("Android")
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White))
                    {
                        Column {

                            var QuerryList by remember { mutableStateOf(listOf("item1","item2"))}
                            QuerryList(QuerryList)

                            Row(verticalAlignment = Alignment.CenterVertically) {


                                var text by remember { mutableStateOf("") }



                                TextField(modifier = Modifier.weight(1.0f),
                                    value = text,
                                    onValueChange = { text = it },
                                    label = { Text("Enter Text") }
                                )
                                Button(onClick = {
                                    QuerryList+=text
                                    text=""
                                    Log.d("MainActivity ",  "ButtonClicked")

                                }) {
                                    Text(text = "Button")


                                    Icon(
                                        imageVector = Icons.Filled.Send,
                                        contentDescription = "Send Message"
                                    )
                                }
                            }
                        }
                        }
                }

            }
        }
    }
}

@Composable
fun QuerryList(QuerryList:List<String>){
    LazyColumn(content = {
        items(QuerryList){
            item ->  Text(text=item)
        }
    })
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
   // WebViewComposable(url = "https://en.m.wikipedia.org/wiki/Sonamarg", context = LocalContext.current)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WebViewTheme {
        Greeting("Android")
    }
}

@Composable
fun WebViewComposable (url:String,context:Context) {
    AndroidView(factory = { context -> WebView(context).apply {
    layoutParams=ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT)
        webViewClient= WebViewClient()
        loadUrl(url)
    }})
}