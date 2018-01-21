[app](../../index.md) / [com.example.chaosruler.msa_manager.abstraction_classes](../index.md) / [local_SQL_Helper](index.md) / [update_data](.)

# update_data

`protected fun update_data(where_clause: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, equal_to: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, update_to: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

a function to update a row in the database with data

### Parameters

`where_clause` - which data should I update? field should be targetted by WHERE clause

`equal_to` - the data to compare to the targeted the row we should update...

`update_to` - what should we update it to, key = value name, value = value data

### Exceptions

`SQLiteException` -

**Author**
Chaosruler972

**Return**
if data update was succesfull

`protected fun update_data(where_clause: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, equal_to: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, update_to: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Similar to the reguler update_data function, only this one gets multiple parameters to find the specified row to update

### Parameters

`where_clause` - which data should I update? field should be targetted by WHERE clause

`equal_to` - the data to compare to the targeted the row we should update...

`update_to` - what should we update it to, key = value name, value = value data

### Exceptions

`SQLiteException` -

**Author**
Chaosruler972

**Return**
if data update was succesfull

