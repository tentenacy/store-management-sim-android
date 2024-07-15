package com.tenutz.storemngsim.ui.menu.optionmenu

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionsResponse
import com.tenutz.storemngsim.databinding.ItemOptionMenusBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class OptionMenusViewHolder(
    val binding: ItemOptionMenusBinding,
    private val onClickListener: (Int, Any?) -> Unit,
): BaseViewHolder<OptionsResponse.Option>(binding.root) {

    private lateinit var _item: OptionsResponse.Option
    val item: OptionsResponse.Option get() = _item

    init {
        binding.constraintIoptionMenusContainer.setOnClickListener {
            onClickListener(it.id, item)
        }
    }

    override fun bind(item: OptionsResponse.Option) {
        _item = item
        binding.args = item
    }
}

class OptionMenusAdapter(
    private val onClickListener: (Int, Any?) -> Unit,
): BaseRecyclerView<OptionsResponse.Option, OptionMenusViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionMenusViewHolder {
        return OptionMenusViewHolder(ItemOptionMenusBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClickListener)
    }

    override fun getItemId(position: Int): Long {
        return items[position].optionCode.hashCode().toLong()
    }
}