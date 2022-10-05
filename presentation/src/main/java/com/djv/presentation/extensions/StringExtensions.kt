package com.djv.presentation.extensions

fun String.encodeString(): String =
    this.trim().replace(" ", "+")