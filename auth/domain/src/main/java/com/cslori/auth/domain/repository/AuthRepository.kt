package com.cslori.auth.domain.repository

import com.cslori.core.domain.util.DataError
import com.cslori.core.domain.util.EmptyResult
import com.cslori.core.domain.util.Result

interface AuthRepository {

    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}