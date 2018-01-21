[app](../../index.md) / [com.example.chaosruler.msa_manager.MSSQL_helpers](../index.md) / [remote_opr_table_helper](.)

# remote_opr_table_helper

`class remote_opr_table_helper`

### Constructors

| [&lt;init&gt;](-init-.md) | `remote_opr_table_helper()` |

### Companion Object Properties

| [DATAAREAID](-d-a-t-a-a-r-e-a-i-d.md) | `var DATAAREAID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [DATAAREAID_TYPE](-d-a-t-a-a-r-e-a-i-d_-t-y-p-e.md) | `var DATAAREAID_TYPE: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [DATABASE_NAME](-d-a-t-a-b-a-s-e_-n-a-m-e.md) | `var DATABASE_NAME: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ID](-i-d.md) | `var ID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ID_TYPE](-i-d_-t-y-p-e.md) | `var ID_TYPE: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [NAME](-n-a-m-e.md) | `var NAME: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [NAME_TYPE](-n-a-m-e_-t-y-p-e.md) | `var NAME_TYPE: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [TABLE_NAME](-t-a-b-l-e_-n-a-m-e.md) | `var TABLE_NAME: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Companion Object Functions

| [define_type_map](define_type_map.md) | `fun define_type_map(): `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>`<br>defines a type map as a hashmap that each key is the variable name, and value is is type |
| [extract_variables](extract_variables.md) | `fun extract_variables(context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Inits all the variables with the data from strings.xml holding right for opr database remote metadata |
| [push_update](push_update.md) | `fun push_update(obj: `[`table_dataclass`](../../com.example.chaosruler.msa_manager.abstraction_classes/table_dataclass/index.md)`, map: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>pushes an update to the database on remote call`fun push_update(opr: `[`opr_data`](../../com.example.chaosruler.msa_manager.object_types/opr_data/index.md)`, map: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>a function to take the object dataclass and initate and take the identifying traits and he updated traits and create an update query matching that |

