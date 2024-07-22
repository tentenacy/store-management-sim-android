package com.tenutz.storemngsim.ui.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.paging.entity.SalesList
import com.tenutz.storemngsim.databinding.FragmentSalesBinding
import com.tenutz.storemngsim.databinding.FragmentSalesBindingImpl
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.sales.bs.SalesFilterBottomSheetDialog
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalesFragment: BaseFragment() {

    private var _binding: FragmentSalesBinding? = null
    val binding: FragmentSalesBinding get() = _binding!!

    val vm: SalesViewModel by viewModels()

    val adapter: SalesAdapter by lazy {
        SalesAdapter { id, _ ->

        }.apply {
            addLoadStateListener { loadState ->
                vm.empty.value =
                    !(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1)
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
            findNavController().navigate(R.id.action_global_mainFragment)
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
        _binding = null
    }
}