[app](../../index.md) / [com.example.chaosruler.msa_manager.abstraction_classes](../index.md) / [remote_helper](index.md) / [push_update](.)

# push_update

`protected abstract fun push_update(obj: `[`table_dataclass`](../table_dataclass/index.md)`, map: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

push an update to the database

### Parameters

`context` - baseContext to work with, must be valid

`map` - the input data we wan to update, wih variable name on the key, and data on the value

`obj` - a table dataclass metaobject to work with, in an abstract form, please see object_types package for more info

**Author**
Chaosruler972

