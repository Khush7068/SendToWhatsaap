package im.chimerical.sendonwhatsaap.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import im.chimerical.sendonwhatsaap.presentation.ccp.component.RoundedPicker
import im.chimerical.sendonwhatsaap.presentation.ccp.data.utils.checkPhoneNumber
import im.chimerical.sendonwhatsaap.presentation.ccp.data.utils.getDefaultLangCode
import im.chimerical.sendonwhatsaap.presentation.ccp.data.utils.getDefaultPhoneCode
import im.chimerical.sendonwhatsaap.presentation.ccp.data.utils.getLibCountries

@Composable
fun RoundedPicker() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var phoneCode by rememberSaveable { mutableStateOf(getDefaultPhoneCode(context)) }
        val phoneNumber = rememberSaveable { mutableStateOf("") }
        var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode(context)) }
        var isValidPhone by remember { mutableStateOf(true) }
        val fullPhoneNumber = "$phoneCode${phoneNumber.value}"

        val checkPhoneNumber = checkPhoneNumber(
            phone = phoneNumber.value,
            fullPhoneNumber = fullPhoneNumber,
            countryCode = defaultLang
        )

        RoundedPicker(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
            pickedCountry = {
                phoneCode = it.countryPhoneCode
                defaultLang = it.countryCode.ifBlank { "in" }
            },
            error = isValidPhone
        )

        OutlinedButton(
            onClick = { isValidPhone = checkPhoneNumber }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .height(
                    50.dp
                )
        ) {
            Text(text = "Verify Phone Number")
        }
    }


}
