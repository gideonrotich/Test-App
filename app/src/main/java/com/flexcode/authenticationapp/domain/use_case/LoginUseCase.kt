package com.flexcode.authenticationapp.domain.use_case

import com.flexcode.authenticationapp.data.remote.request.AuthRequest
import com.flexcode.authenticationapp.domain.model.AuthResult
import com.flexcode.authenticationapp.domain.repository.AuthRepository
import com.flexcode.authenticationapp.domain.repository.FPLrepository
import com.flexcode.authenticationapp.util.Resource

class LoginUseCase(
    private val repository: FPLrepository
) {
    suspend fun execute(username: String, password: String): Resource<Boolean> {
        return repository.login(username, password)
    }
}