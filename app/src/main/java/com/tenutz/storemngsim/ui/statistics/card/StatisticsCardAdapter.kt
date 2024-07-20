package com.tenutz.storemngsim.ui.statistics.card

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByCreditCardResponse
import com.tenutz.storemngsim.databinding.ItemTstatisticsCardBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class StatisticsCardViewHolder(
    private val binding: ItemTstatisticsCardBinding,
): BaseViewHolder<StatisticsSalesByCreditCardResponse.StatisticsSalesByCreditCard>(binding.root) {

    override fun bind(
        position: Int,
        item: StatisticsSalesByCreditCardResponse.StatisticsSalesByCreditCard
    ) {
        binding.index = position + 1
        binding.args = item
    }
}

class StatisticsCardAdapter: BaseRecyclerView<StatisticsSalesByCreditCardResponse.StatisticsSalesByCreditCard, StatisticsCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsCardViewHolder {
        return StatisticsCardViewHolder(ItemTstatisticsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}