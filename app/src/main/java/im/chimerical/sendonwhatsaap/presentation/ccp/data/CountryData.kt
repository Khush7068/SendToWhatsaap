package im.chimerical.sendonwhatsaap.presentation.ccp.data

import im.chimerical.sendonwhatsaap.R
import java.util.*

data class CountryData(
    private var cCodes: String,
    val countryPhoneCode: String = "+90",
    val cNames:String = "tr",
    val flagResID: Int = R.drawable.`in`
) {
    val countryCode = cCodes.lowercase(Locale.getDefault())
}
