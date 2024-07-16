package com.tenutz.storemngsim.ui.menu.optiongroup

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.databinding.ItemOptionGroupsEditBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.optiongroup.args.OptionGroupsEditArgs

class OptionGroupsEditViewHolder(
    val binding: ItemOptionGroupsEditBinding,
    private val onCheckedChangeListener: () -> Unit
) : BaseViewHolder<OptionGroupsEditArgs.OptionGroup>(binding.root) {

    init {
        binding.constraintIoptionGroupsEdit.setOnClickListener {
            binding.checkIoptionGroupsEdit.isChecked = !binding.checkIoptionGroupsEdit.isChecked
        }
    }

    override fun bind(position: Int, item: OptionGroupsEditArgs.OptionGroup) {
        binding.args = item
        binding.checkIoptionGroupsEdit.setOnCheckedChangeListener(null)
        if(item.checked != binding.checkIoptionGroupsEdit.isChecked) {
            onCheckedChangeListener()
            binding.checkIoptionGroupsEdit.isChecked = item.checked
        }
        binding.checkIoptionGroupsEdit.setOnCheckedChangeListener { _, isChecked ->
            item.checked = isChecked
            onCheckedChangeListener()
        }
    }
}

class OptionGroupsEditAdapter(
    private val onCheckedChangeListener: () -> Unit,
) : BaseRecyclerView<OptionGroupsEditArgs.OptionGroup, OptionGroupsEditViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OptionGroupsEditViewHolder {
        return OptionGroupsEditViewHolder(
            ItemOptionGroupsEditBinding.inflate(
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
        return items[position].optionGroupCode.hashCode().toLong()
    }
}