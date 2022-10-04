package im.chimerical.sendonwhatsaap.presentation.ccp.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import im.chimerical.sendonwhatsaap.presentation.ccp.data.CountryData
import im.chimerical.sendonwhatsaap.presentation.ccp.data.utils.getNumberHint
import im.chimerical.sendonwhatsaap.presentation.ccp.transformation.PhoneNumberTransformation
import im.chimerical.sendonwhatsaap.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RoundedPicker(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp),
    color: Color = MaterialTheme.colors.background,
    showCountryCode: Boolean = true,
    defaultCountry: CountryData,
    pickedCountry: (CountryData) -> Unit,
    focusedBorderColor: Color = MaterialTheme.colors.primary,
    unfocusedBorderColor: Color = MaterialTheme.colors.onSecondary,
    cursorColor: Color = MaterialTheme.colors.primary,
    dialogAppBarColor: Color = MaterialTheme.colors.primary,
    dialogAppBarTextColor: Color = Color.White,
    error: Boolean,
    rowPadding: Modifier = modifier.padding(vertical = 16.dp, horizontal = 3.dp),
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)
    val keyboardController = LocalSoftwareKeyboardController.current


    Surface(color = color) {
        Column(modifier = rowPadding) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValueState = it
                        if (value != it.text) {
                            onValueChange(it.text)
                        }
                    },
                    modifier = modifier,
                    shape = shape,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (!error) Color.Red else focusedBorderColor,
                        unfocusedBorderColor = if (!error) Color.Red else unfocusedBorderColor,
                        cursorColor = cursorColor
                    ),
                    label = {
                        Text(
                            "Phone Number",
                            color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        )
                    },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = getNumberHint(defaultCountry.countryCode))) },
                    visualTransformation = PhoneNumberTransformation(defaultCountry.countryCode.uppercase()),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword,
                        autoCorrect = true,
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    trailingIcon = {
                        if (!error)
                            Icon(
                                imageVector = Icons.Filled.Warning, contentDescription = "Error",
                                tint = MaterialTheme.colors.error
                            )
                    },
                    leadingIcon = {
                        Row {
                            Column {
                                val dialog = CodePicker()
                                dialog.CodeDialog(
                                    pickedCountry = pickedCountry,
                                    defaultSelectedCountry = defaultCountry,
                                    dialogAppBarColor = dialogAppBarColor,
                                    dialogAppBarTextColor = dialogAppBarTextColor,
                                    showCountryCode = showCountryCode
                                )
                            }
                        }
                    }
                )
            }
            if (!error)
                Text(
                    text = stringResource(id = R.string.invalid_number),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.8.dp)
                )
        }
    }
}