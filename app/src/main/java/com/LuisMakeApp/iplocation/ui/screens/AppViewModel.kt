package com.LuisMakeApp.iplocation.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LuisMakeApp.iplocation.model.IpInfo
import com.LuisMakeApp.iplocation.model.UiState
import com.LuisMakeApp.iplocation.network.RestApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    //call retrofit service and update data uiState
    fun getInfoIp(ip: String){
        viewModelScope.launch {
            try {
                val infoIp = RestApi.retrofitService.getIpInfo(ip)
                _uiState.update {
                    it.copy(infoIp = infoIp)
                }
            }catch (e: Exception){
                Log.d("Exception", e.toString())
            }

        }
    }

}