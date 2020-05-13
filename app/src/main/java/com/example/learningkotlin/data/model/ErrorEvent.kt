package com.example.learningkotlin.data.model

enum class ErrorEvent {
    NONE,
    USERNAME_DOES_NOT_EXIST,
    USERNAME_ALREADY_EXISTS,
    FAILURE_TO_ADD_TO_DB,
    WRONG_PASSWORD;
}