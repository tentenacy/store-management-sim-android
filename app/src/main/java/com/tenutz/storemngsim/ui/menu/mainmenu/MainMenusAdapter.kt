package com.tenutz.storemngsim.ui.menu.mainmenu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenusResponse
import com.tenutz.storemngsim.databinding.ItemMainMenusBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class MainMenusViewHolder(
    val binding: ItemMainMenusBinding,
    private val onClickListener: (Int, Any?) -> Unit,
): BaseViewHolder<MainMenusResponse.MainMenu>(binding.root) {

    private lateinit var _item: MainMenusResponse.MainMenu
    val item: MainMenusResponse.MainMenu get() = _item

    init {
        binding.constraintImainMenusContainer.setOnClickListener {
            onClickListener(it.id, item)
        }
    }

    override fun bind(position: Int, item: MainMenusResponse.MainMenu) {
        _item = item
        binding.args = item
    }
}

class MainMenusAdapter(
    private val onClickListener: (Int, Any?) -> Unit,
): BaseRecyclerView<MainMenusResponse.MainMenu, MainMenusViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenusViewHolder {
        return MainMenusViewHolder(ItemMainMenusBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClickListener)
    }

    override fun getItemId(position: Int): Long {
        return items[position].menuCode.hashCode().toLong()
    }
}