package com.mitteloupe.whoami.home.presentation.model

sealed interface HomeViewState {
    object Loading : HomeViewState

    data class Connected(
        val ipAddress: String,
        val city: String?,
        val region: String?,
        val countryCode: String?,
        val geolocation: String?,
        val internetServiceProviderName: String?,
        val postCode: String?,
        val timeZone: String?
    ) : HomeViewState

    object Disconnected : HomeViewState

    data class Error(val error: ErrorPresentationModel) : HomeViewState
}
