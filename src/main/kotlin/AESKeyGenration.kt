import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * Function for generating random AES key and IV of 128 bits
 * */
@OptIn(ExperimentalEncodingApi::class)
@Throws(NoSuchAlgorithmException::class)
fun generateKeyAndIV(): Pair<String, String> {
    val keyGenerator = KeyGenerator.getInstance("AES")
    keyGenerator.init(128)  // AES-128 (16 bytes key)
    val secretKey: SecretKey = keyGenerator.generateKey()

    // Generate a random IV (16 bytes)
    val iv = ByteArray(16)
    SecureRandom().nextBytes(iv)

    val keyBase64 = Base64.Default.encode(secretKey.encoded)
    val ivBase64 = Base64.Default.encode(iv)

    return Pair(keyBase64, ivBase64)
}