package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoriesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuMappersResponse
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuOptionGroupsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionMappersResponse
import com.tenutz.storemngsim.databinding.ItemMiddleCategoriesBinding
import com.tenutz.storemngsim.databinding.ItemOmOptionGroupsBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class OmOptionGroupsViewHolder(val binding: ItemOmOptionGroupsBinding, private val listener: (OptionMappersResponse.OptionMapper) -> Unit): BaseViewHolder<OptionMappersResponse.OptionMapper>(binding.root) {

    override fun bind(position: Int, item: OptionMappersResponse.OptionMapper) {
        binding.name = item.optionName
        binding.code = item.optionGroupCode
        binding.constraintIomOptionGroups.setOnClickListener {
            listener(item)
        }
    }
}

class OmOptionGroupsAdapter(private val listener: (OptionMappersResponse.OptionMapper) -> Unit): BaseRecyclerView<OptionMappersResponse.OptionMapper, OmOptionGroupsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OmOptionGroupsViewHolder {
        return OmOptionGroupsViewHolder(ItemOmOptionGroupsBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }
}