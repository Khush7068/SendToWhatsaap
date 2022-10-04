package im.chimerical.sendonwhatsaap.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import im.chimerical.sendonwhatsaap.R

fun sendMessageOnWhatsapp(phoneNumber: String, message: String, business: Boolean = false): Intent {

    val total = "https://api.whatsapp.com/send?phone=" +
            phoneNumber.replace("+", "") + "&text=${message}"

    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(total)
        `package` = if (business) "com.whatsapp.w4b" else "com.whatsapp"
    }
    return intent
}

fun Intent.launchIfResolved(context: Context) {
    if (resolveActivity(context.packageManager) == null) context.createToast(R.string.not_installed)
    else context.startActivity(this)
}

//extension function on Context to create a toast.
fun Context.createToast(@StringRes messageResource: Int) {
    Toast.makeText(this, messageResource, Toast.LENGTH_SHORT).show()
}
