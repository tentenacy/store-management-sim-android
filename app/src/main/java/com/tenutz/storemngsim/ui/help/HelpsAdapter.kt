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
    private val onExpandedChangeListener: (HelpsArgs.Help) -> Unit,
): BaseViewHolder<HelpsArgs.Help>(binding.root) {

    init {
        binding.constraintIhelpsExpandableContainer.setOnClickListener {
            binding.constraintIhelpsContentContainer.visibility =
                if(binding.constraintIhelpsContentContainer.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
        binding.constraintIhelpsContentContainer.tag = binding.constraintIhelpsContentContainer.visibility
    }

    override fun bind(position: Int, item: HelpsArgs.Help) {
        binding.position = position
        binding.args = item
        if(item.expanded != binding.constraintIhelpsContentContainer.isVisible) {
            onExpandedChangeListener(item)
            binding.constraintIhelpsContentContainer.visibility = if(item.expanded) View.VISIBLE else View.GONE
        }
        binding.constraintIhelpsContentContainer.viewTreeObserver.addOnGlobalLayoutListener {
            if(binding.constraintIhelpsContentContainer.tag as Int != binding.constraintIhelpsContentContainer.visibility) {
                binding.constraintIhelpsContentContainer.tag = binding.constraintIhelpsContentContainer.visibility
                //visibility has changed
                item.expanded = binding.constraintIhelpsContentContainer.visibility == View.VISIBLE
                binding.args = item
                onExpandedChangeListener(item)
            }
        }
    }
}

class HelpsAdapter(
    private val onExpandedChangeListener: (HelpsArgs.Help) -> Unit,
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