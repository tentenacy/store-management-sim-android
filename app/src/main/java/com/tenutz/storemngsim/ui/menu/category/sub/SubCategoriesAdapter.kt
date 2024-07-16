package com.tenutz.storemngsim.ui.menu.category.sub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tenutz.storemngsim.data.datasource.api.dto.category.SubCategoriesResponse
import com.tenutz.storemngsim.databinding.ItemSubCategoriesBinding
import com.tenutz.storemngsim.databinding.ItemSubCategoriesTopBinding
import com.tenutz.storemngsim.ui.base.BaseMVHRecyclerView
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder
import com.tenutz.storemngsim.ui.menu.category.sub.args.SubCategoriesNavArgs

sealed class SubCategoriesItem(val type: SubCategoriesType) {
    object Header: SubCategoriesItem(SubCategoriesType.HEADER)
    data class Data(val value: SubCategoriesResponse.SubCategory): SubCategoriesItem(SubCategoriesType.DATA)
}

sealed class SubCategoriesViewHolder(
    binding: ViewDataBinding,
): RecyclerView.ViewHolder(binding.root) {
    class SubCategoriesTopViewHolder(
        val binding: ItemSubCategoriesTopBinding,
        private val onClickListener: (Int, Any?) -> Unit,
    ) : BaseViewHolder<SubCategoriesNavArgs>(binding.root) {

        init {
            binding.btnSubCategoriesTopDetails.setOnClickListener {
                onClickListener(it.id, null)
            }
            binding.textSubCategoriesTopEdit.setOnClickListener {
                onClickListener(it.id, null)
            }
        }

        override fun bind(position: Int, args: SubCategoriesNavArgs) {
            binding.args = args
        }
    }

    class SubCategoryViewHolder(
        val binding: ItemSubCategoriesBinding,
        private val onClickListener: (Int, Any?) -> Unit,
    ) : BaseViewHolder<SubCategoriesResponse.SubCategory>(binding.root) {

        override fun bind(position: Int, item: SubCategoriesResponse.SubCategory) {
            binding.name = item.categoryName
            binding.code = item.categoryCode
            binding.use = item.use
            binding.constraintIsubCategoriesContainer.setOnClickListener {
                onClickListener(it.id, item)
            }
        }
    }
}

enum class SubCategoriesType { HEADER, DATA }

class SubCategoriesAdapter(
    private val args: SubCategoriesNavArgs,
    private val onClickListener: (Int, Any?) -> Unit,
) : BaseMVHRecyclerView<SubCategoriesItem, RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_DATA = 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items.getOrNull(position)?.let {
            when(holder) {
                is SubCategoriesViewHolder.SubCategoriesTopViewHolder -> {
                    holder.bind(position, args)
                }
                is SubCategoriesViewHolder.SubCategoryViewHolder -> {
                    holder.bind(position, (it as SubCategoriesItem.Data).value)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                SubCategoriesViewHolder.SubCategoriesTopViewHolder(
                    ItemSubCategoriesTopBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    ),
                    onClickListener,
                )
            }
            VIEW_TYPE_DATA -> {
                SubCategoriesViewHolder.SubCategoryViewHolder(
                    ItemSubCategoriesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClickListener,
                )
            }
            else -> {
                throw RuntimeException("뷰타입이 존재하지 않습니다.")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        items.getOrNull(position)?.let {
            return if(it.type == SubCategoriesType.HEADER) VIEW_TYPE_HEADER else VIEW_TYPE_DATA
        } ?: kotlin.run {
            return VIEW_TYPE_DATA
        }
    }
}