package org.example

fun main() {
    val encrypted = encryptToBase64(
        "my-secret",
        "Hello, world"
    )

    println(encrypted)
}
