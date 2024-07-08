package app.workmod.workmod_kmm.android.settings

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.workmod.workmod_kmm.MainViewModel
import com.keepqueue.workmode.auth.view.AuthActivity
import org.koin.androidx.compose.koinViewModel

@Composable
fun Settings(navController: NavHostController,
             showSnack: (message: String) -> Unit,
             viewModel: MainViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val signOutResult by viewModel.signOutResult.collectAsState()
    val userName by viewModel.getUserName().collectAsState(initial = "")

    if (signOutResult.success) {
        (context as Activity).finish()
        context.startActivity(Intent(context, AuthActivity::class.java))
    } else if (signOutResult.error.isNotEmpty()) {
        showSnack(signOutResult.error)
    }

    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(text = userName,
                    modifier = Modifier.align(Alignment.CenterHorizontally))
                Text(text = "Settings",
                    modifier = Modifier.align(Alignment.CenterHorizontally))
                Button(onClick = {
                    viewModel.signOut()
                }) {
                    Text("Sign out")
                }
            }
            if (signOutResult.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp).align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}