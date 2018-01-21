[app](../../index.md) / [com.example.chaosruler.msa_manager.services](../index.md) / [encryption](.)

# encryption

`object encryption`

### Functions

| [decrypt](decrypt.md) | `fun decrypt(a: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)<br>This function decrypts a byteArray Must call generate_key() before this function |
| [encrypt](encrypt.md) | `fun encrypt(a: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)<br>This function encrypts a byteArray and returns an encrypted form of that byteArray Must call generate_key() before this function |
| [generate_key](generate_key.md) | `fun generate_key(context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>this function is responsible for generatoin an AES key from the keystore per encryption/decryption, and refreshing the current one |

