package com.tenutz.storemngsim.ui.review.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuReviews
import com.tenutz.storemngsim.databinding.ItemTmenuReviewsBinding
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class MenuReviewViewHolder(
    private val binding: ItemTmenuReviewsBinding,
    private val onClickListener: (Int, Any?) -> Unit,
): BaseViewHolder<MenuReviews.MenuReview>(binding.root) {

    override fun bind(item: MenuReviews.MenuReview) {
        binding.args = item
        binding.textItmenuReviewsReplyPost.setOnClickListener { view ->
            onClickListener(view.id, item)
        }
        binding.textItmenuReviewsReplyEdit.setOnClickListener { view ->
            item.menuReviewReply?.let { onClickListener(view.id, item) }
        }
        binding.textItmenuReviewsReplyDelete.setOnClickListener { view ->
            item.menuReviewReply?.seq?.let { onClickListener(view.id, it) }
        }
    }
}

class MenuReviewsAdapter(private val onClickListener: (Int, Any?) -> Unit): PagingDataAdapter<MenuReviews.MenuReview, MenuReviewViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuReviewViewHolder {
        return MenuReviewViewHolder(ItemTmenuReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClickListener)
    }

    override fun onBindViewHolder(holder: MenuReviewViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MenuReviews.MenuReview>() {
            override fun areItemsTheSame(oldItem: MenuReviews.MenuReview, newItem: MenuReviews.MenuReview): Boolean {
                return oldItem.seq == newItem.seq
            }

            override fun areContentsTheSame(oldItem: MenuReviews.MenuReview, newItem: MenuReviews.MenuReview): Boolean {
                return oldItem == newItem
            }
        }
    }
}