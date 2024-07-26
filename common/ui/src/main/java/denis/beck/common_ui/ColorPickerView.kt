package denis.beck.common_ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.children
import denis.beck.common_core.extensions.dpToPx
import denis.beck.common_core.extensions.heightPx
import denis.beck.common_core.extensions.widthDp
import denis.beck.common_core.extensions.widthPx
import denis.beck.common_ui.databinding.ColorPickerLayoutBinding

/**
 * Actually we have a lot of problem here.
 * 1) Plate don't need have a margin. It's must be padding so user can tap around
 * 2) calculating plate size have too much magical numbers
 * 3) shadows and gradient background
 */
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

    private val plateSize = ((widthDp - (16 * 2) - (colorSet.size * 2 * 4) - 10.dpToPx) / colorSet.size).dpToPx.toInt()
    private val sidePlateSize = plateSize + 4.dpToPx.toInt()
    private val selectedPlateSize = sidePlateSize + 16.dpToPx.toInt()
    private val viewHeight = selectedPlateSize + 16.dpToPx.toInt()

    private var selectColorListener: ((String) -> Unit) = {}

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        colorSet.forEachIndexed { index, color ->
            createColorPlateView(index, color).also {
                binding.root.addView(it)
            }
        }
        binding.root.children.forEach { view ->
            (view.layoutParams as MarginLayoutParams)
                .setMargins(4.dpToPx.toInt(), 4.dpToPx.toInt(), 4.dpToPx.toInt(), 4.dpToPx.toInt())
        }
        val lp = binding.root.layoutParams.apply {
            height = viewHeight
        }
        binding.root.layoutParams = lp
    }

    private fun createColorPlateView(index: Int, color: String) = FrameLayout(context).apply {
        background = getPlateBackground(color)
        layoutParams = getLayoutParams(index)
        setOnClickListener {
            changeSelectedPlate(index)
        }
    }

    private fun changeSelectedPlate(index: Int) {
        val animationIndexSet = mutableSetOf(
            selectedIndex,
            selectedIndex - 1,
            selectedIndex + 1,
            index,
            index - 1,
            index + 1,
        ).filter { it >= 0 && it < colorSet.size }

        selectedIndex = index

        animationIndexSet.forEach(::animateViewSize)

        selectColorListener(colorSet.elementAt(selectedIndex))

        val location = IntArray(2)
        binding.root.children.elementAt(selectedIndex).getLocationOnScreen(location)
        binding.root.rootView.rootView.background = getBackground2(colorSet.elementAt(selectedIndex), location)
    }

    private fun animateViewSize(index: Int) {
        val view = binding.root.children.elementAt(index)
        val size = getPlateSize(index)
        val animation = ValueAnimator.ofInt(view.height, size)
        animation.addUpdateListener { value ->
            Log.d("prpr", "value: ${value.animatedValue}")
            val lp = view.layoutParams.apply {
                width = value.animatedValue as Int
                height = value.animatedValue as Int
            }
            view.layoutParams = lp
        }
        animation.duration = 300
        animation.start()
    }

    private fun getLayoutParams(index: Int): LinearLayoutCompat.LayoutParams {
        val layoutSize = getPlateSize(index)
        return LinearLayoutCompat.LayoutParams(layoutSize, layoutSize)
    }

    private fun getPlateSize(index: Int): Int = when {
        index == selectedIndex -> selectedPlateSize
        (index - 1) == selectedIndex || (index + 1) == selectedIndex -> sidePlateSize
        else -> plateSize
    }

    fun setOnColorSelectListener(listener: (String) -> Unit) {
        selectColorListener = listener
    }

    private fun getPlateBackground(color: String): Drawable =
        GradientDrawable().apply {
            setColor(Color.parseColor(color))
            cornerRadius = 4.dpToPx
        }

    private fun getBackground2(color: String, location: IntArray): Drawable =
        GradientDrawable().apply {
            colors = intArrayOf(Color.parseColor(color), Color.parseColor("#000000"))
            gradientRadius = 700F
            gradientType = GradientDrawable.RADIAL_GRADIENT
            setGradientCenter((location[0] / widthPx.toFloat()), (location[1] / heightPx.toFloat()))
        }

}