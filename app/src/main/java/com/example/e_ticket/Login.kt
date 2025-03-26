package com.example.e_ticket


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_ticket.ui.theme.poppinsFontFamily
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text



@Composable
fun LoginScreen(navController: NavController){
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var isEmailFocused by remember{ mutableStateOf(false) }
    var isPasswordFocused by remember{ mutableStateOf(false) }


    fun onLoginClicked(){
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    navController.navigate("home")
                }
                else{
                    email = ""
                    password = ""

                }
            }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ){paddingValues->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(all = 30.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.size(50.dp))
            Image(
                painter = painterResource(R.drawable.ft_logo),

                contentDescription = "App_Icon"
            )
            Text(
                text = "Login",
                fontSize = 50.sp,
                color = Color.Blue,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.align(Alignment.CenterHorizontally)

            )

            Spacer(Modifier.size(50.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {newEmail: String -> email = newEmail},
                label = {
                    Text(
                        text = "Email",
                        fontSize = if(isEmailFocused || email.isNotEmpty()) 15.sp else 20.sp,

                    )
                },
                textStyle = TextStyle(
                    fontSize = 20.sp
                ),
                modifier = Modifier.onFocusChanged {focus-> isEmailFocused = focus.isFocused
                }
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Enter your Email",
                        fontSize = 20.sp,
                        color = Color.Gray

                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Blue,
                    focusedLabelColor = Color.Blue
                )


            )

            Spacer(Modifier.size(30.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {newPassword: String -> password = newPassword},
                label = {
                    Text(
                        text = "Password",
                        fontSize = if(isPasswordFocused || password.isNotEmpty()) 15.sp else 20.sp,
                    )
                },
                textStyle = TextStyle(
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .onFocusChanged {focus->
                        isPasswordFocused = focus.isFocused
                    }
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Enter your Password",
                        fontSize = 20.sp,
                        color = Color.Gray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Blue,
                    focusedLabelColor = Color.Blue
                )

            )
            Spacer(modifier = Modifier.size(30.dp))

            //make the button below to match the size of the parent column

            Button(
                onClick = {onLoginClicked()},
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ){
                Text(
                    text = "Login",
                    fontSize = 20.sp,

                )
            }

        }
    }
}