package com.example.rikmasterstz.ui.elements

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rikmasterstz.R
import com.example.rikmasterstz.ui.theme.LightGray
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun DoorBlock(
    name: String,
    snapshot: String?,
    isFavorite: Boolean,
    onFavorite: () -> Unit,
    onRename: () -> Unit
) {


    val favorite = SwipeAction(
        onSwipe = {
            onFavorite()
        },
        icon = {
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .border(1.dp, Color.Gray, CircleShape)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = if (!isFavorite) R.drawable.star_icon_outlined else R.drawable.star_icon_filled),
                    contentDescription = "Favorite check",
                    tint = Color.Yellow
                )
            }

        },
        background = Color.Transparent
    )
    val rename = SwipeAction(
        onSwipe = {
            onRename()
        },
        icon = {
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .border(1.dp, Color.Gray, CircleShape)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_icon),
                    contentDescription = "Rename check",
                    tint = Color.Blue
                )
            }

        },
        background = Color.Transparent
    )
    SwipeableActionsBox(
        endActions = listOf(favorite),
        startActions = listOf(rename),
        backgroundUntilSwipeThreshold = LightGray
    ) {
        if (snapshot != null) {
            Card(
                modifier = Modifier
                    .heightIn(max = 280.dp)
                    .aspectRatio(1.2f),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column {
                    Box {
                        Box(contentAlignment = Alignment.Center) {
                            AsyncImage(
                                model = snapshot,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(210.dp)
                                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                                contentDescription = "Camera Snapshot"
                            )
                            Box {

                                Icon(
                                    painter = painterResource(id = R.drawable.play_button),
                                    contentDescription = "Play Button",
                                    tint = Color.White
                                )

                            }


                        }
                        if (isFavorite) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.star_icon_filled),
                                    contentDescription = "star icon filled",

                                    )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = name, fontSize = 17.sp, fontFamily = FontFamily(
                                Font(R.font.circe_font)
                            ), color = Color.Black
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.lockon),
                            contentDescription = "Lock on"
                        )
                    }
                }
            }
        } else {
            Card(
                modifier = Modifier.widthIn(max = 336.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name, fontSize = 17.sp, fontFamily = FontFamily(
                            Font(R.font.circe_font)
                        ), color = Color.Black
                    )
                    Row (horizontalArrangement = Arrangement.spacedBy(15.dp)){
                        if (isFavorite) {
                            Image(
                                painter = painterResource(id = R.drawable.star_icon_filled),
                                contentDescription = "star icon filled",
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.lockon),
                            contentDescription = "Lock on"
                        )
                    }

                }
            }

        }

    }
}