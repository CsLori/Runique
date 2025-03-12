package com.cslori.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}