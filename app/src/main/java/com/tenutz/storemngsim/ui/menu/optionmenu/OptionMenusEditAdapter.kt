package com.tenutz.storemngsim.ui.menu.optionmenu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.databinding.ItemOptionMenusEditBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.optionmenu.args.OptionMenusEditArgs

class OptionMenusEditViewHolder(
    val binding: ItemOptionMenusEditBinding,
    private val onCheckedChangeListener: () -> Unit
) : BaseViewHolder<OptionMenusEditArgs.OptionEdit>(binding.root) {

    init {
        binding.constraintIoptionMenusEditContainer.setOnClickListener {
            binding.checkImainCategoriesEdit.isChecked = !binding.checkImainCategoriesEdit.isChecked
        }
    }

    override fun bind(item: OptionMenusEditArgs.OptionEdit) {
        binding.args = item
        binding.checkImainCategoriesEdit.setOnCheckedChangeListener(null)
        if(item.checked != binding.checkImainCategoriesEdit.isChecked) {
            onCheckedChangeListener()
            binding.checkImainCategoriesEdit.isChecked = item.checked
        }
        binding.checkImainCategoriesEdit.setOnCheckedChangeListener { _, isChecked ->
            item.checked = isChecked
            onCheckedChangeListener()
        }
    }
}

class OptionMenusEditAdapter(
    private val onCheckedChangeListener: () -> Unit,
) : BaseRecyclerView<OptionMenusEditArgs.OptionEdit, OptionMenusEditViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OptionMenusEditViewHolder {
        return OptionMenusEditViewHolder(
            ItemOptionMenusEditBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ),
            onCheckedChangeListener
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun checkOrUncheckAll() {
        val items = if(this.items.count { it.checked } == this.items.size) {
            this.items.asSequence().onEach { it.checked = false }.toList()
        } else {
            this.items.asSequence().onEach { it.checked = true }.toList()
        }
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return items[position].optionCode.hashCode().toLong()
    }
}