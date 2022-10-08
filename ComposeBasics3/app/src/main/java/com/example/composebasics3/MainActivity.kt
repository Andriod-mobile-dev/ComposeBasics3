package com.example.composebasics3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composebasics3.ui.theme.ComposeBasics3Theme

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
//    val listItems = listOf(
//        ListItem(R.drawable.android_image_1, "Image 1", "Details about image 1\nDetails about image 1"),
//        ListItem(R.drawable.android_image_2, "Image 2", "Details about image 2\nDetails about image 2"),
//        ListItem(R.drawable.android_image_3, "Image 3", "Details about image 3\nDetails about image 3"),
//        ListItem(R.drawable.android_image_4, "Image 4", "Details about image 4\nDetails about image 4"),
//        ListItem(R.drawable.android_image_5, "Image 5", "Details about image 5\nDetails about image 5"),
//        ListItem(R.drawable.android_image_6, "Image 6", "Details about image 6\nDetails about image 6"),
//        ListItem(R.drawable.android_image_7, "Image 7", "Details about image 7\nDetails about image 7"),
//        ListItem(R.drawable.android_image_8, "Image 8", "Details about image 8\nDetails about image 8")
//
//    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyList()

//            LazyColumn{
//
//                itemsIndexed(
//
//                    // pass a list of any type
//                    //listOf("This", "is", "a", "sample", "list")
//                    listItems
//
//                ) { index, item -> // 2nd param is a member of the list
//
//                    ItemCard(item = item)
//
//                    Divider(
//                        color = MaterialTheme.colors.primary,
//                        thickness = 2.dp,
//                        startIndent = 0.dp
//                    )
////                    Text(
////                        text = string,
////                        fontSize = 24.sp,
////                        fontWeight = FontWeight.Bold,
////                        textAlign = TextAlign.Center,
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .padding(vertical = 24.dp)
////                    )
//
//                }// itemsIndexed
//
//
//
////                items(count = 5000){
////                    Text(
////                        text = "Item $it", // use the item index from items
////                        fontSize = 24.sp,
////                        fontWeight = FontWeight.Bold,
////                        textAlign = TextAlign.Center,
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .padding(vertical = 24.dp)
////                    )
////                }
//
//
////               // add a single item
////               item {
////                   Text(text = "First Item")
////               }
////
////                // add 5 items
////                items(5){ index ->
////                    Text(text="Item: $index")
////                }
////
////                // add another single item
////                item {
////                    Text(text = "Last Item")
////                }
//
//            } // LazyColumn

        } // setContent
    } // onCreate
} // MainActivity


data class ListItem(val image: Int, val title: String, val detail: String,
    var isSelected: MutableState<Boolean> = mutableStateOf(false))

@Composable
fun ItemCard(item: ListItem, updateSelected: (Boolean) -> Unit) {

    var isSelected by remember {
        mutableStateOf(false)
    }

    val targetColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary

        else Color.Transparent,

        // define an animation spec to slow it down
        animationSpec = tween(4000)
    )
    //Surface(color = targetColor) {
        Row(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .clickable {
                    updateSelected(!item.isSelected.value)
                }
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column{
                Row{
                    Image(
                        painter = painterResource(id = item.image),
                        contentDescription = "Contact profile picture",
                        modifier = Modifier
                            .size(40.dp) // set the image size
                            .clip(CircleShape) // clip image to a circle shape
                            .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                    ) // Image

                    Spacer(modifier = Modifier.width(8.dp))

                    // keep track whether our card is expanded or not
                    var isExpanded by remember { mutableStateOf(false) }

                    // surfaceColor will update gradually from one color to the other

                    val surfaceColor: Color by animateColorAsState(
                        if (isExpanded) MaterialTheme.colors.secondary else MaterialTheme.colors.surface
                    )

                    // toggle the isExpanded variable when we click on the column
                    Column(
                        modifier = Modifier.clickable {
                            isExpanded = !isExpanded
                        }
                    ) {
                        Text(
                            text = item.title,
                            color = MaterialTheme.colors.secondaryVariant,
                            style = MaterialTheme.typography.subtitle2
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // provide a background for the text
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            elevation = 1.dp,
                            color = surfaceColor, // gradually change from primary to surface
                            modifier = Modifier
                                .animateContentSize()
                                .padding(1.dp) // animate the size gradually
                        )
                        {
                            Text(
                                text = item.detail,
                                modifier = Modifier.padding(all = 4.dp),
                                // set the number of lines based on isExpanded
                                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                                style = MaterialTheme.typography.body2
                            )
                        } // Surface
                    } // Text Column
                } // image and text
            }  // Row Column
            Column{
                if(item.isSelected.value){
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Selected",
                        tint = Color.Green,
                        modifier = Modifier.size(20.dp)
                    )
                }
            } // Checkmark Column

        } // Row
    // }
} // ItemCard


