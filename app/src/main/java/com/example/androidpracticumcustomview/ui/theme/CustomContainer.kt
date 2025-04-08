package com.example.androidpracticumcustomview.ui.theme

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

            // Суммируем размеры каждого дочернего элемента
            totalWidth = maxOf(totalWidth, child.measuredWidth)
            totalHeight += child.measuredHeight
        }

        // Применяем размеры
        val width = resolveSize(totalWidth, widthMeasureSpec)
        val height = resolveSize(totalHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

        if (childCount == 1) {

            val child = getChildAt(0)

            val childLeft = (right - left - child.measuredWidth) / 2
            val childRight = childLeft + child.measuredWidth
            val childBottom = top + child.measuredHeight

            child.layout(childLeft, top, childRight, childBottom)
        } else {

            val child = getChildAt(1)

            val childLeft = (right - left - child.measuredWidth) / 2
            val childTop = bottom - child.measuredHeight
            val childRight = childLeft + child.measuredWidth

            child.layout(childLeft, childTop, childRight, bottom)
        }

    }

    override fun addView(child: View) {
        if (this.childCount == 2) {
            throw IllegalStateException("Элементов может быть только два")
        }

        super.addView(child)
    }
}