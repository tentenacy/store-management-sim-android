package com.tenutz.storemngsim.ui.menu.optiongroup

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupsResponse
import com.tenutz.storemngsim.databinding.ItemOptionGroupsBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class OptionGroupsViewHolder(val binding: ItemOptionGroupsBinding, private val listener: (OptionGroupsResponse.OptionGroup) -> Unit): BaseViewHolder<OptionGroupsResponse.OptionGroup>(binding.root) {

    override fun bind(position: Int, item: OptionGroupsResponse.OptionGroup) {
        binding.name = item.optionGroupName
        binding.code = item.optionGroupCode
        binding.constraintIoptionGroups.setOnClickListener {
            listener(item)
        }
    }
}

class OptionGroupsAdapter(private val listener: (OptionGroupsResponse.OptionGroup) -> Unit): BaseRecyclerView<OptionGroupsResponse.OptionGroup, OptionGroupsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionGroupsViewHolder {
        return OptionGroupsViewHolder(ItemOptionGroupsBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }
}