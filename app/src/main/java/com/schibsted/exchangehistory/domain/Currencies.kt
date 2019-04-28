package com.schibsted.exchangehistory.domain

sealed class Currencies(val name: String) {
    object USD : Currencies("USD")
    object EUR : Currencies("EUR")
}