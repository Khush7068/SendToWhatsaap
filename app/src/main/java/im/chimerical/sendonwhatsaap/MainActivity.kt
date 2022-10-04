package im.chimerical.sendonwhatsaap

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import im.chimerical.sendonwhatsaap.presentation.screens.MainScreen
import im.chimerical.sendonwhatsaap.presentation.screens.toLog
import im.chimerical.sendonwhatsaap.presentation.ui.theme.SendOnWhatsaapTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContent {
                SendOnWhatsaapTheme {
                    Surface(modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background) {

                        MainScreen()

                    }
                }
            }
        } catch (e: Exception) {
            "exception from main activity".toLog()
        }
    }

    
}