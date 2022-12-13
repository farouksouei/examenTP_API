package com.example.examentp_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.examentp_api.ui.theme.Album
import com.example.examentp_api.ui.theme.ExamenTP_APITheme
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenTP_APITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var value1 by remember { mutableStateOf("") };
                    TextField(
                        value = value1,
                        onValueChange ={value1=it},
                        placeholder = {Text("Taper votre text")},
                        label={Text("text1")},
                        modifier = Modifier.padding(all = 10.dp)
                            .fillMaxWidth()

                    )
                    //button
                    Button(onClick = {
                        getphotosById2(value1.toInt())
                    }) {
                        Text("Click me to Search")
                    }

                }
            }
        }
    }
}

fun getAlbums(): List<Album> {
    val albumApi = RetrofitHelper.getInstance().create(AlbumAPI::class.java)
    lateinit var albumList: Deferred<List<Album>>
    lateinit var albumList1: List<Album>
    runBlocking {
        albumList = async {
            albumApi.getPhotos().body()!!
        }
        albumList1 = albumList.await()
    }
    return albumList1
}

fun getphotosById2(int: Int): List<Album> {
    val albumApi = RetrofitHelper.getInstance().create(AlbumAPI::class.java)
    lateinit var albumList: Deferred<List<Album>>
    lateinit var albumList1: List<Album>
    runBlocking {
        albumList = async {
            albumApi.getphotosById(Int).body()!!
        }
        albumList1 = albumList.await()
    }
    return albumList1
}

@Composable
fun Album(album: Album) {
    //make a container for each album
    Text(text = album.title , style = MaterialTheme.typography.h4)
    //make an image for each album with the url
    Image(
        painter = rememberAsyncImagePainter(album.url,),
        contentDescription = null,
        modifier = Modifier.size(128.dp)
    )
}

@Composable
fun AlbumList(albums: List<Album>) {
    LazyColumn(content = {
        items(albums.size) {
            Album(albums[it])
        }
    })
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExamenTP_APITheme {
        Greeting("Android")
    }
}