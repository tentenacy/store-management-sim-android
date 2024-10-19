package com.tenutz.storemngsim.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tenutz.storemngsim.data.datasource.paging.entity.PushAlarms
import com.tenutz.storemngsim.databinding.ItemNotificationBinding
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class NotificationViewHolder(
    private val binding: ItemNotificationBinding,
): BaseViewHolder<PushAlarms.PushAlarm>(binding.root) {

    override fun bind(position: Int, item: PushAlarms.PushAlarm) {
        binding.args = item
    }
}

class NotificationAdapter: PagingDataAdapter<PushAlarms.PushAlarm, NotificationViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(position, it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PushAlarms.PushAlarm>() {
            override fun areItemsTheSame(oldItem: PushAlarms.PushAlarm, newItem: PushAlarms.PushAlarm): Boolean {
//                return oldItem.seq == newItem.seq
                return false
            }

            override fun areContentsTheSame(oldItem: PushAlarms.PushAlarm, newItem: PushAlarms.PushAlarm): Boolean {
//                return oldItem == newItem
                return false
            }
        }
    }
}