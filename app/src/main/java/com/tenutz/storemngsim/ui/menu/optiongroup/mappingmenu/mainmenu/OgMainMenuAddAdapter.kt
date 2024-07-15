package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.tenutz.storemngsim.databinding.ItemOgMainMenuAddBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgMainMenuAddArgs

class OgMainMenuAddViewHolder(
    val binding: ItemOgMainMenuAddBinding,
    private val onExpandedChangeListener: (OgMainMenuAddArgs.OptionGroupMainMenus) -> Unit,
    private val listener: (OgMainMenuAddArgs.OptionGroupMainMenus) -> Unit
): BaseViewHolder<OgMainMenuAddArgs.OptionGroupMainMenus>(binding.root) {

    init {
        binding.constraintIogMainMenuAddExpandableContainer.setOnClickListener {
            binding.constraintIogMainMenuAddCategoriesContainer.visibility =
                if(binding.constraintIogMainMenuAddCategoriesContainer.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
        binding.constraintIogMainMenuAddCategoriesContainer.tag = binding.constraintIogMainMenuAddCategoriesContainer.visibility
    }

    override fun bind(item: OgMainMenuAddArgs.OptionGroupMainMenus) {
        binding.args = item
        if(item.expanded != binding.constraintIogMainMenuAddCategoriesContainer.isVisible) {
            onExpandedChangeListener(item)
            binding.constraintIogMainMenuAddCategoriesContainer.visibility = if(item.expanded) View.VISIBLE else View.GONE
        }
        binding.constraintIogMainMenuAddCategoriesContainer.viewTreeObserver.addOnGlobalLayoutListener {
            if(binding.constraintIogMainMenuAddCategoriesContainer.tag as Int != binding.constraintIogMainMenuAddCategoriesContainer.visibility) {
                binding.constraintIogMainMenuAddCategoriesContainer.tag = binding.constraintIogMainMenuAddCategoriesContainer.visibility
                //visibility has changed
                item.expanded = binding.constraintIogMainMenuAddCategoriesContainer.visibility == View.VISIBLE
                binding.args = item
                onExpandedChangeListener(item)
            }
        }
        binding.textIogMainMenuAdd.setOnClickListener {
            listener(item)
        }
    }
}

class OgMainMenuAddAdapter(
    private val onExpandedChangeListener: (OgMainMenuAddArgs.OptionGroupMainMenus) -> Unit,
    private val listener: (OgMainMenuAddArgs.OptionGroupMainMenus) -> Unit
): BaseRecyclerView<OgMainMenuAddArgs.OptionGroupMainMenus, OgMainMenuAddViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OgMainMenuAddViewHolder {
        return OgMainMenuAddViewHolder(
            ItemOgMainMenuAddBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onExpandedChangeListener,
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