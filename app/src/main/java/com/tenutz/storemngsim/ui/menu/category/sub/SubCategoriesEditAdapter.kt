package com.tenutz.storemngsim.ui.menu.category.sub

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.core.view.doOnAttach
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.tenutz.storemngsim.databinding.ItemSubCategoriesEditBinding
import com.tenutz.storemngsim.databinding.ItemSubCategoriesEditTopBinding
import com.tenutz.storemngsim.ui.base.BaseMVHRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.category.sub.args.SubCategoriesEditArgs
import com.tenutz.storemngsim.ui.menu.category.sub.args.SubCategoriesNavArgs
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import java.util.*

sealed class SubCategoriesEditItem(val type: SubCategoriesEditType) {
    object Header : SubCategoriesEditItem(SubCategoriesEditType.HEADER)
    data class Data(val value: SubCategoriesEditArgs.SubCategoryEdit) :
        SubCategoriesEditItem(SubCategoriesEditType.DATA)
}

sealed class SubCategoriesEditViewHolder(
    binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    class SubCategoriesEditTopViewHolder(
        val binding: ItemSubCategoriesEditTopBinding,
        vm: SubCategoriesEditViewModel,
        private val onClickListener: (Int, Any?) -> Unit,
    ) : BaseViewHolder<SubCategoriesNavArgs>(binding.root) {

        init {

            binding.vm = vm
            itemView.doOnAttach {
                binding.lifecycleOwner = itemView.findViewTreeLifecycleOwner()
            }

            binding.constraintSubCategoriesEditTopAllContainer.setOnClickListener {
                onClickListener(it.id, null)
            }
        }

        override fun bind(position: Int, args: SubCategoriesNavArgs) {
            binding.args = args
        }
    }

    class SubCategoryEditViewHolder(
        val binding: ItemSubCategoriesEditBinding,
        private val onCheckedChangeListener: () -> Unit,
    ) : BaseViewHolder<SubCategoriesEditArgs.SubCategoryEdit>(binding.root) {

        init {
            binding.constraintIsubCategoriesEditContainer.setOnClickListener {
                binding.checkIsubCategoriesEdit.isChecked =
                    !binding.checkIsubCategoriesEdit.isChecked
            }
        }

        override fun bind(position: Int, item: SubCategoriesEditArgs.SubCategoryEdit) {
            binding.name = item.categoryName
            binding.code = item.categoryCode
            binding.checkIsubCategoriesEdit.setOnCheckedChangeListener(null)
            if (item.checked != binding.checkIsubCategoriesEdit.isChecked) {
                binding.checkIsubCategoriesEdit.isChecked = item.checked
                onCheckedChangeListener()
            }
            binding.checkIsubCategoriesEdit.setOnCheckedChangeListener { _, isChecked ->
                item.checked = isChecked
                onCheckedChangeListener()
            }
        }
    }
}

enum class SubCategoriesEditType { HEADER, DATA }

class SubCategoriesEditAdapter(
    private val args: SubCategoriesNavArgs,
    private val vm: SubCategoriesEditViewModel,
    private val onClickListener: (Int, Any?) -> Unit,
    private val onCheckedChangeListener: () -> Unit,
    private val onDragListener: OnDragListener<SubCategoriesEditViewHolder.SubCategoryEditViewHolder>,
) : BaseMVHRecyclerView<SubCategoriesEditItem, RecyclerView.ViewHolder>(),
    ItemTouchHelperCallback.OnItemMoveListener {

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_DATA = 1
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDropOver() {
        onDragListener.onDragOver()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items.getOrNull(position)?.let {
            when (holder) {
                is SubCategoriesEditViewHolder.SubCategoriesEditTopViewHolder -> {
                    holder.bind(position, args)
                }
                is SubCategoriesEditViewHolder.SubCategoryEditViewHolder -> {
                    holder.bind(position, (it as SubCategoriesEditItem.Data).value)

                    holder.binding.imageIsubCategoriesEditList.setOnTouchListener { v: View?, event: MotionEvent? ->
                        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                            onDragListener.onDragStart(holder)
                        }
                        false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                SubCategoriesEditViewHolder.SubCategoriesEditTopViewHolder(
                    ItemSubCategoriesEditTopBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    ),
                    vm,
                    onClickListener,
                )
            }
            VIEW_TYPE_DATA -> {
                SubCategoriesEditViewHolder.SubCategoryEditViewHolder(
                    ItemSubCategoriesEditBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onCheckedChangeListener,
                )
            }
            else -> {
                throw RuntimeException("뷰타입이 존재하지 않습니다.")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        items.getOrNull(position)?.let {
            return if (it.type == SubCategoriesEditType.HEADER) VIEW_TYPE_HEADER else VIEW_TYPE_DATA
        } ?: kotlin.run {
            return VIEW_TYPE_DATA
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun checkOrUncheckAll() {
        val items = arrayListOf<SubCategoriesEditItem>()
        items.add(SubCategoriesEditItem.Header)
        items.addAll(
            if (this.items.filterIsInstance<SubCategoriesEditItem.Data>()
                    .count { it.value.checked } == this.items.filterIsInstance<SubCategoriesEditItem.Data>().size
            ) {
                this.items.asSequence().filterIsInstance<SubCategoriesEditItem.Data>()
                    .onEach { it.value.checked = false }.toList()
            } else {
                this.items.asSequence().filterIsInstance<SubCategoriesEditItem.Data>()
                    .onEach { it.value.checked = true }.toList()
            }
        )
        val arrayListOf = arrayListOf<Long>()
        for(i in 0..items.size.minus(1)) {
            arrayListOf.add(getItemId(i))
        }
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return items[position].let {
            when(it) {
                is SubCategoriesEditItem.Header -> {
                    it.hashCode().toLong()
                }
                is SubCategoriesEditItem.Data -> {
                    it.value.categoryCode.hashCode().toLong()
                }
            }
        }
    }
}