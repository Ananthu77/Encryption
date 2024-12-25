import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * AES encryption and decryption class that uses CBC mode and PKCS5 padding,
 * Uses 16 byte size key and iv
 * */
class CryptLibrary @Throws(
    NoSuchAlgorithmException::class, NoSuchPaddingException::class
) constructor() {

    /**
     * Encrypts plain text and converts it into BASE64, then returns.
     */
    @OptIn(ExperimentalEncodingApi::class)
    @Throws(Exception::class)
    fun encryptPlainText(plainText: String): String = Base64.Default.encode(
        encryptDecrypt(
            plainText, EncryptMode.ENCRYPT
        )
    )

    /**
     * Decrypts Base64 encoded cipher text and returns the plain text.
     */
    @Throws(Exception::class)
    fun decryptCipherText(cipherText: String): String = String(
        encryptDecrypt(
            cipherText, EncryptMode.DECRYPT
        )
    )

    /**
     * Encrypts or decrypts input text using the provided encryption key and initialization vector.
     *
     * @param mode          Specify the mode encryption / decryption
     */
    @OptIn(ExperimentalEncodingApi::class)
    @Throws(
        InvalidKeyException::class,
        InvalidAlgorithmParameterException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    private fun encryptDecrypt(
        inputText: String, mode: EncryptMode
    ): ByteArray {

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

        val key = ByteArray(16)
        val iv = ByteArray(16)

        // Copy encryptionKey and initVector to key and iv arrays
        ENCRYPT_KEY.toByteArray().copyInto(key, 0, 0, key.size.coerceAtMost(ENCRYPT_KEY.length))
        INIT_VECTOR.toByteArray().copyInto(iv, 0, 0, iv.size.coerceAtMost(INIT_VECTOR.length))

        /**
         * Initialize key and iv specifications
         */
        val keySpec = SecretKeySpec(key, "AES")
        val ivSpec = IvParameterSpec(iv)

        return if (mode == EncryptMode.ENCRYPT) {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            cipher.doFinal(inputText.toByteArray(Charsets.UTF_8))
        } else {
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val decodedValue = Base64.Default.decode(inputText)
            cipher.doFinal(decodedValue)
        }
    }

    private enum class EncryptMode {
        ENCRYPT, DECRYPT
    }


    companion object {
        private const val INIT_VECTOR = "6D4SIHa5h2ZtSLHNXnNJog=="
        private const val ENCRYPT_KEY = "TV7oPG5kAOpp+3KO3/WffQ=="
    }



}