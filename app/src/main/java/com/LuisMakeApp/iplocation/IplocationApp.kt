package com.LuisMakeApp.iplocation

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.LuisMakeApp.iplocation.ui.screens.AppViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.LuisMakeApp.iplocation.ui.screens.homeScreen

enum class Screen{
    Home()
}


@Composable
fun ipLocationApp(
    viewModel: AppViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    context: Context
){

    //State info viewModel
    val uiState by viewModel.uiState.collectAsState()


    //Navigation
    NavHost(navController = navController, startDestination = Screen.Home.name){
        composable(route = Screen.Home.name){
            homeScreen(
                uiState.infoIp,
                getInfo = {ip ->
                    if(ip.isNotEmpty()){
                        //Verify if is a ip.
                        var ipSlipt = ip.split(".")
                        if(ipSlipt.size < 5){
                            var goToIpInfo: Boolean = true
                            for(number in ipSlipt){
                                if(!isNumber(number)){
                                    goToIpInfo = false
                                    val toast = Toast.makeText(context, "Invalid IP", Toast.LENGTH_SHORT)
                                    toast.show()
                                    break
                                }
                            }
                            if (goToIpInfo){
                                infoIp(context,viewModel, ip)
                            }
                        }else{
                            val toast = Toast.makeText(context, "Invalid IP", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    }else{
                        val toast = Toast.makeText(context, "The field is empty", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
            )
        }
    }

}

fun isNumber(str: String): Boolean {
    return str.toLongOrNull() != null || str.toDoubleOrNull() != null
}

fun infoIp(context: Context, viewModel: AppViewModel, ip: String){
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val infoNetwork = cm.allNetworkInfo
    var errorFlagToast = true
    for (ni in infoNetwork) {
        if (ni.typeName.equals("WIFI", ignoreCase = true) || ni.typeName.equals("MOBILE", ignoreCase = true)){
            if (ni.isConnected) {
                viewModel.getInfoIp(ip)
                errorFlagToast = false
                break
            }
        }
    }
    if(errorFlagToast){
        val toast = Toast.makeText(context, "Turn on wifi or mobile data", Toast.LENGTH_SHORT)
        toast.show()
    }
}