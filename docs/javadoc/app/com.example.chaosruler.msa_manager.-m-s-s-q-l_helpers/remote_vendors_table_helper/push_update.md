[app](../../index.md) / [com.example.chaosruler.msa_manager.MSSQL_helpers](../index.md) / [remote_vendors_table_helper](index.md) / [push_update](.)

# push_update

`fun push_update(vendor_data: `[`vendor_data`](../../com.example.chaosruler.msa_manager.object_types/vendor_data/index.md)`, map: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

a function to take the object dataclass and initate and take the identifying traits and he updated traits and create an update query matching that

### Parameters

`context` - a baseContext to work with

`map` - a map of the variables we want to identify the object with

`vendor_data` - the data-object we want to update and take the data from

**Author**
Chaosruler972

`protected fun push_update(obj: `[`table_dataclass`](../../com.example.chaosruler.msa_manager.abstraction_classes/table_dataclass/index.md)`, map: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Overrides [remote_helper.push_update](../../com.example.chaosruler.msa_manager.abstraction_classes/remote_helper/push_update.md)

a function to take the object dataclass and initate and take the identifying traits and he updated traits and create an update query matching that

### Parameters

`context` - a baseContext to work with

`map` - a map of the variables we want to identify the object with

`obj` - the data-object we want to update and take the data from

**Author**
Chaosruler972

