package denis.beck.common_ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.allViews
import androidx.core.view.children
import denis.beck.common_core.extensions.dpToPx
import denis.beck.common_core.extensions.widthDp
import denis.beck.common_ui.databinding.ColorPickerLayoutBinding

class ColorPickerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ColorPickerLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    private var selectedIndex = 3

    private val colorSet = setOf(
        "#FFFFFF",
        "#F44336",
        "#9C27B0",
        "#673AB7",
        "#3F51B5",
        "#03A9F4",
        "#009688",
        "#CDDC39",
        "#FFC107",
    )

    private val views = mutableListOf<FrameLayout>()

    private var selectColorListener: ((String) -> Unit) = {}

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.apply {
            colorSet.forEachIndexed { index, color ->

                val view = FrameLayout(context).apply {
                    background = getBackground(color)
                    layoutParams = getLayoutParams(index)
                    setOnClickListener {
                        selectedIndex = index
                        selectColorListener(color)
                        binding.root.children.forEachIndexed { index, view ->
                            view.layoutParams = getLayoutParams(index).also {
                                it.setMargins(4.dpToPx.toInt(), 4.dpToPx.toInt(), 4.dpToPx.toInt(), 4.dpToPx.toInt())
                            }
                        }
                        this@ColorPickerView.requestLayout()
                    }
                }
                root.addView(view)
                (view.layoutParams as MarginLayoutParams).setMargins(4.dpToPx.toInt(), 4.dpToPx.toInt(), 4.dpToPx.toInt(), 4.dpToPx.toInt())
            }
        }
    }

    private fun getLayoutParams(index: Int): LinearLayoutCompat.LayoutParams {
        val width = ((widthDp - (16 * 2) - (colorSet.size * 2 * 4) - 4.dpToPx) / colorSet.size)
        val layoutWidth = if (index == selectedIndex) {
            width.dpToPx.toInt() + 8.dpToPx.toInt()
        } else {
            width.dpToPx.toInt()
        }
        return LinearLayoutCompat.LayoutParams(layoutWidth, layoutWidth)
    }

    fun setOnColorSelectListener(listener: (String) -> Unit) {
        selectColorListener = listener
    }

    private fun getBackground(color: String): Drawable =
        GradientDrawable().apply {
            setColor(Color.parseColor(color))
            cornerRadius = 4.dpToPx
        }
}