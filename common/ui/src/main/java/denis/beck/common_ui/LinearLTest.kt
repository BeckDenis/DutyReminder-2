package denis.beck.common_ui

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat

class LinearLTest @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private lateinit var view2: FrameLayout

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        view2 = FrameLayout(context)
        addView(view2)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val mainHandler = Handler(context.mainLooper)
        view2.layoutParams.apply {
            height = 500
            width = 500
        }
        view2.setBackgroundColor(Color.parseColor("#758456"))
        val runnable = Runnable {
            view2.requestLayout()
        }
        mainHandler.post(runnable)
    }

}