@Composable
@ExperimentalMaterialApi
@SuppressLint("UnrememberedMutableState")
fun MyList(){

    val listState = rememberLazyListState()

    var listItems =
       mutableStateListOf(

                ListItem(R.drawable.android_image_1, "Image 1", "Details about image 1\nDetails about image 1"),
                ListItem(R.drawable.android_image_2, "Image 2", "Details about image 2\nDetails about image 2"),
                ListItem(R.drawable.android_image_3, "Image 3", "Details about image 3\nDetails about image 3"),
                ListItem(R.drawable.android_image_4, "Image 4", "Details about image 4\nDetails about image 4"),
                ListItem(R.drawable.android_image_5, "Image 5", "Details about image 5\nDetails about image 5"),
                ListItem(R.drawable.android_image_6, "Image 6", "Details about image 6\nDetails about image 6"),
                ListItem(R.drawable.android_image_7, "Image 7", "Details about image 7\nDetails about image 7"),
                ListItem(R.drawable.android_image_8, "Image 8", "Details about image 8\nDetails about image 8",)

            ) // mutableStateOf

    LazyColumn(
//        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
//        verticalArrangement = Arrangement.spacedBy(4.dp)
    modifier = Modifier
        .fillMaxSize(),

    listState
    ){

        itemsIndexed(

            // pass a list of any type
            //listOf("This", "is", "a", "sample", "list")
            listItems,
            key = {_, listItem ->
                listItem.hashCode()
            }

        ) { index, item -> // 2nd param is a member of the list
            val state = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        listItems.remove(item)
                    }
                    true
                }
            )

            SwipeToDismiss(
                state = state,
                background = {
                    val color = when (state.dismissDirection) {
                        DismissDirection.StartToEnd -> Color.Transparent
                        DismissDirection.EndToStart -> Color.Red
                        null -> Color.Transparent
                    } // when
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = color)
                            .padding(10.dp)

                    ) {

                        if (state.dismissDirection == DismissDirection.EndToStart) {
                            Icon(imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Gray,
                            modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    } // Box
                }, // background
                dismissContent = {
                    if (listItems.count() > 0) {
                        ItemCard(
                            item = item,
                            updateSelected = { selected ->
                                item.isSelected.value = selected
                            }
                        )
                    }
                }, // dismissContent
                directions = setOf(DismissDirection.EndToStart)
            ) // SwipeToDismiss

//            ItemCard(item = item,
//                // function to increase count
//                updateSelected = { selected ->
//                   item.isSelected.value = selected
//                }
//            )

            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 2.dp,
                startIndent = 0.dp
            )
//                    Text(
//                        text = string,
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 24.dp)
//                    )

        }// itemsIndexed

        item{

            if(listItems.count() == 0){

                Column(
                    modifier = Modifier.padding(all = 8.dp)
                ) {
                    Text(
                        text = "No Items to Display",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(all = 4.dp),
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h2
                    )
                } // Column

            } // if

        } // empty list item



//                items(count = 5000){
//                    Text(
//                        text = "Item $it", // use the item index from items
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 24.dp)
//                    )
//                }


//               // add a single item
//               item {
//                   Text(text = "First Item")
//               }
//
//                // add 5 items
//                items(5){ index ->
//                    Text(text="Item: $index")
//                }
//
//                // add another single item
//                item {
//                    Text(text = "Last Item")
//                }

    } // LazyColumn



} // MyList