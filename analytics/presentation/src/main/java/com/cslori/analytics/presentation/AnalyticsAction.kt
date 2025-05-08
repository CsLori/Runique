package com.cslori.analytics.presentation

sealed interface AnalyticsAction {
    data object OnBackClick: AnalyticsAction
}