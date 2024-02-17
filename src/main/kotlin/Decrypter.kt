package org.example

import java.nio.charset.Charset
import java.util.*
import javax.crypto.Cipher

fun decrypt(
    password: String,
    encrypted: ByteArray,
    transformation: String = DEFAULT_TRANSFORMATION,
    keyLength: Int = 256,
    iterationCount: Int = 10000
): ByteArray {
    require(encrypted.size > SALTED_SIGNATURE_LENGTH + SALT_LENGTH) { "Invalid encrypted size" }

    val signature = encrypted.copyOfRange(0, SALTED_SIGNATURE_LENGTH)
    check(signature.contentEquals(SALTED_SIGNATURE)) { "Invalid signature" }

    val salt = encrypted.copyOfRange(SALTED_SIGNATURE_LENGTH, SALTED_SIGNATURE_LENGTH + SALT_LENGTH)

    val cipher = generateCipher(Cipher.DECRYPT_MODE, password, salt, transformation, keyLength, iterationCount)

    return checkNotNull(cipher.doFinal(encrypted.copyOfRange(SALTED_SIGNATURE_LENGTH + SALT_LENGTH, encrypted.size)))
}

fun decryptToString(
    password: String,
    encrypted: ByteArray,
    resultEncoding: Charset = Charsets.UTF_8,
    transformation: String = DEFAULT_TRANSFORMATION,
    keyLength: Int = 256,
    iterationCount: Int = 10000
): String {
    return decrypt(
        password,
        encrypted,
        transformation,
        keyLength,
        iterationCount
    ).toString(resultEncoding)
}

fun decryptFromBase64(
    password: String,
    encrypted: String,
    transformation: String = DEFAULT_TRANSFORMATION,
    keyLength: Int = 256,
    iterationCount: Int = 10000
): ByteArray {
    return decrypt(
        password,
        Base64.getDecoder().decode(encrypted),
        transformation,
        keyLength,
        iterationCount
    )
}

fun decryptFromBase64ToString(
    password: String,
    encrypted: String,
    resultEncoding: Charset = Charsets.UTF_8,
    transformation: String = DEFAULT_TRANSFORMATION,
    keyLength: Int = 256,
    iterationCount: Int = 10000
): String {
    return decryptFromBase64(
        password,
        encrypted,
        transformation,
        keyLength,
        iterationCount
    ).toString(resultEncoding)
}
