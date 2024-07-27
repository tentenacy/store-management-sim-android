package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuMappersResponse
import com.tenutz.storemngsim.databinding.ItemMmOptionGroupsBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class MmOptionGroupsViewHolder(val binding: ItemMmOptionGroupsBinding, private val listener: (MainMenuMappersResponse.MainMenuMapper) -> Unit): BaseViewHolder<MainMenuMappersResponse.MainMenuMapper>(binding.root) {

    override fun bind(position: Int, item: MainMenuMappersResponse.MainMenuMapper) {
        binding.name = item.optionName
        binding.code = item.optionGroupCode
        binding.constraintImmOptionGroups.setOnClickListener {
            listener(item)
        }
    }
}

class MmOptionGroupsAdapter(private val listener: (MainMenuMappersResponse.MainMenuMapper) -> Unit): BaseRecyclerView<MainMenuMappersResponse.MainMenuMapper, MmOptionGroupsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MmOptionGroupsViewHolder {
        return MmOptionGroupsViewHolder(ItemMmOptionGroupsBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }
}