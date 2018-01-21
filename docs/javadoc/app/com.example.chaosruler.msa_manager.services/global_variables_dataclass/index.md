[app](../../index.md) / [com.example.chaosruler.msa_manager.services](../index.md) / [global_variables_dataclass](.)

# global_variables_dataclass

`class global_variables_dataclass`

### Constructors

| [&lt;init&gt;](-init-.md) | `global_variables_dataclass()` |

### Companion Object Properties

| [DB_BIG](-d-b_-b-i-g.md) | `var DB_BIG: `[`local_big_table_helper`](../../com.example.chaosruler.msa_manager.-s-q-l-i-t-e_helpers.sync_table/local_big_table_helper/index.md)`?` |
| [DB_INVENTORY](-d-b_-i-n-v-e-n-t-o-r-y.md) | `var DB_INVENTORY: `[`local_inventory_table_helper`](../../com.example.chaosruler.msa_manager.-s-q-l-i-t-e_helpers.sync_table/local_inventory_table_helper/index.md)`?` |
| [DB_OPR](-d-b_-o-p-r.md) | `var DB_OPR: `[`local_OPR_table_helper`](../../com.example.chaosruler.msa_manager.-s-q-l-i-t-e_helpers.sync_table/local_-o-p-r_table_helper/index.md)`?` |
| [DB_VENDOR](-d-b_-v-e-n-d-o-r.md) | `var DB_VENDOR: `[`local_vendor_table_helper`](../../com.example.chaosruler.msa_manager.-s-q-l-i-t-e_helpers.sync_table/local_vendor_table_helper/index.md)`?` |
| [DB_project](-d-b_project.md) | `var DB_project: `[`local_projects_table_helper`](../../com.example.chaosruler.msa_manager.-s-q-l-i-t-e_helpers.sync_table/local_projects_table_helper/index.md)`?` |
| [GUI_MODE](-g-u-i_-m-o-d-e.md) | `var GUI_MODE: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isLocal](is-local.md) | `var isLocal: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [projid](projid.md) | `var projid: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |

### Companion Object Functions

| [get_device_id](get_device_id.md) | `fun get_device_id(con: Context): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [init_dbs](init_dbs.md) | `fun init_dbs(context: Context): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [report_to_Main_Activity_Thread_syncing_is_done](report_to_-main_-activity_-thread_syncing_is_done.md) | `fun report_to_Main_Activity_Thread_syncing_is_done(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [toHex](to-hex.md) | `fun toHex(arg: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [xorWithKey](xor-with-key.md) | `fun xorWithKey(a: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, key: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, flag: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, con: Context): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |

