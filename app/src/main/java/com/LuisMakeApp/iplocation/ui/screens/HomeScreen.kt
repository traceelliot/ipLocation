package com.LuisMakeApp.iplocation.ui.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.LuisMakeApp.iplocation.R
import com.LuisMakeApp.iplocation.model.IpInfo


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun homeScreen(
    infoIp: IpInfo?,
    getInfo: (String) -> Unit
){

    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var loadingImage by remember { mutableStateOf(false) }

    


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logotype",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .border(10.dp, MaterialTheme.colors.primaryVariant, MaterialTheme.shapes.large)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Ip Location", style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = text,
                onValueChange = {text = it},
                placeholder = {Text(text = "Input Ip")},
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        getInfo(text)
                        loadingImage = true
                    }
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                keyboardController?.hide()
                getInfo(text)
                loadingImage = true
            }) {
                Text(text = "Get Info")
            }
            if(loadingImage){
                Image(
                    painter = painterResource(id = R.drawable.loading_img),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp))
            }
            if (infoIp != null) {
                loadingImage = false
                Text(text = "Ip: " + infoIp.ip)
                Text(text = "Isp: " + infoIp.isp)
                Text(text = "City: " + infoIp.city)
                Text(text = "ContinentName: " + infoIp.continentName)
                Text(text = "CountryName: " + infoIp.countryName)
                Text(text = "ZipCode: " + infoIp.zipCode)
            }
        }
    }
}
