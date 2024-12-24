@file:OptIn(ExperimentalFoundationApi::class)

package com.example.kotlin_vk3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Routes.Main.route
            ) {
                composable(route = Routes.Main.route) {
                    MainScreen(navController)
                }

                composable(route= Routes.Bookmarked.route) {
                    BookmarkedScreen(navController)
                }

                composable(route = Routes.Search.route) {
                    NothingFoundScreen(navController)
                }


            }
        }
    }
}

sealed class Routes(val route: String) {

    object Main : Routes("main")
    object Bookmarked : Routes("bookmarked")
    object Search : Routes("nothingFound")
}
@Composable
fun MainScreen(navController: NavHostController,viewModel: MovieViewModel = viewModel()){

    val movies by viewModel.movies.collectAsState()
    viewModel.fetchMovies()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.light_bg))) {
        Column {
            Header(stringResource(R.string.popular))
            MoviesList(movies)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
    ){
        Footer(navController)
    }

}
}

@Composable
fun BookmarkedScreen(navController: NavHostController,viewModel: MovieViewModel = viewModel()){
    val movies by viewModel.movies.collectAsState()
    viewModel.fetchBookmarkedMovies()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.light_bg))) {
        Column {
            Header(stringResource(R.string.bookmarked))
            MoviesList(movies)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ){
            Footer(navController)
        }

    }
}

@Composable
fun Header(headerTitle:String){
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
        .fillMaxWidth()
        .height(50.dp))

    {
        Spacer(modifier = Modifier.width(30.dp))
        Image(painter = painterResource(id = R.drawable.pumpkin), contentDescription = "", modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = headerTitle, fontSize = 24.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun Footer(navController:NavHostController){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
            .background(Color.White))

    {
        FooterButton(imgId =R.drawable.main, textId = stringResource(R.string.main),navController,Routes.Main.route)
        Spacer(modifier = Modifier.width(30.dp))
        FooterButton(imgId =R.drawable.favourites, textId = stringResource(R.string.bookmarked),navController,Routes.Bookmarked.route)
        Spacer(modifier = Modifier.width(30.dp))
        FooterButton(imgId =R.drawable.search, textId = stringResource(R.string.search),navController,Routes.Search.route)
        Spacer(modifier = Modifier.width(30.dp))
        FooterButton(imgId =R.drawable.profile , textId = stringResource(R.string.profile),navController,Routes.Main.route)
    }
}

@Composable
fun FooterButton(imgId:Int, textId:String, navController:NavHostController, navLink:String){
    Column(horizontalAlignment =  Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.size(60.dp,50.dp)) {
            Image(
                painter = painterResource(id = imgId),
                contentDescription = "",
                modifier = Modifier.size(18.dp).
                clickable {  navController.navigate(navLink)}
            )
            Text(text = textId,fontSize = 11.sp)
    }
}

@Composable
fun MoviesList(movies: List<MovieShort>){
        LazyVerticalGrid(columns = GridCells.Fixed(2),
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
    Box(modifier = Modifier
        .size(184.dp, 322.dp)
        .clip(RoundedCornerShape(8.dp))) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)) {
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
        if(!movieShort.isBookmarked) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .offset(146.dp, 4.dp)
                    .background(colorResource(R.color.orange))
                    .clickable {  }

            ) {
                Image(
                    painter = painterResource(id = R.drawable.bookmark_add),
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
                    painter = painterResource(id = R.drawable.star),
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
                    modifier =  Modifier
                    .fillMaxSize()
                    ,contentScale = ContentScale.Crop)
            }

            else -> {
                CircularProgressIndicator( modifier = Modifier.fillMaxSize())
            }
        }
    }

}

@Composable
fun NothingFoundScreen(navController:NavHostController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.light_bg))) {
        SearchHeader(searchString = "hello",navController)
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(id = R.drawable.nothing_found), contentDescription = "", modifier = Modifier.size(246.dp))

            Text(text = stringResource(R.string.nothing_found), fontSize = 24.sp, fontWeight = FontWeight.Medium)
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)) {
            Footer(navController)
        }
    }
}
@Composable
fun SearchHeader(searchString:String, navController: NavHostController){
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
        .fillMaxWidth()
        .height(50.dp))

    {
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = { navController.navigate(Routes.Main.route) },modifier = Modifier.size(28.dp,30.dp)) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = stringResource(R.string.search_results)+searchString, fontSize = 24.sp, fontWeight = FontWeight.Medium)
    }
}

