package com.tenutz.storemngsim.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.tenutz.storemngsim.data.datasource.paging.entity.PushAlarms
import com.tenutz.storemngsim.data.datasource.sharedpref.Events
import com.tenutz.storemngsim.data.datasource.sharedpref.EventsSharedPref
import com.tenutz.storemngsim.databinding.FragmentNotificationBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment: BaseFragment() {

    private var _binding: FragmentNotificationBinding? = null
    val binding: FragmentNotificationBinding get() = _binding!!

    val vm: NotificationViewModel by viewModels()

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (positionStart == 0) {
                binding.recyclerNotification.scrollToPosition(0)
            }
        }
    }

    val adapter: NotificationAdapter by lazy {
        NotificationAdapter().apply {
            registerAdapterDataObserver(adapterDataObserver)
            addLoadStateListener { loadState ->
                vm.empty.value =
                    !(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1)
            }
        }
    }

    var dividerItemDecoration: DividerItemDecoration? = null
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EventsSharedPref.existsNewNotification = false
        Events.existsNewNotification.postValue(EventsSharedPref.existsNewNotification)
        vm.notifications()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

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

    private fun initViews() {
        dividerItemDecoration?.let { binding.recyclerNotification.addItemDecoration(it) }
        binding.recyclerNotification.adapter = adapter
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    NotificationViewModel.EVENT_REFRESH_NOTIFICATIONS -> {
                        adapter.submitData(
                            viewLifecycleOwner.lifecycle,
                            it.second as PagingData<PushAlarms.PushAlarm>
                        )
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageNotificationBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.unregisterAdapterDataObserver(adapterDataObserver)
        _binding = null
    }
}