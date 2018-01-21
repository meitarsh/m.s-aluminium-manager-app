[app](../../index.md) / [com.example.chaosruler.msa_manager.abstraction_classes](../index.md) / [remote_helper](.)

# remote_helper

`abstract class remote_helper`

an abstract class that fulfiles the requirements for the application to load a remote database (MSSQL)

**Author**
Chaosruler972

**See Also**

[remote_opr_table_helper](#)

### Constructors

| [&lt;init&gt;](-init-.md) | `remote_helper()`<br>an abstract class that fulfiles the requirements for the application to load a remote database (MSSQL) |

### Functions

| [define_type_map](define_type_map.md) | `abstract fun define_type_map(): `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>`<br>inits a vector of typemap (key = name, value = type) for processing |
| [extract_variables](extract_variables.md) | `abstract fun extract_variables(context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Inits the variables on the remoe datbase, with the idea that they are initalized in the strings.xml file init a call to each variable on remote database initated |
| [push_update](push_update.md) | `abstract fun push_update(obj: `[`table_dataclass`](../table_dataclass/index.md)`, map: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>push an update to the database |

