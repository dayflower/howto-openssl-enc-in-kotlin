import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.example.decryptFromBase64ToString

class DecrypterTest : StringSpec() {
    init {

        "decryptFromBase64ToString() should decrypt a string created by openssl enc -e -aes-256-cbc -base64" {
            decryptFromBase64ToString(
                "my-secret",
                // generated with: echo -n 'Hello, world' | openssl enc -e -aes-256-cbc -base64 -pbkdf2 -iter 4649 -k 'my-secret'
                "U2FsdGVkX1/ODye7X4DVAcHKmQ/jSPKMg0Tj1yYcOrw=",
                Charsets.UTF_8,
                "AES/CBC/PKCS5Padding",
                256,
                4649
            ) shouldBe "Hello, world"
        }

        "decryptFromBase64ToString() should decrypt a string created by openssl enc -e -aes-256-ctr -base64" {
            decryptFromBase64ToString(
                "my-secret",
                // generated with: echo -n 'Hello, world' | openssl enc -e -aes-256-ctr -base64 -pbkdf2 -iter 4649 -k 'my-secret'
                "U2FsdGVkX1+plGSAmcInnyAWOSPaS2GE/CbqUg==",
                Charsets.UTF_8,
                "AES/CTR/NoPadding",
                256,
                4649
            ) shouldBe "Hello, world"
        }

    }
}
