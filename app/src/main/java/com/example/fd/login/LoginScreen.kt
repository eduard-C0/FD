package com.example.fd.login

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fd.register.RegisterViewModel
import com.example.fd.ui.theme.FDTheme
import com.example.fd.ui.theme.Pink40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginClicked: () -> Unit, onCreateAccountClicked: () -> Unit, viewModel: LoginViewModel = hiltViewModel()) {
    var usernameInputText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var passwordInputText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val createAccountText = "Create account!"
    val context = LocalContext.current
    val registerText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black)) {
            append("If you do not have an account you can create one by clicking on ")
        }

        withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
            pushStringAnnotation(tag = createAccountText, annotation = createAccountText)
            append(createAccountText)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(value = usernameInputText, onValueChange = { usernameInputText = it }, label = { Text(text = "Username") })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = passwordInputText, onValueChange = { passwordInputText = it }, label = { Text(text = "Password") }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                viewModel.login(usernameInputText.text, passwordInputText.text, onComplete = {
                    if (it) {
                        onLoginClicked()
                    } else {
                        Toast.makeText(context,"Invalid credentials!",Toast.LENGTH_SHORT).show()
                    }
                })
            },
            colors = ButtonDefaults.buttonColors(Pink40, Pink40, Pink40, Pink40),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, Color.DarkGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Login", color = Color.White, modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ClickableText(registerText, onClick = { offset ->
            registerText.getStringAnnotations(offset, offset)
                .firstOrNull()?.let { onCreateAccountClicked() }
        })
    }
}


@Preview(showBackground = true)
@Composable
fun previewLoginScreen() {
    FDTheme {
        LoginScreen({}, {})
    }
}
