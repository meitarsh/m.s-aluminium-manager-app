[app](../../index.md) / [com.example.chaosruler.msa_manager.abstraction_classes](../index.md) / [local_SQL_Helper](index.md) / [add_data](.)

# add_data

`protected fun add_data(variables: Vector<`[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Inserts LOADS of data to the database

### Parameters

`variables` - the data we want to add, field name will be on the key, field value is on the value, all the rows are seperated in the vector as different elements

**Author**
Chaosruler972

**Return**
if at least one of the items was successfull

