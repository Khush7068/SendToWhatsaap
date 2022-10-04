package im.chimerical.sendonwhatsaap.presentation.ccp.utils

import android.content.Context
import im.chimerical.sendonwhatsaap.presentation.ccp.data.CountryData
import im.chimerical.sendonwhatsaap.presentation.ccp.data.utils.getCountryName

fun List<CountryData>.searchCountry(key: String, context: Context): MutableList<CountryData> {
    val tempList = mutableListOf<CountryData>()
    this.forEach {
        if (context.resources.getString(getCountryName(it.countryCode)).lowercase().contains(key.lowercase())) {
            tempList.add(it)
        }
    }
    return tempList
}