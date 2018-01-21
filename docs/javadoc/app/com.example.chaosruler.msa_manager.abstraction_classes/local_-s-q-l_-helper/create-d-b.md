[app](../../index.md) / [com.example.chaosruler.msa_manager.abstraction_classes](../index.md) / [local_SQL_Helper](index.md) / [createDB](.)

# createDB

`protected fun createDB(db: SQLiteDatabase, variables: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Create Datbase query handler, in an abstract method

### Parameters

`db` - an instance of the database we are going to work with

`variables` - a hashmap of the variables we are going to initate in our database in format of key: name of the value, value: the type

### Exceptions

`SQLiteException` -

**Author**
Chaosruler972

`protected fun createDB(db: SQLiteDatabase, variables: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, foregin: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, extra: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

an extension of the normal createDB function, it also initates foreign keys (extra is for experts, pushed at the end of the create table query)

### Parameters

`db` - an instance of the database to work with

`variables` - a hashmap of the variables we are going to initate in our database in format of key: name of the value, value: the type

`foregin` - a list of foreign keys with the value name being on the key and the reference in the foreign database on the value

`extra` - an extra added string to push at the end of the Create Table statement

**Author**
Chaosruler972

