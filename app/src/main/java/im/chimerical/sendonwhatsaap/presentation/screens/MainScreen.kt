package im.chimerical.sendonwhatsaap.presentation.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import im.chimerical.sendonwhatsaap.R.drawable
import im.chimerical.sendonwhatsaap.R.string
import im.chimerical.sendonwhatsaap.presentation.MainViewModel
import im.chimerical.sendonwhatsaap.presentation.ccp.component.RoundedPicker
import im.chimerical.sendonwhatsaap.presentation.ccp.data.utils.checkPhoneNumber
import im.chimerical.sendonwhatsaap.presentation.ccp.data.utils.getDefaultLangCode
import im.chimerical.sendonwhatsaap.presentation.ccp.data.utils.getDefaultPhoneCode
import im.chimerical.sendonwhatsaap.presentation.ccp.data.utils.getLibCountries

@Preview(showBackground = true, device = Devices.PIXEL_3, showSystemUi = true)
@Composable
fun MainScreen() {

    val context = LocalContext.current
    val viewModel = viewModel<MainViewModel>()

    Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            HeaderImage()

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(id = string.app_description),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(5.dp))

            AskPhoneNumberOutlinedTextField()
            Spacer(modifier = Modifier.height(5.dp))

            Spacer(modifier = Modifier.height(5.dp))

            AskMessageOutlinedTextField()

            Spacer(modifier = Modifier.height(20.dp))

            val phoneCode by rememberSaveable { mutableStateOf(getDefaultPhoneCode(context)) }
            val fullPhoneNumber = "$phoneCode${viewModel.phone}"

            Button(
                onClick = {
                    (viewModel::submit)(context, fullPhoneNumber)
                },
                enabled = fullPhoneNumber.length >= 12,
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(
                    topStart = 12.dp, bottomEnd = 12.dp
                )
            ) {
                Text(
                    text = "Send Now",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            QuickMessageOutlinedTextField()
        }
    }
}

@Composable
fun HeaderImage() {
    Image(
        painter = painterResource(id = drawable.paper_plane),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .height(130.dp)
            .fillMaxWidth()
            .padding(20.dp),
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun AskPhoneNumberOutlinedTextField() {
    val viewModel = viewModel<MainViewModel>()

    val context = LocalContext.current

    var phoneCode by rememberSaveable { mutableStateOf(getDefaultPhoneCode(context)) }
    val phoneNumber = rememberSaveable { mutableStateOf(viewModel.phone) }
    var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode(context)) }
    val isValidPhone by remember { mutableStateOf(true) }
    val fullPhoneNumber = "$phoneCode${phoneNumber.value}"

    RoundedPicker(
        value = viewModel.phone,
        onValueChange = viewModel::onPhoneChange,
        defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
        pickedCountry = {
            phoneCode = it.countryPhoneCode
            defaultLang = it.countryCode.ifBlank { "in" }
        },
        error = isValidPhone,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth(0.9f),
        rowPadding = Modifier.padding(0.dp),
        dialogAppBarColor = MaterialTheme.colorScheme.primary
    )

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AskMessageOutlinedTextField() {
    val viewModel = viewModel<MainViewModel>()
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = viewModel.message,
        onValueChange = viewModel::onMessageChange,
        shape = RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp),
        label = {
            Text(
                text = "Message",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        placeholder = { Text(text = "Enter message here...") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Send,
            keyboardType = KeyboardType.Text
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.9f),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })

    )
}

@Composable
fun QuickMessageOutlinedTextField() {
    val context = LocalContext.current
    val viewModel = viewModel<MainViewModel>()
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(280.dp)
            .background(color = Color.White),
        elevation = 5.dp,
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp,
            bottomStart = 12.dp,
            bottomEnd = 12.dp
        )
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            text = "Quick Messages",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )

        Column(
            modifier = Modifier
                .fillMaxSize(0.8f)
                .padding(start = 14.dp, top = 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            ClickableText(text = "Hey") {
                (viewModel::onMessageChange)("Hey...")
            }
            ClickableText(text = "How are you ?") {
                (viewModel::onMessageChange)("How are you ?")
            }
            ClickableText(text = "What's app ?") {
                (viewModel::onMessageChange)("What's app ?")
            }
            ClickableText(text = "What's going on ?") {
                (viewModel::onMessageChange)("What's going on ?")
            }
            ClickableText(text = "Call me when you're done") {
                (viewModel::onMessageChange)("Call me when you're done")
            }

        }
    }
}

@Composable
fun ClickableText(text: String, onClick: () -> Unit) {
    TextButton(onClick = {
        onClick()
    }) {
        Text(
            text = text,
            letterSpacing = 1.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

fun Context.toasty(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun String.toToast(context: Context) = Toast.makeText(context, this, Toast.LENGTH_SHORT).show()

fun String.toLog(tag: String = "logger") = Log.e(tag, this)