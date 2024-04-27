package com.foodapp.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.Review
import com.foodapp.helper.helper

class MultipleChoiceSpinnerAdapter (context: Context, private val items: List<String>) :
    ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items) {

    private var selectedItems = HashSet<String>()
    private var selectedCheckBox = HashSet<CheckBox>()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner_multiple_choice, parent, false)

        val checkbox = view.findViewById<CheckBox>(R.id.checkBox)
        checkbox.setOnClickListener {
            if(checkbox.isSelected) {
                checkbox.isSelected = false
                selectedCheckBox.remove(checkbox);
            }else{
                checkbox.isSelected = true
                selectedCheckBox.add(checkbox);
            }
        }
        checkbox.text = items[position];

        return view
    }

    fun getSelectedItems(): Set<String> {
        selectedCheckBox.forEach {
            selectedItems.add(it.text.toString());
        }
        return selectedItems
    }
}