package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuOptionGroupsResponse
import com.tenutz.storemngsim.databinding.ItemMmOptionGroupAddBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class MmOptionGroupAddViewHolder(val binding: ItemMmOptionGroupAddBinding, private val listener: (MainMenuOptionGroupsResponse.MainMenuOptionGroup) -> Unit): BaseViewHolder<MainMenuOptionGroupsResponse.MainMenuOptionGroup>(binding.root) {

    override fun bind(position: Int, item: MainMenuOptionGroupsResponse.MainMenuOptionGroup) {
        binding.name = item.optionName
        binding.code = item.optionGroupCode
        binding.textImmOptionGroupAdd.setOnClickListener {
            listener(item)
        }
    }
}

class MmOptionGroupAddAdapter(private val listener: (MainMenuOptionGroupsResponse.MainMenuOptionGroup) -> Unit): BaseRecyclerView<MainMenuOptionGroupsResponse.MainMenuOptionGroup, MmOptionGroupAddViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MmOptionGroupAddViewHolder {
        return MmOptionGroupAddViewHolder(ItemMmOptionGroupAddBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }
}