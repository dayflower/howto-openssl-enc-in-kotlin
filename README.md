# How to - Interoperability between OpenSSL and Kotlin

This repository shows how to interoperate Kotlin with OpenSSL enc command result.

## Encryption

### `encryptToBase64`

`encryptToBase64("secret", "source", transformation="AES/CBC/PKCS5Padding")`

The result of the above function can be decrypted using the following command:

```sh
$ openssl enc -d -aes-256-cbc -base64 -pbkdf2 -pass pass:secret
```

## Decryption

### `decryptFromBase64ToString`

```sh
$ openssl enc -e -aes-256-cbc -base64 -pbkdf2 -pass pass:secret
```

The result of the above command can be decrypted using the following function:

`decryptFromBase64ToString("secret", "U2Fsd...", transformation="AES/CBC/PKCS5Padding")`
