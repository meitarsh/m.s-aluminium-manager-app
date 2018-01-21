[app](../../index.md) / [com.example.chaosruler.msa_manager.activies](../index.md) / [LoginActivity](.)

# LoginActivity

`class LoginActivity : AppCompatActivity, LoaderCallbacks<Cursor>`

A login screen that offers login via email/password.

### Types

| [ProfileQuery](-profile-query/index.md) | `object ProfileQuery` |
| [UserLoginTask](-user-login-task/index.md) | `inner class UserLoginTask : AsyncTask<Void, Void, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>`<br>Represents an asynchronous login/registration task used to authenticate the user. |

### Constructors

| [&lt;init&gt;](-init-.md) | `LoginActivity()`<br>A login screen that offers login via email/password. |

### Functions

| [onActivityResult](on-activity-result.md) | `fun onActivityResult(requestCode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, resultCode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, data: Intent?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreate](on-create.md) | `fun onCreate(savedInstanceState: Bundle?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreateLoader](on-create-loader.md) | `fun onCreateLoader(i: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, bundle: Bundle?): Loader<Cursor>` |
| [onCreateOptionsMenu](on-create-options-menu.md) | `fun onCreateOptionsMenu(menu: Menu): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onLoadFinished](on-load-finished.md) | `fun onLoadFinished(cursorLoader: Loader<Cursor>, cursor: Cursor): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onLoaderReset](on-loader-reset.md) | `fun onLoaderReset(cursorLoader: Loader<Cursor>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onOptionsItemSelected](on-options-item-selected.md) | `fun onOptionsItemSelected(item: MenuItem): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onRequestPermissionsResult](on-request-permissions-result.md) | `fun onRequestPermissionsResult(requestCode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, permissions: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, grantResults: `[`IntArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Callback received when a permissions request has been completed. |
| [onResume](on-resume.md) | `fun onResume(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

