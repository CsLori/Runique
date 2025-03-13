package com.cslori.auth.data.repository

import com.cslori.auth.data.RegisterRequest
import com.cslori.auth.domain.repository.AuthRepository
import com.cslori.core.data.networking.post
import com.cslori.core.domain.util.DataError
import com.cslori.core.domain.util.EmptyResult
import io.ktor.client.HttpClient
import io.ktor.client.request.post

class AuthRepositoryImpl(
    private val httpClient: HttpClient
) : AuthRepository {
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