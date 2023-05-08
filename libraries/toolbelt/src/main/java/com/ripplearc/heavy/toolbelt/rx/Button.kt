package com.ripplearc.heavy.toolbelt.rx

import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.ripplearc.heavy.toolbelt.constants.Emoji
import io.reactivex.Observable

/**
 * An observable emits Unit when user clicks on the Button.
 */
val Button.clickObserve: Observable<Unit>
	get() = Observable.create<Unit> { emitter ->
		View.OnClickListener {
			emitter.onSafeNext(Unit)
		}.let {
			setOnClickListener(it)
		}

		emitter.setCancellable { setOnClickListener(null) }
	}

/**
 * An observable emits true or false when the button is checked or unchecked.
 */
val CompoundButton.clickObserve: Observable<Boolean>
	get() = Observable.create<Boolean> { emitter ->
		View.OnClickListener {
			emitter.onSafeNext(isChecked)
		}.let {
			setOnClickListener(it)
		}
		emitter.setCancellable { setOnClickListener(null) }
	}

val RadioGroup.checkedObserve: Observable<Int>
	get() = Observable.create<Int> { emitter ->
		RadioGroup.OnCheckedChangeListener { group, checkedId ->
			emitter.onSafeNext(checkedId)
		}.let {
			setOnCheckedChangeListener(it)
		}
		emitter.setCancellable { setOnCheckedChangeListener(null) }
	}
