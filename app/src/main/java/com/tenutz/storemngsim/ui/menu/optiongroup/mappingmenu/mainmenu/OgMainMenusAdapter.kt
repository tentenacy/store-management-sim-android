package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMappersResponse
import com.tenutz.storemngsim.databinding.ItemTogMainMenusBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class OgMainMenusViewHolder(val binding: ItemTogMainMenusBinding, private val listener: (OptionGroupMainMenuMappersResponse.OptionGroupMainMenuMapper) -> Unit): BaseViewHolder<OptionGroupMainMenuMappersResponse.OptionGroupMainMenuMapper>(binding.root) {

    override fun bind(position: Int, item: OptionGroupMainMenuMappersResponse.OptionGroupMainMenuMapper) {
        binding.args = item
        binding.constraintItogMainMenusContainer.setOnClickListener {
            listener(item)
        }
    }
}

class OgMainMenusAdapter(private val listener: (OptionGroupMainMenuMappersResponse.OptionGroupMainMenuMapper) -> Unit): BaseRecyclerView<OptionGroupMainMenuMappersResponse.OptionGroupMainMenuMapper, OgMainMenusViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OgMainMenusViewHolder {
        return OgMainMenusViewHolder(ItemTogMainMenusBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun getItemId(position: Int): Long {
        return "${items[position].mainCategoryCode}${items[position].middleCategoryCode}${items[position].subCategoryCode}${items[position].menuCode}".hashCode().toLong()
    }
}