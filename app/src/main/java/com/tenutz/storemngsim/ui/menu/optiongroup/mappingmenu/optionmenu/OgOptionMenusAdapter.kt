package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupOptionMappersResponse
import com.tenutz.storemngsim.databinding.ItemTogOptionMenusBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class OgOptionMenusViewHolder(val binding: ItemTogOptionMenusBinding, private val listener: (OptionGroupOptionMappersResponse.OptionGroupOptionMapper) -> Unit): BaseViewHolder<OptionGroupOptionMappersResponse.OptionGroupOptionMapper>(binding.root) {

    override fun bind(position: Int, item: OptionGroupOptionMappersResponse.OptionGroupOptionMapper) {
        binding.args = item
        binding.constraintItogOptionMenusContainer.setOnClickListener {
            listener(item)
        }
    }
}

class OgOptionMenusAdapter(private val listener: (OptionGroupOptionMappersResponse.OptionGroupOptionMapper) -> Unit): BaseRecyclerView<OptionGroupOptionMappersResponse.OptionGroupOptionMapper, OgOptionMenusViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OgOptionMenusViewHolder {
        return OgOptionMenusViewHolder(ItemTogOptionMenusBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun getItemId(position: Int): Long {
        return "${items[position].optionCode}".hashCode().toLong()
    }
}