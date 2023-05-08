package com.ripplearc.heavy.toolbelt.picasso

import android.graphics.Bitmap
import android.net.Uri
import android.util.Size
import com.squareup.picasso.Picasso
import io.reactivex.Single
import javax.inject.Inject

class ImageLoader @Inject constructor() {

    fun getBitmapSingle(picasso: Picasso, uri: Uri, size: Size?): Single<Bitmap> = Single.create {
        try {
            if (!it.isDisposed) {
                val request = picasso
                    .load(uri)
                val bitmap = if (size == null) {
                    request.get()
                } else {
                    request.resize(size.width, size.height).get()
                }
                it.onSuccess(bitmap)
            }
        } catch (e: Throwable) {
            it.onError(e)
        }
    }
}