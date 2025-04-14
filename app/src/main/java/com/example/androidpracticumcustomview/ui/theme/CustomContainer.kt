package com.example.androidpracticumcustomview.ui.theme

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.androidpracticumcustomview.R

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        setWillNotDraw(false)
        setBackgroundColor(ContextCompat.getColor(context, R.color.purple_500))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var totalWidth = 0
        var totalHeight = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)


            totalWidth = maxOf(totalWidth, child.measuredWidth)
            totalHeight += child.measuredHeight
        }

        val width = resolveSize(totalWidth, widthMeasureSpec)
        val height = resolveSize(totalHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

        val containerWidth = right - left
        val containerHeight = bottom - top

        if (childCount == 1) {

            val child = getChildAt(0)

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            val centerX = (containerWidth - childWidth) / 2
            val centerY = (containerHeight - childHeight) / 2

            child.layout(centerX, centerY, centerX + childWidth, centerY + childHeight)

            val deltaY = (paddingTop - centerY).toFloat()

            val alfa = ObjectAnimator.ofFloat(child, ALPHA, 0f, 1f).apply {
                duration = 2000
            }

            val translation = ObjectAnimator.ofFloat(child, TRANSLATION_Y, 0f, deltaY).apply {
                duration = 5000

            }

            AnimatorSet().apply {
                playTogether(alfa, translation)
                start()
            }


        } else {

            val child = getChildAt(1)

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            val centerX = (containerWidth - childWidth) / 2
            val centerY = (containerHeight - childHeight) / 2

            child.layout(centerX, centerY, centerX + childWidth, centerY + childHeight)

            val deltaY = (paddingBottom + centerY).toFloat()

            val alfa = ObjectAnimator.ofFloat(child, ALPHA, 0f, 1f).apply {
                duration = 2000
            }

            val translation = ObjectAnimator.ofFloat(child, TRANSLATION_Y, 0f, deltaY).apply {
                duration = 5000

            }

            AnimatorSet().apply {
                playTogether(alfa, translation)
                start()
            }
        }

    }

    override fun addView(child: View) {
        if (this.childCount == 2) {
            throw IllegalStateException("Элементов может быть только два")
        }

        /*   child.apply {
               alpha = 0f
           }*/

        super.addView(child)

        /*      child.animate()
                  .alpha(1f)
                  .setDuration(2000)*/
    }
}