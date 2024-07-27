package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import com.tenutz.storemngsim.databinding.ItemMmOptionGroupsEditBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.args.MmOptionGroupsEditArgs
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import java.util.Collections

class MmOptionGroupsEditViewHolder(
    val binding: ItemMmOptionGroupsEditBinding,
    private val onCheckedChangeListener: () -> Unit
) : BaseViewHolder<MmOptionGroupsEditArgs.MainMenuOptionGroup>(binding.root) {

    init {
        binding.constraintImmOptionGroupsEdit.setOnClickListener {
            binding.checkImmOptionGroupsEdit.isChecked = !binding.checkImmOptionGroupsEdit.isChecked
        }
    }

    override fun bind(position: Int, item: MmOptionGroupsEditArgs.MainMenuOptionGroup) {
        binding.name = item.optionName
        binding.code = item.optionGroupCode
        binding.checkImmOptionGroupsEdit.setOnCheckedChangeListener(null)
        if(item.checked != binding.checkImmOptionGroupsEdit.isChecked) {
            onCheckedChangeListener()
            binding.checkImmOptionGroupsEdit.isChecked = item.checked
        }
        binding.checkImmOptionGroupsEdit.setOnCheckedChangeListener { _, isChecked ->
            item.checked = isChecked
            onCheckedChangeListener()
        }
    }
}

class MmOptionGroupsEditAdapter(
    private val onCheckedChangeListener: () -> Unit,
    private val onDragListener: OnDragListener<MmOptionGroupsEditViewHolder>,
) : BaseRecyclerView<MmOptionGroupsEditArgs.MainMenuOptionGroup, MmOptionGroupsEditViewHolder>(), ItemTouchHelperCallback.OnItemMoveListener {

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDropOver() {
        onDragListener.onDragOver()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MmOptionGroupsEditViewHolder {
        return MmOptionGroupsEditViewHolder(
            ItemMmOptionGroupsEditBinding.inflate(
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MmOptionGroupsEditViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.binding.imageImmOptionGroupsEditList.setOnTouchListener { v: View?, event: MotionEvent? ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                onDragListener.onDragStart(holder)
            }
            false
        }
    }
}