package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import com.tenutz.storemngsim.databinding.ItemTogMainMenusEditBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgMainMenusEditArgs
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import java.util.*

class OgMainMenusEditViewHolder(
    val binding: ItemTogMainMenusEditBinding,
    private val onCheckedChangeListener: () -> Unit
) : BaseViewHolder<OgMainMenusEditArgs.OptionGroupMainMenuMapper>(binding.root) {

    init {
        binding.constraintItogMainMenusEditContainer.setOnClickListener {
            binding.checkImainCategoriesEdit.isChecked = !binding.checkImainCategoriesEdit.isChecked
        }
    }

    override fun bind(position: Int, item: OgMainMenusEditArgs.OptionGroupMainMenuMapper) {
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

class OgMainMenusEditAdapter(
    private val onCheckedChangeListener: () -> Unit,
    private val onDragListener: OnDragListener<OgMainMenusEditViewHolder>,
) : BaseRecyclerView<OgMainMenusEditArgs.OptionGroupMainMenuMapper, OgMainMenusEditViewHolder>(), ItemTouchHelperCallback.OnItemMoveListener {

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
    ): OgMainMenusEditViewHolder {
        return OgMainMenusEditViewHolder(
            ItemTogMainMenusEditBinding.inflate(
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
        return "${items[position].mainCategoryCode}${items[position].middleCategoryCode}${items[position].subCategoryCode}${items[position].menuCode}".hashCode().toLong()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: OgMainMenusEditViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.binding.imageImainCategoriesEditList.setOnTouchListener { v: View?, event: MotionEvent? ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                onDragListener.onDragStart(holder)
            }
            false
        }
    }
}