package denis.beck.common_ui

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import denis.beck.common_core.extensions.dpToPx
import denis.beck.common_core.extensions.heightPx
import denis.beck.common_core.extensions.widthPx
import denis.beck.common_ui.databinding.ColorPickerLayoutBinding

/**
 * Actually we have a lot of problem here.
 * 1) Plate don't need have a margin. It's must be padding so user can tap around
 * 2) calculating plate size and background center have too much magical numbers
 * 3) Fucking windows and different locations. You cant just get all right so center of background will be some random
 */
class ColorPickerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        const val PLATE_PADDINGS = 4
        const val SIDE_PLATE_SIZE_INCREASE = 4
        const val SELECTED_PLATE_SIZE_INCREASE = 16
    }

    private val binding = ColorPickerLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    private var selectedIndex = 0

    private val colorSet = setOf(
        "#FFFFFF",
        "#FFC107",
        "#CDDC39",
        "#009688",
        "#03A9F4",
        "#3F51B5",
        "#673AB7",
        "#9C27B0",
        "#F44336",
    )

    private var plateSize = 0
    private var sidePlateSize = 0
    private var selectedPlateSize = 0
    private var viewHeight = 0

    private var selectColorListener: ((String) -> Unit) = {}
    private var yCoordinate: Int = 0
    private val xCoordinates = IntArray(colorSet.size)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        colorSet.forEachIndexed { index, color ->
            createColorPlateView(index, color).also {
                binding.root.addView(it)
            }
        }

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(
            object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    calculatePlateSizes(binding.root.width)
                    for (index in colorSet.indices) {
                        updatePlateSize(index)
                    }
                    setViewHeight()
                    calculateXCoordinates(binding.root.width)
                    drawStartBackground()
                    binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })

        binding.root.children.forEach { view ->
            (view.layoutParams as MarginLayoutParams)
                .setMargins(PLATE_PADDINGS.dpToPx.toInt())
        }
    }

    private fun drawStartBackground() {
        (binding.root.parent.parent as View).background = getBackground2(
            Color.parseColor(colorSet.elementAt(selectedIndex)),
            xCoordinates[selectedIndex]
        )
    }

    private fun calculateXCoordinates(width: Int) {
        val locations = IntArray(2)
        binding.root.getLocationInWindow(locations)
        val pp = (width - locations[0]) / colorSet.size
        var next = locations[0]

        for (index in colorSet.indices) {
            next += pp
            xCoordinates[index] = next - (pp / 2)
        }
    }

    private fun calculatePlateSizes(width: Int) {
        val spaces = (colorSet.size * 2 * PLATE_PADDINGS.dpToPx) + (SIDE_PLATE_SIZE_INCREASE.dpToPx * 2) + SELECTED_PLATE_SIZE_INCREASE.dpToPx
        plateSize = ((width - spaces) / colorSet.size).toInt()
        sidePlateSize = plateSize + 4.dpToPx.toInt()
        selectedPlateSize = sidePlateSize + 16.dpToPx.toInt()
        viewHeight = selectedPlateSize + 16.dpToPx.toInt()
    }

    private fun setViewHeight() {
        val location = IntArray(2)
        binding.root.getLocationInWindow(location)
        binding.root.layoutParams.apply {
            height = viewHeight
        }
    }

    private fun updatePlateSize(index: Int) {
        requestLayout()
        val layoutSize = getPlateSize(index)
        val view = binding.root.children.elementAt(index)
        view.layoutParams.apply {
            height = layoutSize
            width = layoutSize
        }
    }

    private fun getPlateSize(index: Int): Int = when {
        index == selectedIndex -> selectedPlateSize
        (index - 1) == selectedIndex || (index + 1) == selectedIndex -> sidePlateSize
        else -> plateSize
    }

    private fun createColorPlateView(index: Int, color: String) = FrameLayout(context).apply {
        background = getPlateBackground(color)
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

        animateBackgroundChange(selectedIndex, index)

        selectedIndex = index

        animationIndexSet.forEach(::animateViewSize)

        selectColorListener(colorSet.elementAt(selectedIndex))

        val location = IntArray(2)
        binding.root.children.elementAt(selectedIndex).getLocationInWindow(location)
    }

    private fun animateBackgroundChange(pastIndex: Int, newIndex: Int) {
        val animationCoordX = ValueAnimator.ofInt(xCoordinates[pastIndex], xCoordinates[newIndex]).apply {
            duration = 300
        }
        val animationColor =
            ValueAnimator.ofArgb(Color.parseColor(colorSet.elementAt(pastIndex)), Color.parseColor(colorSet.elementAt(newIndex))).apply {
                duration = 300
            }

        animationCoordX.addUpdateListener { value ->
            (binding.root.parent.parent as View).background = getBackground2(animationColor.animatedValue as Int, value.animatedValue as Int)
        }

        animationCoordX.start()
        animationColor.start()
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

    fun setSelectedColor(color: String) {
        selectedIndex = colorSet.indexOf(color)
        drawStartBackground()
        for (index in colorSet.indices) {
            updatePlateSize(index)
        }
    }

    fun setOnColorSelectListener(listener: (String) -> Unit) {
        selectColorListener = listener
        selectColorListener(colorSet.elementAt(selectedIndex))
    }

    private fun getPlateBackground(color: String): Drawable =
        GradientDrawable().apply {
            setColor(Color.parseColor(color))
            cornerRadius = 4.dpToPx
        }

    private fun getBackground2(color: Int, xCoordinate: Int): Drawable =
        GradientDrawable().apply {
            // ToDo oh God, just fix this shit
            colors = intArrayOf(color, Color.parseColor("#000000"))
            gradientRadius = 1200F
            gradientType = GradientDrawable.RADIAL_GRADIENT
            setGradientCenter((xCoordinate / widthPx.toFloat()), (yCoordinate / heightPx.toFloat()))
        }
}