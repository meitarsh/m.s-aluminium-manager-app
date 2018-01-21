[app](../../index.md) / [com.example.chaosruler.msa_manager.services](../index.md) / [encryption](index.md) / [generate_key](.)

# generate_key

`fun generate_key(context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

this function is responsible for generatoin an AES key from the keystore per encryption/decryption, and refreshing the current one

### Parameters

`context` - a base Context, must not be null, for keyStore access