[app](../../index.md) / [com.example.chaosruler.msa_manager.abstraction_classes](../index.md) / [table_dataclass](.)

# table_dataclass

`interface table_dataclass`

### Functions

| [copy](copy.md) | `abstract fun copy(): table_dataclass`<br>a copy constructor of the object |
| [toString](to-string.md) | `abstract fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>to identify the object, we must reimplement the toString funcion with identifying marks on the strings |

### Inheritors

| [big_table_data](../../com.example.chaosruler.msa_manager.object_types/big_table_data/index.md) | `class big_table_data : table_dataclass` |
| [inventory_data](../../com.example.chaosruler.msa_manager.object_types/inventory_data/index.md) | `class inventory_data : table_dataclass` |
| [opr_data](../../com.example.chaosruler.msa_manager.object_types/opr_data/index.md) | `class opr_data : table_dataclass` |
| [project_data](../../com.example.chaosruler.msa_manager.object_types/project_data/index.md) | `class project_data : table_dataclass` |
| [vendor_data](../../com.example.chaosruler.msa_manager.object_types/vendor_data/index.md) | `class vendor_data : table_dataclass`<br>a dataclass for vendor data |

