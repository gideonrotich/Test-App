import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.authenticationapp.presentation.AuthViewModel
import com.flexcode.authenticationapp.util.Resource

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val loginResult by viewModel.loginResult.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier.padding(bottom = 8.dp),
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(bottom = 16.dp),
        )
        Button(
            onClick = { viewModel.login(username.value, password.value) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Log in")
        }
        Spacer(modifier = Modifier.height(16.dp))
        when (loginResult) {
            is Resource.Success -> Toast.makeText(
                LocalContext.current,
                "Login successful",
                Toast.LENGTH_SHORT
            ).show()
            is Resource.Error -> Toast.makeText(
                LocalContext.current,
                "Login failed",
                Toast.LENGTH_SHORT
            ).show()
            else -> {}
        }
    }
}