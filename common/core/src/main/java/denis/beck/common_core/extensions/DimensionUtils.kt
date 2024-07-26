package denis.beck.common_core.extensions

import android.content.res.Resources
import android.util.DisplayMetrics

private val displayMetrics: DisplayMetrics by lazy { Resources.getSystem().displayMetrics }

val widthDp = displayMetrics.run { widthPixels / density }

val Float.dpToPx get() = this * displayMetrics.density
val Int.dpToPx get() = this * displayMetrics.density