package com.example.hotel.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.hotel.R
import com.example.hotel.databinding.GroupHolderBinding
import com.example.hotel.databinding.InfoTouristHolderBinding
import com.google.android.material.textfield.TextInputLayout

class ExpandableListAdapter(
    private val context: Context,
    var listTouristGroup: List<String>,
    var listTouristChild: Map<String, List<String>>
) : BaseExpandableListAdapter() {

    var touristList: MutableMap<Pair<Int, Int>, String> = mutableMapOf()
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
        val binding = InfoTouristHolderBinding
            .inflate(LayoutInflater.from(context), parent, false)
        binding.tilItem.hint = childText
        binding.etItem.hint = childText
        val savedText = touristList[Pair(groupPosition, childPosition)]
        binding.etItem.setText(savedText)
        setBackgroundField(binding.etItem, binding.tilItem)

        binding.etItem.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                touristList[Pair(groupPosition, childPosition)] = binding.etItem.text.toString()
                setBackgroundField(binding.etItem, binding.tilItem)
                hideKeyboard(binding.root)
            }
        }

        return binding.root
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


    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val headerTitle = getGroup(groupPosition) as String
        val binding = GroupHolderBinding
            .inflate(LayoutInflater.from(context), parent, false)
        binding.tvTourist.text = headerTitle

        binding.ibIndicator.setOnClickListener {
            val expandableListView = parent as ExpandableListView
            if (expandableListView.isGroupExpanded(groupPosition)) {
                expandableListView.collapseGroup(groupPosition)
                hideKeyboard(binding.root)
            } else {
                expandableListView.expandGroup(groupPosition)
                hideKeyboard(binding.root)
            }
        }

        if (isExpanded) {
            binding.ibIndicator.setImageResource(R.drawable.icon_arrow_up)
        } else {
            binding.ibIndicator.setImageResource(R.drawable.icon_arrow_down)
        }

        return binding.root
    }

    private fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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


