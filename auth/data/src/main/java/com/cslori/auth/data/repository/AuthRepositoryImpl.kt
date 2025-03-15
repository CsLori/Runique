package com.cslori.auth.data.repository

import com.cslori.auth.data.LoginRequest
import com.cslori.auth.data.LoginResponse
import com.cslori.auth.data.RegisterRequest
import com.cslori.auth.domain.repository.AuthRepository
import com.cslori.core.data.networking.post
import com.cslori.core.domain.AuthInfo
import com.cslori.core.domain.SessionStorage
import com.cslori.core.domain.util.DataError
import com.cslori.core.domain.util.EmptyResult
import com.cslori.core.domain.util.Result
import com.cslori.core.domain.util.asEmptyResult
import io.ktor.client.HttpClient
import io.ktor.client.request.post

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
) : AuthRepository {
    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if(result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyResult()
    }

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}