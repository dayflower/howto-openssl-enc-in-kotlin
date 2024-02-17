package org.example

import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

const val DEFAULT_TRANSFORMATION = "AES/CBC/PKCS5Padding"

val SALTED_SIGNATURE = "Salted__".toByteArray(Charsets.UTF_8)

val SALTED_SIGNATURE_LENGTH = SALTED_SIGNATURE.size
const val IV_LENGTH = 16
const val SALT_LENGTH = 8

fun generateCipher(
    opmode: Int,
    password: String,
    salt: ByteArray,
    transformation: String,
    keyLength: Int,
    iterationCount: Int
): Cipher {
    val keySpec = PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength + 8 * IV_LENGTH /* IV */)
    val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
    val secretKeyAndIvEntity = secretKeyFactory.generateSecret(keySpec)

    val secretKeyAndIv = secretKeyAndIvEntity.encoded
    check(secretKeyAndIv.size == keyLength / 8 + IV_LENGTH) { "Invalid key length" }
    val secretKey = secretKeyAndIv.copyOfRange(0, keyLength / 8)
    val iv = secretKeyAndIv.copyOfRange(keyLength / 8, secretKeyAndIv.size)

    val cipher = Cipher.getInstance(transformation)
    val algorithm = cipher.algorithm.split("/")[0]
    cipher.init(opmode, SecretKeySpec(secretKey, algorithm), IvParameterSpec(iv))

    return cipher
}
