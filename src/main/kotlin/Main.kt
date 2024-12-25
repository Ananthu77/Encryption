fun main() {

    val encodedString = CryptLibrary().encryptPlainText("Sample string for encryption!")
    println("Encrypted String: $encodedString")

    val decodedString = CryptLibrary().decryptCipherText(encodedString)
    println("Decrypted String: $decodedString")

}