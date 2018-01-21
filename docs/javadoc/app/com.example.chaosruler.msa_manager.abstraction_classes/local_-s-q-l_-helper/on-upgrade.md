[app](../../index.md) / [com.example.chaosruler.msa_manager.abstraction_classes](../index.md) / [local_SQL_Helper](index.md) / [onUpgrade](.)

# onUpgrade

`open fun onUpgrade(db: SQLiteDatabase, oldVersion: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, newVersion: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

if we got a newer version in our android device than the installed version of our current database
we will delete all entries of our current database, drop it and its scheme and recreate a new one (without the old data)

### Parameters

`db` - an instance of the database to work with

`newVersion` - the new version number we are installing now (in integers)

`oldVersion` - the old version number that is installed on the device

**Author**
Chaosruler972

