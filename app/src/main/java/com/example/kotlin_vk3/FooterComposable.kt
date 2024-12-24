package com.example.kotlin_vk3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
@Composable
fun Footer(navController: NavHostController){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
            .background(Color.White)
    )

    {
        FooterButton(
            imgId = R.drawable.main,
            textId = stringResource(R.string.main),
            navController,
            Routes.Main.route
        )
        Spacer(modifier = Modifier.width(30.dp))
        FooterButton(
            imgId = R.drawable.favourites,
            textId = stringResource(R.string.bookmarked),
            navController,
            Routes.Bookmarked.route
        )
        Spacer(modifier = Modifier.width(30.dp))
        FooterButton(
            imgId = R.drawable.search,
            textId = stringResource(R.string.search),
            navController,
            Routes.Search.route
        )
        Spacer(modifier = Modifier.width(30.dp))
        FooterButton(
            imgId = R.drawable.profile,
            textId = stringResource(R.string.profile),
            navController,
            Routes.Main.route
        )
    }
}

@Composable
fun FooterButton(imgId:Int, textId:String, navController: NavHostController, navLink:String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.size(60.dp, 50.dp)
    ) {
        Image(
            painter = painterResource(id = imgId),
            contentDescription = "",
            modifier = Modifier.size(18.dp).clickable { navController.navigate(navLink) }
        )
        Text(text = textId, fontSize = 11.sp)
    }
}