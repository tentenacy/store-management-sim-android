package com.tenutz.storemngsim.ui.sales

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tenutz.storemngsim.data.datasource.paging.entity.SalesList
import com.tenutz.storemngsim.databinding.ItemSalesBinding
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class SalesViewHolder(
    private val binding: ItemSalesBinding,
    private val onClickListener: (Int, Any?) -> Unit,
): BaseViewHolder<SalesList.Sales>(binding.root) {

    override fun bind(position: Int, item: SalesList.Sales) {
        binding.args = item
    }
}

class SalesAdapter(private val onClickListener: (Int, Any?) -> Unit): PagingDataAdapter<SalesList.Sales, SalesViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesViewHolder {
        return SalesViewHolder(ItemSalesBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClickListener)
    }

    override fun onBindViewHolder(holder: SalesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(position, it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<SalesList.Sales>() {
            override fun areItemsTheSame(oldItem: SalesList.Sales, newItem: SalesList.Sales): Boolean {
//                return oldItem.id == newItem.id
                return false
            }

            override fun areContentsTheSame(oldItem: SalesList.Sales, newItem: SalesList.Sales): Boolean {
//                return oldItem == newItem
                return false
            }
        }
    }
}