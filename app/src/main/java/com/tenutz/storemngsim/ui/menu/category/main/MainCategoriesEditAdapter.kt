package com.tenutz.storemngsim.ui.menu.category.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoryPrioritiesChangeRequest
import com.tenutz.storemngsim.databinding.ItemMainCategoriesEditBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.category.main.args.MainCategoriesEditArgs
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import java.util.Collections

class MainCategoriesEditViewHolder(
    val binding: ItemMainCategoriesEditBinding,
    private val onCheckedChangeListener: () -> Unit
) : BaseViewHolder<MainCategoriesEditArgs.MainCategoryEdit>(binding.root) {

    init {
        binding.constraintImainCategoriesEdit.setOnClickListener {
            binding.checkImainCategoriesEdit.isChecked = !binding.checkImainCategoriesEdit.isChecked
        }
    }

    override fun bind(position: Int, item: MainCategoriesEditArgs.MainCategoryEdit) {
        binding.name = item.categoryName
        binding.code = item.categoryCode
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

class MainCategoriesEditAdapter(
    private val onCheckedChangeListener: () -> Unit,
    private val onChangePrioritiesListener: (CategoryPrioritiesChangeRequest) -> Unit,
    private val onDragListener: OnDragListener<MainCategoriesEditViewHolder>,
) : BaseRecyclerView<MainCategoriesEditArgs.MainCategoryEdit, MainCategoriesEditViewHolder>(), ItemTouchHelperCallback.OnItemMoveListener {

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
    ): MainCategoriesEditViewHolder {
        return MainCategoriesEditViewHolder(
            ItemMainCategoriesEditBinding.inflate(
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
    override fun onBindViewHolder(holder: MainCategoriesEditViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.binding.imageImainCategoriesEditList.setOnTouchListener { v: View?, event: MotionEvent? ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                onDragListener.onDragStart(holder)
            }
            false
        }
    }
}