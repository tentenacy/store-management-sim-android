package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.tenutz.storemngsim.databinding.ItemOgOptionMenuAddBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgOptionMenuAddArgs

class OgOptionMenuAddViewHolder(
    val binding: ItemOgOptionMenuAddBinding,
    private val listener: (OgOptionMenuAddArgs.OptionGroupOptionMenus) -> Unit
): BaseViewHolder<OgOptionMenuAddArgs.OptionGroupOptionMenus>(binding.root) {

    override fun bind(position: Int, item: OgOptionMenuAddArgs.OptionGroupOptionMenus) {
        binding.args = item
        binding.textIogOptionMenuAdd.setOnClickListener {
            listener(item)
        }
    }
}

class OgOptionMenuAddAdapter(
    private val listener: (OgOptionMenuAddArgs.OptionGroupOptionMenus) -> Unit
): BaseRecyclerView<OgOptionMenuAddArgs.OptionGroupOptionMenus, OgOptionMenuAddViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OgOptionMenuAddViewHolder {
        return OgOptionMenuAddViewHolder(
            ItemOgOptionMenuAddBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener,
        )
    }

    override fun getItemId(position: Int): Long {
        return "${items[position].optionCode}".hashCode().toLong()
    }
}