package com.example.e_ticket

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_ticket.ui.theme.PurpleLight
import com.example.e_ticket.ui.theme.poppinsFontFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreen(navController: NavController? = null){
    val context = LocalContext.current
    //@Composable
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    val qw = buildAnnotatedString {
        withStyle(SpanStyle(fontStyle = FontStyle.Italic, color = Blue)) {
            append("Flo")
        }
        withStyle(SpanStyle(color = Red, fontStyle = FontStyle.Italic)) {
            append("Tix")
        }
    }

    var isCameraPermissionDialogBoxVisible by remember { mutableStateOf(false)}

    @Composable
    fun CameraPermissionDialogBox(){

    }
    Scaffold(

    ){ paddingValues->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
            //verticalArrangement = Arrangement.Center,
        ){
            Spacer(modifier = Modifier.size(100.dp))
            Image(
                painter = painterResource(R.drawable.ft_logo),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                contentDescription = "App_Icon"
            )
            Text(
                text = buildAnnotatedString {
                    append("Welcome to \n")
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic, color = Blue)) {
                        append("Flo")
                    }
                    withStyle(SpanStyle(color = Red, fontStyle = FontStyle.Italic)) {
                        append("Tix")
                    }
                },
                fontSize = 60.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(50.dp))

            Button(
                onClick ={
                    if(cameraPermissionState.status.isGranted){
                        navController?.navigate("scan")
                    }
                    else{
                        // ask for permission
                        cameraPermissionState.launchPermissionRequest()
                        if(cameraPermissionState.status.isGranted){
                            navController?.navigate("scan")
                        }
                        else{
                            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    contentColor = Color.White
                )
            ){
                Text(
                    text = "Scan",
                    fontSize = 50.sp,
                    )
            }
            Spacer(modifier = Modifier.size(200.dp))
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController?.navigate("login"){
                        popUpTo("login"){inclusive=true}
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),

                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = White,
                    containerColor = Blue
                )
            ){
                Text(
                    "Log Out",
                    fontSize = 30.sp
                )
            }


        }
    }
}