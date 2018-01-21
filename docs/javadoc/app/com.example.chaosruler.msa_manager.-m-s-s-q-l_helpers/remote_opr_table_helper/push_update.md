[app](../../index.md) / [com.example.chaosruler.msa_manager.MSSQL_helpers](../index.md) / [remote_opr_table_helper](index.md) / [push_update](.)

# push_update

`protected fun push_update(obj: `[`table_dataclass`](../../com.example.chaosruler.msa_manager.abstraction_classes/table_dataclass/index.md)`, map: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Overrides [remote_helper.push_update](../../com.example.chaosruler.msa_manager.abstraction_classes/remote_helper/push_update.md)

pushes an update to the database on remote call

### Parameters

`obj` - a representation of the object data class we want to push an update to

`context` - a baseContext to work with

`map` - a map of the identifying traits of what we should update on the object in the remote database

**Author**
Chaosruler972

`fun push_update(opr: `[`opr_data`](../../com.example.chaosruler.msa_manager.object_types/opr_data/index.md)`, map: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

a function to take the object dataclass and initate and take the identifying traits and he updated traits and create an update query matching that

### Parameters

`context` - a baseContext to work with

`map` - a map of the variables we want to identify the object with

`opr` - the data-object we want to update and take the data from

**Author**
Chaosruler972

