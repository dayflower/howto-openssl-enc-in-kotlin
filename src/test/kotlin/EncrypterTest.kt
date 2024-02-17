import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.example.decryptFromBase64ToString
import org.example.encryptToBase64

class EncrypterTest : StringSpec() {
    init {

        "encryptToBase64(AES/CBC/PKCS5Padding) should encrypt a string to a base64 string" {
            val encrypted = encryptToBase64(
                "my-secret",
                "Hello, world",
                Charsets.UTF_8,
                "AES/CBC/PKCS5Padding",
                256,
                4649
            )

            decryptFromBase64ToString(
                "my-secret",
                encrypted,
                Charsets.UTF_8,
                "AES/CBC/PKCS5Padding",
                256,
                4649
            ) shouldBe "Hello, world"
        }

        "encryptToBase64(AES/CTR/NoPadding) should encrypt a string to a base64 string" {
            val encrypted = encryptToBase64(
                "my-secret",
                "Hello, world",
                Charsets.UTF_8,
                "AES/CTR/NoPadding",
                256,
                4649
            )

            decryptFromBase64ToString(
                "my-secret",
                encrypted,
                Charsets.UTF_8,
                "AES/CTR/NoPadding",
                256,
                4649
            ) shouldBe "Hello, world"
        }

    }
}
