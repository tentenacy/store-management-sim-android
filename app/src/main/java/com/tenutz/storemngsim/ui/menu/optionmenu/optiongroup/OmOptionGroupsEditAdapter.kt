package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import com.tenutz.storemngsim.databinding.ItemOmOptionGroupsEditBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.args.OmOptionGroupsEditArgs
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import java.util.*

class OmOptionGroupsEditViewHolder(
    val binding: ItemOmOptionGroupsEditBinding,
    private val onCheckedChangeListener: () -> Unit
) : BaseViewHolder<OmOptionGroupsEditArgs.OptionOptionGroupEdit>(binding.root) {

    init {
        binding.constraintIomOptionGroupsEdit.setOnClickListener {
            binding.checkIomOptionGroupsEdit.isChecked = !binding.checkIomOptionGroupsEdit.isChecked
        }
    }

    override fun bind(item: OmOptionGroupsEditArgs.OptionOptionGroupEdit) {
        binding.args = item
        binding.checkIomOptionGroupsEdit.setOnCheckedChangeListener(null)
        if(item.checked != binding.checkIomOptionGroupsEdit.isChecked) {
            onCheckedChangeListener()
            binding.checkIomOptionGroupsEdit.isChecked = item.checked
        }
        binding.checkIomOptionGroupsEdit.setOnCheckedChangeListener { _, isChecked ->
            item.checked = isChecked
            onCheckedChangeListener()
        }
    }
}

class OmOptionGroupsEditAdapter(
    private val onCheckedChangeListener: () -> Unit,
    private val onDragListener: OnDragListener<OmOptionGroupsEditViewHolder>,
) : BaseRecyclerView<OmOptionGroupsEditArgs.OptionOptionGroupEdit, OmOptionGroupsEditViewHolder>(), ItemTouchHelperCallback.OnItemMoveListener {

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
    ): OmOptionGroupsEditViewHolder {
        return OmOptionGroupsEditViewHolder(
            ItemOmOptionGroupsEditBinding.inflate(
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
    override fun onBindViewHolder(holder: OmOptionGroupsEditViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.binding.imageIomOptionGroupsEditList.setOnTouchListener { v: View?, event: MotionEvent? ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                onDragListener.onDragStart(holder)
            }
            false
        }
    }
}