package com.tenutz.storemngsim.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByMenusTodayResponse
import com.tenutz.storemngsim.databinding.ItemPieMenuBinding

class PieMenusAdapter(val context: Context): BaseAdapter() {

    var items: ArrayList<StatisticsSalesByMenusTodayResponse.StatisticsSalesByMenuToday> = arrayListOf()

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding = ItemPieMenuBinding.inflate(LayoutInflater.from(context), parent, false)

        binding.args = items[position]
        binding.viewIpieMenu1.backgroundTintList = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(
                ContextCompat.getColor(
                    context,
                    when(position) {
                        0 -> R.color.rect_4476
                        1 -> R.color.rect_4475
                        2 -> R.color.rect_4475_a50
                        else -> R.color.rect_4480
                    }
                )
            )
        )

        return binding.root
    }

    fun updateItems(items: List<StatisticsSalesByMenusTodayResponse.StatisticsSalesByMenuToday>?) {
        items?.let {
            this.items.run {
                clear()
                addAll(it)
                notifyDataSetChanged()
            }
        }
    }
}