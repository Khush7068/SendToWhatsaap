package im.chimerical.sendonwhatsaap.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import im.chimerical.sendonwhatsaap.data.sendMessageOnWhatsapp

class MainViewModel : ViewModel() {

    var phone by mutableStateOf("")
        private set

    var message by mutableStateOf("")

    fun onPhoneChange(phone: String) {
        this.phone = phone
    }

    fun onMessageChange(message: String) {
        this.message = message
    }

    fun submit(context: Context,phone: String){
        context.startActivity(sendMessageOnWhatsapp(phone,message))
    }
}