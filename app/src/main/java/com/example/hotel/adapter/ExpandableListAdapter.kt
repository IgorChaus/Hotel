package com.example.hotel.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.hotel.R
import com.google.android.material.textfield.TextInputLayout

class ExpandableListAdapter(
    private val context: Context,
    var listTouristGroup: List<String>,
    var listTouristChild: Map<String, List<String>>
) : BaseExpandableListAdapter() {

    val touristList: MutableMap<Pair<Int, Int>, String> = mutableMapOf()
    var isShowError = false

    override fun getGroup(groupPosition: Int): Any {
        return listTouristGroup[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    @SuppressLint("MissingInflatedId")
    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val childText = getChild(groupPosition, childPosition) as String
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.info_tourist_holder, null)
        val textInputLayout: TextInputLayout = view.findViewById(R.id.til_item)
        textInputLayout.hint = childText
        val editText: EditText = view.findViewById(R.id.et_item)
        editText.hint = childText
        val edText = touristList[Pair(groupPosition, childPosition)]
        editText.setText(edText)
        setBackgroundField(editText, textInputLayout)

        editText.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                touristList.put(Pair(groupPosition, childPosition), editText.text.toString())
                setBackgroundField(editText, textInputLayout)
            }
        }

        return view
    }

    private fun setBackgroundField(
        editText: EditText,
        textInputLayout: TextInputLayout
    ) {
        if (isShowError) {
            val usualColor = ContextCompat.getDrawable(
                editText.context,
                R.drawable.rounded_corners_contact
            )
            val errorColor = ContextCompat.getDrawable(
                editText.context,
                R.drawable.rounded_corners_error
            )
            if (editText.text == null || editText.text.isEmpty()) {
                textInputLayout.background = errorColor
                editText.background
            } else {
                textInputLayout.background = usualColor
                editText.background = usualColor
            }
        }
    }


    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val headerTitle = getGroup(groupPosition) as String
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.group_holder, null)
        val lblListHeader = view.findViewById<TextView>(R.id.tv_tourist)
        lblListHeader.text = headerTitle

        val ibIndicator: ImageButton = view.findViewById(R.id.ib_indicator)

        ibIndicator.setOnClickListener {
            val expandableListView = parent as ExpandableListView
            if (expandableListView.isGroupExpanded(groupPosition)) {
                expandableListView.collapseGroup(groupPosition)
            } else {
                expandableListView.expandGroup(groupPosition)
            }
        }

        if (isExpanded) {
            ibIndicator.setImageResource(R.drawable.icon_arrow_up)
        } else {
            ibIndicator.setImageResource(R.drawable.icon_arrow_down)
        }

        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listTouristChild[listTouristGroup[groupPosition]]?.size ?: 0
    }

    override fun getGroupCount(): Int {
        return listTouristGroup.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return listTouristChild[listTouristGroup[groupPosition]]?.get(childPosition) ?: ""
    }
}


