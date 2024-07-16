package com.tenutz.storemngsim.ui.menu.mainmenu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoryPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MenuPrioritiesChangeRequest
import com.tenutz.storemngsim.databinding.ItemMainMenusEditBinding
import com.tenutz.storemngsim.databinding.ItemMiddleCategoriesEditBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.category.middle.args.MiddleCategoriesEditArgs
import com.tenutz.storemngsim.ui.menu.mainmenu.args.MainMenusEditArgs
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import java.util.*

class MainMenusEditViewHolder(
    val binding: ItemMainMenusEditBinding,
    private val onCheckedChangeListener: () -> Unit
) : BaseViewHolder<MainMenusEditArgs.MainMenuEdit>(binding.root) {

    init {
        binding.constraintImainMenusEditContainer.setOnClickListener {
            binding.checkImainCategoriesEdit.isChecked = !binding.checkImainCategoriesEdit.isChecked
        }
    }

    override fun bind(position: Int, item: MainMenusEditArgs.MainMenuEdit) {
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

class MainMenusEditAdapter(
    private val onCheckedChangeListener: () -> Unit,
    private val onDragListener: OnDragListener<MainMenusEditViewHolder>,
) : BaseRecyclerView<MainMenusEditArgs.MainMenuEdit, MainMenusEditViewHolder>(), ItemTouchHelperCallback.OnItemMoveListener {

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
    ): MainMenusEditViewHolder {
        return MainMenusEditViewHolder(
            ItemMainMenusEditBinding.inflate(
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
        return items[position].menuCode.hashCode().toLong()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MainMenusEditViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.binding.imageImainCategoriesEditList.setOnTouchListener { v: View?, event: MotionEvent? ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                onDragListener.onDragStart(holder)
            }
            false
        }
    }
}