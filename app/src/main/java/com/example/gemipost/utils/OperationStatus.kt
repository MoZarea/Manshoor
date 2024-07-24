package com.example.gemipost.utils

sealed class Status {
    data object IDLE : Status()
    data object LOADING : Status()
    data object SUCCESS : Status()
    data class ERROR(val message: String) : Status()
}