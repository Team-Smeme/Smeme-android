package com.sopt.smeme

class SmemeException (val status: Int, override val message: String): RuntimeException() {
}