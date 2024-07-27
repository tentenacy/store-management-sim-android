package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.databinding.ItemOgMainMenuAddV2Binding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgMainMenuAddArgs

class OgMainMenuAddViewHolderV2(
    val binding: ItemOgMainMenuAddV2Binding,
    private val listener: (OgMainMenuAddArgs.OptionGroupMainMenus) -> Unit
): BaseViewHolder<OgMainMenuAddArgs.OptionGroupMainMenus>(binding.root) {

    override fun bind(position: Int, item: OgMainMenuAddArgs.OptionGroupMainMenus) {
        binding.args = item
        binding.textIogMainMenuAddV2.setOnClickListener {
            listener(item)
        }
    }
}

class OgMainMenuAddAdapterV2(
    private val listener: (OgMainMenuAddArgs.OptionGroupMainMenus) -> Unit
): BaseRecyclerView<OgMainMenuAddArgs.OptionGroupMainMenus, OgMainMenuAddViewHolderV2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OgMainMenuAddViewHolderV2 {
        return OgMainMenuAddViewHolderV2(
            ItemOgMainMenuAddV2Binding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener,
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun expandOrContractAll() {
        val items = if(this.items.count { it.expanded } == this.items.size) {
            this.items.asSequence().onEach { it.expanded = false }.toList()
        } else {
            this.items.asSequence().onEach { it.expanded = true }.toList()
        }
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return "${items[position].mainCategoryCode}${items[position].middleCategoryCode}${items[position].subCategoryCode}${items[position].menuCode}".hashCode().toLong()
    }
}