[app](../../index.md) / [com.example.chaosruler.msa_manager.abstraction_classes](../index.md) / [local_SQL_Helper](index.md) / [remove_from_db](.)

# remove_from_db

`protected fun remove_from_db(where_clause: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, equal_to: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Removes an entry from the database that holds true to the arguement where_clause equals (at least one of the equal_to strings)

### Parameters

`where_clause` - the field name to compare to on each row (if that field holds true, it is deleted)

`equal_to` - what to compare the field name to (as in, what data it should be equal to)

**Author**
Chaosruler972

**Return**
if data removed was successfull

`protected fun remove_from_db(where_clause: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, equal_to: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Removes from database with multiple where clauses requirements

### Parameters

`where_clause` - a vector that holds the multiple amounts of fields that we are going to compare by name

`equal_to` - the fields value to compare to

**Author**
Chaosruler972

**Return**
if the data removal was successfull

