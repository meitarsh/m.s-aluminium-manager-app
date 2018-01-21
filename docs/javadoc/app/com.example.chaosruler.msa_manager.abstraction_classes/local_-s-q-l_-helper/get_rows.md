[app](../../index.md) / [com.example.chaosruler.msa_manager.abstraction_classes](../index.md) / [local_SQL_Helper](index.md) / [get_rows](.)

# get_rows

`fun get_rows(map: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): Vector<`[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>`

SELECT * FROM TABLE_NAME WHERE XXX query to kotlin code
function works with multi threadding but is blocked until results is generated

### Parameters

`map` - filter by which values? key = name, value = data

### Exceptions

`SQLiteException` -

**Author**
Chaosruler972

**Return**
a vector that each element represents a row and each hashtable represents all the columns (key = column name, value = data)

