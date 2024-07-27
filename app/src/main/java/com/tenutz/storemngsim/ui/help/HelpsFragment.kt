package com.tenutz.storemngsim.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.FragmentHelpsBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.utils.ext.editTextObservable
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HelpsFragment: BaseFragment() {

    private val disposable = CompositeDisposable()

    private var _binding: FragmentHelpsBinding? = null
    val binding: FragmentHelpsBinding get() = _binding!!

    val vm: HelpsViewModel by viewModels()

    private val adapter: HelpsAdapter by lazy {
        HelpsAdapter(
            onExpandedChangeListener = {
                vm.updateExpandedItemCount()
            },
        ).apply {
            setHasStableIds(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.helps()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHelpsBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageHelpsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageHelpsHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageHelpsHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
    }

    private fun observeData() {
        vm.helps.observe(viewLifecycleOwner) {
            adapter.updateAllItems(it.helps)
        }
    }

    private fun initViews() {
        binding.recyclerHelps.adapter = adapter
        editTextObservable(binding.editHelpsSearch)
            .debounce(500, TimeUnit.MILLISECONDS).skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.helps(it)
            }
            .addTo(disposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}