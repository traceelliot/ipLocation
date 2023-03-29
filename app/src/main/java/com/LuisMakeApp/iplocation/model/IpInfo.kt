package com.LuisMakeApp.iplocation.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class IpInfo(
     val ip: String,
     @SerialName("continent_name")
     val continentName: String,
     @SerialName("country_name")
     val countryName: String,
     val city: String,
     @SerialName("zipcode")
     val zipCode: String,
     val isp: String
)
