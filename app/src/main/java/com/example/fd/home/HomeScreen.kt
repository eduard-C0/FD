package com.example.fd.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.musicstreaming.services.dtos.Artist
import com.example.musicstreaming.services.dtos.Track

private const val APIKEY = "apikey"
private const val APIKEY_VALUE = "M2ExNTkyZWUtOWVlOS00NDU0LWJhOTEtYWFmMjI0NGZhNTM5\n"
private const val URL_BASE = "https://api.napster.com/imageserver/v2/albums/"
private const val URL_BASE_ARTIST_IMAGE_SERVER =
    "https://api.napster.com/imageserver/v2/artists/"
private const val IMAGE_SIZE = 170
private const val IMAGE_SIZE_ARTIST_W = 150
private const val IMAGE_SIZE_ARTIST_H = 100

enum class HomeTabs {
    HOME, SEARCH
}

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val tabIndex = rememberSaveable { mutableStateOf(HomeTabs.HOME.ordinal) }
    val tabData = listOf(HomeTabs.HOME.name, HomeTabs.SEARCH.name)

    LaunchedEffect(Unit) {
        viewModel.getTopArtists()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        when (tabIndex.value) {
            HomeTabs.HOME.ordinal -> {
                HomeContent(modifier = Modifier.weight(1f, true), viewModel = viewModel)
            }

            HomeTabs.SEARCH.ordinal -> {
                SearchContent(modifier = Modifier.weight(1f, true), viewModel)
            }
        }

        TabRow(selectedTabIndex = tabIndex.value) {
            tabData.forEachIndexed { index, string ->
                Tab(tabIndex.value == index, onClick = { tabIndex.value = index }, text = { Text(text = string) })
            }
        }
    }


}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArtistItem(artist: Artist) {
    val urlImageSize = "/images/${IMAGE_SIZE_ARTIST_W}x${IMAGE_SIZE_ARTIST_H}.jpg"
    val url = "${URL_BASE_ARTIST_IMAGE_SERVER}${artist.id}$urlImageSize"
    Card(elevation = CardDefaults.cardElevation(2.dp), shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(Color.LightGray)) {
        Column {
            Row(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    model = GlideUrl(
                        url, LazyHeaders.Builder()
                            .addHeader(APIKEY, APIKEY_VALUE)
                            .build()
                    ), contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(48.dp),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = artist.name, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow {
                artist.topTracks?.let {
                    items(it) {
                        TrackItem(track = it)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TrackItem(track: Track) {
    val urlImageSize = "/images/${IMAGE_SIZE}x${IMAGE_SIZE}.jpg"
    val url = "$URL_BASE${track.albumId}$urlImageSize"
    val trackPictureSize = 84.dp
    Column(
        modifier = Modifier
            .width(trackPictureSize)
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = GlideUrl(
                url, LazyHeaders.Builder()
                    .addHeader(APIKEY, APIKEY_VALUE)
                    .build()
            ), contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(trackPictureSize),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        ) {
            it.error {
                Log.d("Picture", "not ok")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = track.name, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun HomeContent(modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    val artists = viewModel.topArtists.observeAsState()
    val loading = viewModel.isLoadingHome.observeAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (loading.value == true) {
            CircularProgressIndicator(color = Color.Black, modifier = Modifier.align(CenterHorizontally))
        } else {
            artists.value?.let {
                LazyColumn {
                    items(it) {
                        ArtistItem(artist = it)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    var searchInputText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val searchResult = viewModel.searchTracks.observeAsState()
    val loading = viewModel.isLoadingSearch.observeAsState()

    Column(modifier = modifier.padding(start = 4.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = searchInputText, onValueChange = { searchInputText = it }, label = { Text(text = "Search") })
            Spacer(modifier = Modifier.width(12.dp))
            Button(onClick = { viewModel.search(searchInputText.text) }) {
                Text(text = "Search")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            if (loading.value == true) {
                CircularProgressIndicator(color = Color.Black, modifier = Modifier.align(CenterHorizontally))
            } else {
                LazyColumn(modifier = Modifier.weight(1f, true)) {
                    searchResult.value?.let { results ->
                        items(results) {
                            SearchTrackItem(track = it)
                        }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SearchTrackItem(track: Track) {
    val urlImageSize = "/images/${IMAGE_SIZE}x${IMAGE_SIZE}.jpg"
    val url = "$URL_BASE${track.albumId}$urlImageSize"
    val trackPictureSize = 84.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = GlideUrl(
                url, LazyHeaders.Builder()
                    .addHeader(APIKEY, APIKEY_VALUE)
                    .build()
            ), contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(trackPictureSize),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        ) {
            it.error {
                Log.d("Picture", "not ok")
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = track.name, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = track.artistName, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}