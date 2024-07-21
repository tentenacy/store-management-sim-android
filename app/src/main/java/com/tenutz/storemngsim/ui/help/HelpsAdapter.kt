package com.tenutz.storemngsim.ui.help

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.tenutz.storemngsim.databinding.ItemHelpsBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.help.args.HelpsArgs

class HelpsViewHolder(
    val binding: ItemHelpsBinding,
    private val onExpandedChangeListener: () -> Unit,
): BaseViewHolder<HelpsArgs.Help>(binding.root) {

    override fun bind(position: Int, item: HelpsArgs.Help) {
        binding.index = position + 1
        binding.args = item
        if(item.expanded != binding.constraintIhelpsContentContainer.isVisible) {
            onExpandedChangeListener()
            binding.constraintIhelpsContentContainer.visibility = if(item.expanded) View.VISIBLE else View.GONE
        }
        binding.constraintIhelpsExpandableContainer.setOnClickListener {
            item.expanded = !item.expanded
            binding.args = item
            onExpandedChangeListener()
            binding.constraintIhelpsContentContainer.visibility = if(item.expanded) View.VISIBLE else View.GONE
        }
    }
}

class HelpsAdapter(
    private val onExpandedChangeListener: () -> Unit,
): BaseRecyclerView<HelpsArgs.Help, HelpsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpsViewHolder {
        return HelpsViewHolder(
            ItemHelpsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onExpandedChangeListener,
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
        return "${items[position].seq}".hashCode().toLong()
    }
}