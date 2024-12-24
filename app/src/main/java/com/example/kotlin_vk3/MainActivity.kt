
package com.example.kotlin_vk3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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

