package com.example.kotlin_vk3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest
import kotlinx.coroutines.delay

@Composable
fun MoviesList(movies: List<MovieShort>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        items(movies) { movie -> MovieCard(movie) }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
    }

@Composable
fun MovieCard(movieShort: MovieShort) {
    val ratingColor = when(movieShort.rating){
        in 8.0..10.0 -> colorResource(R.color.good_rating)
        in 4.0..8.0 -> colorResource(R.color.ok_rating)
        else -> colorResource(R.color.bad_rating)
    }
    Box(
        modifier = Modifier
            .size(184.dp, 322.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            MovieImage(imgUrl = movieShort.imgUrl)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Text(text = movieShort.title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Text(text = movieShort.year.toString(), fontSize = 15.sp)
            }
        }
        if (!movieShort.isBookmarked) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .offset(146.dp, 4.dp)
                    .background(colorResource(R.color.orange))
                    .clickable { }

            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.bookmark_add),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }
        }
        Box(
            modifier = Modifier
                .size(55.dp, 26.dp)
                .offset(126.dp, 218.dp)
                .background(ratingColor)
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.star),
                    contentDescription = "",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = movieShort.rating.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.White)
                )
            }
        }
    }
}

@Composable
fun MovieImage(imgUrl: String) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        delay(3000)
    }
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context).data(imgUrl).build(),
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .height(248.dp)
    ) {

        val state by painter.state.collectAsState()

        when (state) {
            is AsyncImagePainter.State.Success -> {

                SubcomposeAsyncImageContent(
                    modifier = Modifier
                        .fillMaxSize(), contentScale = ContentScale.Crop
                )
            }

            else -> {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            }
        }
    }

}