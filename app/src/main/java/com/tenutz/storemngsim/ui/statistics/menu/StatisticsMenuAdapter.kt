package com.tenutz.storemngsim.ui.statistics.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList
import com.tenutz.storemngsim.databinding.ItemTstatisticsMenuBinding
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class StatisticsMenuViewHolder(
    private val binding: ItemTstatisticsMenuBinding,
): BaseViewHolder<MenuSalesList.MenuSales>(binding.root) {

    override fun bind(position: Int, item: MenuSalesList.MenuSales) {
        binding.args = item
    }
}

class StatisticsMenuAdapter: PagingDataAdapter<MenuSalesList.MenuSales, StatisticsMenuViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsMenuViewHolder {
        return StatisticsMenuViewHolder(ItemTstatisticsMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StatisticsMenuViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(position, it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MenuSalesList.MenuSales>() {
            override fun areItemsTheSame(oldItem: MenuSalesList.MenuSales, newItem: MenuSalesList.MenuSales): Boolean {
//                return oldItem.id == newItem.id
                return false
            }

            override fun areContentsTheSame(oldItem: MenuSalesList.MenuSales, newItem: MenuSalesList.MenuSales): Boolean {
//                return oldItem == newItem
                return false
            }
        }
    }
}