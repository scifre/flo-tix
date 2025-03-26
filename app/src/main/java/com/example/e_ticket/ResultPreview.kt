package com.example.e_ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_ticket.ui.theme.Purple40
import com.example.e_ticket.ui.theme.PurpleLight
import com.example.e_ticket.ui.theme.poppinsFontFamily
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ResultScreenPreview(){

    var isLoading by remember{ mutableStateOf(false) }
    var attendee by remember{ mutableStateOf<Attendee?>(null) }

    @Composable
    fun TextBoxSmall(text: String){
        Text(
            text = text,
            fontSize = 25.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Monospace,
            color = Color.DarkGray
        )
    }

    @Composable
    fun TextBoxBig(text: String, modifier: Modifier = Modifier, fontSize: TextUnit = 35.sp){
        Text(
            text = text,
            fontSize = fontSize,
            fontFamily = poppinsFontFamily,
            color = Color.Blue
        )
    }



    //main UI
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Attendee Details",
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Arrow",
                            modifier = Modifier.size(40.dp),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue
                )
            )
        }
    ) {paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(all = 8.dp)
            .fillMaxSize()
        ){
            if(isLoading){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(100.dp),
                        color = Color.Blue
                    )
                }
            }
            else if(attendee == null) {
                if(false){
                    Text(
                        text = "This pass is already scanned",
                        color = Color.White,
                        fontSize = 40.sp,
                        modifier = Modifier.background(Color.Red)
                    )
                }
                TextBoxSmall("Name:")
                TextBoxBig(
                    text = "Ayush Yadav",
                )

                Spacer(modifier = Modifier.size(20.dp))
                TextBoxSmall("College/Organisation:")
                TextBoxBig("NIT Raipur",)

                Spacer(modifier = Modifier.size(20.dp))
                TextBoxSmall("Phone:")
                TextBoxBig("0987654321")

                Spacer(modifier = Modifier.size(20.dp))
                TextBoxSmall("Email:")
                TextBoxBig(
                    text = "ayushaydavi@gmail.com",
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.size(20.dp))

                TextBoxBig(
                    text = "Executive Pass"
                )

                Spacer(modifier = Modifier.size(20.dp))
                Icon(
                    painter = painterResource(R.drawable.green_tick),
                    contentDescription = "Scan Card",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable(onClick = {}),
                    tint = Green,

                    )

            }
            else{
                Text(
                    text = "No data found",
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic

                )
            }
        }
    }

}