package com.tenutz.storemngsim.ui.review.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tenutz.storemngsim.data.datasource.paging.entity.StoreReviews
import com.tenutz.storemngsim.databinding.ItemTstoreReviewsBinding
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class StoreReviewViewHolder(
    private val binding: ItemTstoreReviewsBinding,
    private val onClickListener: (Int, Any?) -> Unit,
): BaseViewHolder<StoreReviews.StoreReview>(binding.root) {

    override fun bind(position: Int, item: StoreReviews.StoreReview) {
        binding.args = item
        binding.textItstoreReviewsReplyPost.setOnClickListener { view ->
            onClickListener(view.id, item)
        }
        binding.textItstoreReviewsReplyEdit.setOnClickListener { view ->
            item.storeReviewReply?.let { onClickListener(view.id, item) }
        }
        binding.textItstoreReviewsReplyDelete.setOnClickListener { view ->
            item.storeReviewReply?.seq?.let { onClickListener(view.id, it) }
        }
    }
}

class StoreReviewsAdapter(private val onClickListener: (Int, Any?) -> Unit): PagingDataAdapter<StoreReviews.StoreReview, StoreReviewViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreReviewViewHolder {
        return StoreReviewViewHolder(ItemTstoreReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClickListener)
    }

    override fun onBindViewHolder(holder: StoreReviewViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(position, it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<StoreReviews.StoreReview>() {
            override fun areItemsTheSame(oldItem: StoreReviews.StoreReview, newItem: StoreReviews.StoreReview): Boolean {
//                return oldItem.seq == newItem.seq
                return false
            }

            override fun areContentsTheSame(oldItem: StoreReviews.StoreReview, newItem: StoreReviews.StoreReview): Boolean {
//                return oldItem == newItem
                return false
            }
        }
    }
}