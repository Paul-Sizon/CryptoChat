package com.crypto.chat.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.crypto.chat.navigation.Screen

@Composable
fun RegisterScreen(navController: NavController) {
    Surface {
        var credentials by remember { mutableStateOf(Credentials()) }
        var repeatedPassword by remember { mutableStateOf("") }
        val context = LocalContext.current

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            LoginField(
                value = credentials.login,
                onChange = { data -> credentials = credentials.copy(login = data) },
                modifier = Modifier.fillMaxWidth(),
                label = "Email",
                placeholder = "Enter your Email"
            )
            PasswordField(
                value = credentials.pwd,
                onChange = { data -> credentials = credentials.copy(pwd = data) },
                submit = {
                    registerUser(credentials, repeatedPassword, context)
                },
                modifier = Modifier.fillMaxWidth()
            )
            PasswordField(
                value = repeatedPassword,
                onChange = { data -> repeatedPassword = data },
                submit = {
                    registerUser(credentials, repeatedPassword, context)
                },
                modifier = Modifier.fillMaxWidth(),
                label = "Repeat Password",
                placeholder = "Repeat your Password"
            )
            PasswordStrengthIndicator(credentials.pwd)
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    registerUser(credentials, repeatedPassword, context)
                },
                enabled = credentials.isValid() && credentials.pwd == repeatedPassword,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    // Integrate Google Sign-In here
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register with Google")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Already have an account? Login", modifier = Modifier.clickable {
                navController.navigate(Screen.LoginScreen.route)
            })
        }
    }
}
@Composable
fun PasswordStrengthIndicator(password: String) {
    val strength = determinePasswordStrength(password)
    val progress = when (strength) {
        PasswordStrength.WEAK -> 0.25f
        PasswordStrength.MEDIUM -> 0.5f
        PasswordStrength.STRONG -> 0.75f
        PasswordStrength.VERY_STRONG -> 1f
    }
    val color = when (strength) {
        PasswordStrength.WEAK -> Color.Red
        PasswordStrength.MEDIUM -> Color.Yellow
        PasswordStrength.STRONG -> Color.Blue
        PasswordStrength.VERY_STRONG -> Color.Green
    }

    LinearProgressIndicator(
        progress = progress,
        color = color,
        modifier = Modifier.fillMaxWidth()
    )
}


fun registerUser(creds: Credentials, repeatedPassword: String, context: Context) {
    if (creds.isValid() && creds.pwd == repeatedPassword) {
        // Integrate Firebase Authentication to register the user.
        // For the sake of this example, we'll just show a toast.
        Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show()
    } else {
        if (!creds.isValid()) {
            Toast.makeText(context, "Password must be strong (9 chars, mix of cases, numbers, symbols)", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
        }
    }
}


fun Char.isSpecialChar(): Boolean {
    return this in '!'..'/' || this in ':'..'@' || this in '['..'`' || this in '{'..'~'
}

enum class PasswordStrength {
    WEAK, MEDIUM, STRONG, VERY_STRONG
}

fun determinePasswordStrength(password: String): PasswordStrength {
    val hasUpperCase = password.any { it.isUpperCase() }
    val hasLowerCase = password.any { it.isLowerCase() }
    val hasNumbers = password.any { it.isDigit() }
    val hasSpecialChar = password.any { it.isSpecialChar() }
    val isOfValidLength = password.length >= 9

    var strengthScore = 0
    if (hasUpperCase) strengthScore++
    if (hasLowerCase) strengthScore++
    if (hasNumbers) strengthScore++
    if (hasSpecialChar) strengthScore++
    if (isOfValidLength) strengthScore++

    return when (strengthScore) {
        0 -> PasswordStrength.WEAK
        1 -> PasswordStrength.WEAK
        2 -> PasswordStrength.MEDIUM
        3 -> PasswordStrength.STRONG
        4 -> PasswordStrength.STRONG
        else -> PasswordStrength.VERY_STRONG
    }
}



@Preview(showBackground = true, device = "id:pixel_6a", showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = NavController(LocalContext.current))
}
