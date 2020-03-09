package com.ripplearc.heavy.common.rxUtil

import android.content.Context
import android.content.SharedPreferences
import dagger.Reusable
import io.reactivex.Completable
import io.reactivex.Observable


/**
 * RxPreference emits the preference value saved to the SharedPreference.
 */
@Reusable
class RxPreference constructor(
    val preference: SharedPreferences
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
            when (value) {
                is Long -> preference.edit().putLong(key, value).apply()
                is Boolean -> preference.edit().putBoolean(key, value).apply()
                is String -> preference.edit().putString(key, value).apply()
                else -> {
                }
            }
            emitter.onComplete()
        }

    fun keyChange(preferences: SharedPreferences): Observable<String> =
        Observable.create<String> { emitter ->
            SharedPreferences.OnSharedPreferenceChangeListener { preference, key ->
                emitter.onSafeNext(key)
            }.let {
                preferences.registerOnSharedPreferenceChangeListener(it)
                emitter.setCancellable {
                    preferences.unregisterOnSharedPreferenceChangeListener(it)
                }
            }
        }
            .share()

    companion object {
        fun create(context: Context): RxPreference =
            RxPreference(context.getSharedPreferences("RxPrefs", Context.MODE_PRIVATE))
    }
}