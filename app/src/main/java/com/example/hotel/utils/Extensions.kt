package com.example.hotel.utils

import android.util.TypedValue
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.hotel.R


fun setPeculiaritiesLayout(view: LinearLayout, peculiarities: List<String>) {

    view.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            view.viewTreeObserver.removeOnGlobalLayoutListener(this)

            val maxWidth = view.width

            var currentLineWidth = 0
            var currentRowLayout = LinearLayout(view.context)
            currentRowLayout.orientation = LinearLayout.HORIZONTAL
            view.addView(currentRowLayout)

            for (peculiarity in peculiarities) {
                val peculiarityTextView = TextView(view.context)
                peculiarityTextView.text = peculiarity
                val backgroundText = ContextCompat.getDrawable(
                    view.context,
                    R.drawable.rounded_corners_peculiarity
                )
                peculiarityTextView.background = backgroundText
                val colorText = ContextCompat.getColor(view.context, R.color.grey_600)
                peculiarityTextView.setTextColor(colorText)
                peculiarityTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.toFloat())

                val densityDpi = view.context.resources.displayMetrics.densityDpi
                val leftPaddingDp = densityDpi / 160 * 10
                val rightPaddingDp = densityDpi / 160 * 10
                val topPaddingDp = densityDpi / 160 * 5
                val bottomPaddingDp = densityDpi / 160 * 5
                peculiarityTextView.setPadding(
                    leftPaddingDp,
                    topPaddingDp,
                    rightPaddingDp,
                    bottomPaddingDp
                )

                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                layoutParams.setMargins(8, 8, 8, 8)

                peculiarityTextView.layoutParams = layoutParams
                peculiarityTextView.measure(0, 0)

                if (currentLineWidth + peculiarityTextView.measuredWidth > maxWidth) {
                    currentRowLayout = LinearLayout(view.context)
                    currentRowLayout.orientation = LinearLayout.HORIZONTAL
                    view.addView(currentRowLayout)
                    currentLineWidth = 0
                }

                currentRowLayout.addView(peculiarityTextView)
                currentLineWidth += peculiarityTextView.measuredWidth
            }
        }
    })
}
