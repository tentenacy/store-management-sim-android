package com.tenutz.storemngsim.ui.menu.category.middle

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoryPrioritiesChangeRequest
import com.tenutz.storemngsim.databinding.ItemMiddleCategoriesEditBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.category.main.args.MainCategoriesEditArgs
import com.tenutz.storemngsim.ui.menu.category.middle.args.MiddleCategoriesEditArgs
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import java.util.*

class MiddleCategoriesEditViewHolder(
    val binding: ItemMiddleCategoriesEditBinding,
    private val onCheckedChangeListener: () -> Unit
) : BaseViewHolder<MiddleCategoriesEditArgs.MiddleCategoryEdit>(binding.root) {

    init {
        binding.constraintImiddleCategoriesEdit.setOnClickListener {
            binding.checkImiddleCategoriesEdit.isChecked = !binding.checkImiddleCategoriesEdit.isChecked
        }
    }

    override fun bind(position: Int, item: MiddleCategoriesEditArgs.MiddleCategoryEdit) {
        binding.name = item.categoryName
        binding.code = item.categoryCode
        binding.checkImiddleCategoriesEdit.setOnCheckedChangeListener(null)
        if(item.checked != binding.checkImiddleCategoriesEdit.isChecked) {
            onCheckedChangeListener()
            binding.checkImiddleCategoriesEdit.isChecked = item.checked
        }
        binding.checkImiddleCategoriesEdit.setOnCheckedChangeListener { _, isChecked ->
            item.checked = isChecked
            onCheckedChangeListener()
        }
    }
}

class MiddleCategoriesEditAdapter(
    private val onCheckedChangeListener: () -> Unit,
    private val onDragListener: OnDragListener<MiddleCategoriesEditViewHolder>,
) : BaseRecyclerView<MiddleCategoriesEditArgs.MiddleCategoryEdit, MiddleCategoriesEditViewHolder>(), ItemTouchHelperCallback.OnItemMoveListener {

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
    ): MiddleCategoriesEditViewHolder {
        return MiddleCategoriesEditViewHolder(
            ItemMiddleCategoriesEditBinding.inflate(
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
        return items[position].categoryCode.hashCode().toLong()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MiddleCategoriesEditViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.binding.imageImiddleCategoriesEditList.setOnTouchListener { v: View?, event: MotionEvent? ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                onDragListener.onDragStart(holder)
            }
            false
        }
    }
}