package org.example

import java.nio.charset.Charset
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher

fun encrypt(
    password: String,
    plain: ByteArray,
    transformation: String = DEFAULT_TRANSFORMATION,
    keyLength: Int = 256,
    iterationCount: Int = 10000
): ByteArray {
    val salt = SecureRandom().generateSeed(SALT_LENGTH)

    val cipher = generateCipher(Cipher.ENCRYPT_MODE, password, salt, transformation, keyLength, iterationCount)

    val encrypted = cipher.doFinal(plain)

    return SALTED_SIGNATURE + salt + encrypted
}

fun encrypt(
    password: String,
    plain: String,
    plainEncoding: Charset = Charsets.UTF_8,
    transformation: String = DEFAULT_TRANSFORMATION,
    keyLength: Int = 256,
    iterationCount: Int = 10000
): ByteArray {
    return encrypt(password, plain.toByteArray(plainEncoding), transformation, keyLength, iterationCount)
}

fun encryptToBase64(
    password: String,
    plain: ByteArray,
    transformation: String = DEFAULT_TRANSFORMATION,
    keyLength: Int = 256,
    iterationCount: Int = 10000,
): String {
    val encrypted = encrypt(password, plain, transformation, keyLength, iterationCount)
    return Base64.getEncoder().encodeToString(encrypted)
}

fun encryptToBase64(
    password: String,
    plain: String,
    plainEncoding: Charset = Charsets.UTF_8,
    transformation: String = DEFAULT_TRANSFORMATION,
    keyLength: Int = 256,
    iterationCount: Int = 10000,
): String {
    return encryptToBase64(password, plain.toByteArray(plainEncoding), transformation, keyLength, iterationCount)
}
