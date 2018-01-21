[app](../../index.md) / [com.example.chaosruler.msa_manager.activies.settings_activity](../index.md) / [AppCompatPreferenceActivity](.)

# AppCompatPreferenceActivity

`abstract class AppCompatPreferenceActivity : PreferenceActivity`

A [android.preference.PreferenceActivity](#) which implements and proxies the necessary calls
to be used with AppCompat.

### Constructors

| [&lt;init&gt;](-init-.md) | `AppCompatPreferenceActivity()`<br>A [android.preference.PreferenceActivity](#) which implements and proxies the necessary calls to be used with AppCompat. |

### Properties

| [supportActionBar](support-action-bar.md) | `val supportActionBar: ActionBar?` |

### Functions

| [addContentView](add-content-view.md) | `open fun addContentView(view: View, params: LayoutParams): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getMenuInflater](get-menu-inflater.md) | `open fun getMenuInflater(): MenuInflater` |
| [invalidateOptionsMenu](invalidate-options-menu.md) | `open fun invalidateOptionsMenu(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onConfigurationChanged](on-configuration-changed.md) | `open fun onConfigurationChanged(newConfig: Configuration): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreate](on-create.md) | `open fun onCreate(savedInstanceState: Bundle?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDestroy](on-destroy.md) | `open fun onDestroy(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onPostCreate](on-post-create.md) | `open fun onPostCreate(savedInstanceState: Bundle?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onPostResume](on-post-resume.md) | `open fun onPostResume(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStop](on-stop.md) | `open fun onStop(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onTitleChanged](on-title-changed.md) | `open fun onTitleChanged(title: `[`CharSequence`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html)`, color: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setContentView](set-content-view.md) | `open fun setContentView(layoutResID: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`open fun setContentView(view: View): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`open fun setContentView(view: View, params: LayoutParams): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setSupportActionBar](set-support-action-bar.md) | `fun setSupportActionBar(toolbar: Toolbar?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inheritors

| [SettingsActivity](../-settings-activity/index.md) | `class SettingsActivity : AppCompatPreferenceActivity`<br>A [PreferenceActivity](#) that presents a set of application settings. On handset devices, settings are presented as a single list. On tablets, settings are split by category, with category headers shown to the left of the list of settings. |

