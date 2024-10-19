package com.tenutz.storemngsim.ui.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.paging.entity.SalesList
import com.tenutz.storemngsim.databinding.FragmentSalesBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.sales.bs.SalesFilterBottomSheetDialog
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SalesFragment: BaseFragment() {

    private var _binding: FragmentSalesBinding? = null
    val binding: FragmentSalesBinding get() = _binding!!

    val vm: SalesViewModel by viewModels()

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (positionStart == 0) {
                binding.recyclerSales.scrollToPosition(0)
            }
        }
    }

    val adapter: SalesAdapter by lazy {
        SalesAdapter { id, _ ->

        }.apply {
            registerAdapterDataObserver(adapterDataObserver)
            addLoadStateListener { loadState ->
                vm.empty.value = adapter.itemCount < 1
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.salesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSalesBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    SalesViewModel.EVENT_REFRESH_SALES_LIST -> {
                        adapter.submitData(
                            viewLifecycleOwner.lifecycle,
                            it.second as PagingData<SalesList.Sales>
                        )
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.recyclerSales.adapter = adapter
    }

    private fun setOnClickListeners() {
        binding.imageSalesBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageSalesHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageSalesHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.constraintSalesFilterContainer.setOnClickListener {
            SalesFilterBottomSheetDialog { id, _ ->

            }.show(childFragmentManager, "salesFilterBottomSheetDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.unregisterAdapterDataObserver(adapterDataObserver)
        _binding = null
    }
}