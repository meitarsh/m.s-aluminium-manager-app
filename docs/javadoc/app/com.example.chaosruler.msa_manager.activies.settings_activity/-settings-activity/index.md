[app](../../index.md) / [com.example.chaosruler.msa_manager.activies.settings_activity](../index.md) / [SettingsActivity](.)

# SettingsActivity

`class SettingsActivity : `[`AppCompatPreferenceActivity`](../-app-compat-preference-activity/index.md)

A [PreferenceActivity](#) that presents a set of application settings. On
handset devices, settings are presented as a single list. On tablets,
settings are split by category, with category headers shown to the left of
the list of settings.

See [Android Design: Settings](http://developer.android.com/design/patterns/settings.html)
for design guidelines and the [Settings API Guide](http://developer.android.com/guide/topics/ui/settings.html)
for more information on developing a Settings UI.

### Types

| [DataSyncPreferenceFragment](-data-sync-preference-fragment/index.md) | `class DataSyncPreferenceFragment : PreferenceFragment`<br>This fragment shows data and sync preferences only. It is used when the activity is showing a two-pane settings UI. |
| [DevelopMentSettingsPrefFragment](-develop-ment-settings-pref-fragment/index.md) | `class DevelopMentSettingsPrefFragment : PreferenceFragment` |
| [GeneralPreferenceFragment](-general-preference-fragment/index.md) | `class GeneralPreferenceFragment : PreferenceFragment`<br>This fragment shows general preferences only. It is used when the activity is showing a two-pane settings UI. |
| [NotificationPreferenceFragment](-notification-preference-fragment/index.md) | `class NotificationPreferenceFragment : PreferenceFragment`<br>This fragment shows notification preferences only. It is used when the activity is showing a two-pane settings UI. |
| [VPNSettingsFragment](-v-p-n-settings-fragment/index.md) | `class VPNSettingsFragment : PreferenceFragment`<br>This fragment shows data and sync preferences only. It is used when the activity is showing a two-pane settings UI. |

### Constructors

| [&lt;init&gt;](-init-.md) | `SettingsActivity()`<br>A [PreferenceActivity](#) that presents a set of application settings. On handset devices, settings are presented as a single list. On tablets, settings are split by category, with category headers shown to the left of the list of settings. |

### Inherited Properties

| [supportActionBar](../-app-compat-preference-activity/support-action-bar.md) | `val supportActionBar: ActionBar?` |

### Functions

| [isValidFragment](is-valid-fragment.md) | `fun isValidFragment(fragmentName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>This method stops fragment injection in malicious applications. Make sure to deny any unknown fragments here. |
| [onBuildHeaders](on-build-headers.md) | `fun onBuildHeaders(target: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<Header>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [onCreate](on-create.md) | `fun onCreate(savedInstanceState: Bundle?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onIsMultiPane](on-is-multi-pane.md) | `fun onIsMultiPane(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>{@inheritDoc} |
| [onOptionsItemSelected](on-options-item-selected.md) | `fun onOptionsItemSelected(item: MenuItem): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Inherited Functions

| [addContentView](../-app-compat-preference-activity/add-content-view.md) | `open fun addContentView(view: View, params: LayoutParams): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getMenuInflater](../-app-compat-preference-activity/get-menu-inflater.md) | `open fun getMenuInflater(): MenuInflater` |
| [invalidateOptionsMenu](../-app-compat-preference-activity/invalidate-options-menu.md) | `open fun invalidateOptionsMenu(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onConfigurationChanged](../-app-compat-preference-activity/on-configuration-changed.md) | `open fun onConfigurationChanged(newConfig: Configuration): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDestroy](../-app-compat-preference-activity/on-destroy.md) | `open fun onDestroy(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onPostCreate](../-app-compat-preference-activity/on-post-create.md) | `open fun onPostCreate(savedInstanceState: Bundle?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onPostResume](../-app-compat-preference-activity/on-post-resume.md) | `open fun onPostResume(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStop](../-app-compat-preference-activity/on-stop.md) | `open fun onStop(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onTitleChanged](../-app-compat-preference-activity/on-title-changed.md) | `open fun onTitleChanged(title: `[`CharSequence`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html)`, color: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setContentView](../-app-compat-preference-activity/set-content-view.md) | `open fun setContentView(layoutResID: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`open fun setContentView(view: View): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`open fun setContentView(view: View, params: LayoutParams): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setSupportActionBar](../-app-compat-preference-activity/set-support-action-bar.md) | `fun setSupportActionBar(toolbar: Toolbar?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

