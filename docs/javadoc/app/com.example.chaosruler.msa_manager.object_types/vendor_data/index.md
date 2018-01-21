[app](../../index.md) / [com.example.chaosruler.msa_manager.object_types](../index.md) / [vendor_data](.)

# vendor_data

`class vendor_data : `[`table_dataclass`](../../com.example.chaosruler.msa_manager.abstraction_classes/table_dataclass/index.md)

a dataclass for vendor data

### Parameters

`DATAAREAID` - for filtering during testing

`ID` - the id of the vendor

`NAME` - the name of the vendor

`USERNAME` - the username that required that data

**Author**
Chaosruler972

### Constructors

| [&lt;init&gt;](-init-.md) | `vendor_data(ID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?, NAME: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?, DATAAREAID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?, USERNAME: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?)`<br>a dataclass for vendor data |

### Functions

| [copy](copy.md) | `fun copy(): vendor_data`<br>Copy constructor for this object precisely |
| [get_DATAREAID](get_-d-a-t-a-r-e-a-i-d.md) | `fun get_DATAREAID(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>gets the daaraaeid (testing phase only) |
| [get_USERNAME](get_-u-s-e-r-n-a-m-e.md) | `fun get_USERNAME(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>gets the username that synced that data |
| [get_accountname](get_accountname.md) | `fun get_accountname(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>gets the account name |
| [get_accountnum](get_accountnum.md) | `fun get_accountnum(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>gets the vendor account ID |
| [set_accountname](set_accountname.md) | `fun set_accountname(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>sets a new account name |
| [set_accountnum](set_accountnum.md) | `fun set_accountnum(accountnum: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>sets a new ID (shouldn't be used) |
| [set_dataareaid](set_dataareaid.md) | `fun set_dataareaid(new_dataareaid: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>sets a dataaraeid (testing phase only) |
| [set_username](set_username.md) | `fun set_username(new_username: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>resetns a new username that synced that data |
| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Identifies this data with the ID |

