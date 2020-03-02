package com.ripplearc.heavy.common.toolbox

import dagger.Reusable
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import net.grandcentrix.tray.TrayPreferences
import net.grandcentrix.tray.core.OnTrayPreferenceChangeListener
import javax.inject.Inject


/**
 * RxCommonPreference emits the preference value saved to the CommonPreference.
 */
@Reusable
class RxCommonPreference @Inject constructor(
    val preference: TrayPreferences
) {

    /**
     * Observe the value change on a given key
     * @param key of the preference to be observed
     * @return an observable that emits the value change of the key. If the key doesn't exist
     * emit false.
     */
    inline fun <reified T : Any> getObserve(key: String, defaultValue: T): Observable<T> =
        Observable.defer {
            keyChange(preference)
                .filter { it == key }
                .mapNotNull { key ->
                    defaultValue.getValue(key)
                }
                .startWithNotNull(defaultValue.getValue(key))
        }

    inline fun <reified T : Any> T.getValue(key: String): T? =
        when (this) {
            is Long -> preference.getLong(key, this)
            is Boolean -> preference.getBoolean(key, this)
            is String -> preference.getString(key, this)
            else -> this
        }.let {
            it as? T
        }

    /**
     * Save the value to the preference on the key.
     * @param key save value to the key.
     * @param value value saved in the preference.
     * @return a single indicates the success of save.
     */
    fun <T : Any> setCompletable(key: String, value: T): Completable =
        Completable.create { emitter ->
            val success = when (value) {
                is Long -> preference.put(key, value)
                is Boolean -> preference.put(key, value)
                is String -> preference.put(key, value)
                else -> false
            }
            if (success)
                emitter.onSafeComplete()
            else
                emitter.onSafeError(Throwable("Fail to save value to preference."))
        }

    fun keyChange(preferences: TrayPreferences): Observable<String> =
        Observable.create<String> { emitter ->
            OnTrayPreferenceChangeListener { trays ->
                trays.forEach {
                    emitter.onSafeNext(it.key())
                }
            }.let {
                preferences.registerOnTrayPreferenceChangeListener(it)
                emitter.setCancellable {
                    preferences.unregisterOnTrayPreferenceChangeListener(it)
                }
            }
        }
            .share()

}