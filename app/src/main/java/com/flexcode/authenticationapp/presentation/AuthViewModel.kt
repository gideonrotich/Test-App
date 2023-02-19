package com.flexcode.authenticationapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexcode.authenticationapp.domain.use_case.LoginUseCase
import com.flexcode.authenticationapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
): ViewModel() {
    private val _loginResult = MutableLiveData<Resource<Boolean>>()
    val loginResult: LiveData<Resource<Boolean>> = _loginResult




    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Resource.Loading()
            val result = loginUseCase.execute(username, password)
            _loginResult.value = result
        }
    }

}